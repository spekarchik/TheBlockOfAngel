package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class RendelithicArmor extends Armor
{
    private final IArmorEffect nauseaEffect;
    private final IArmorEffect slownessEffect;
    private final IArmorEffect levitationEffect;
    private final SwitchingEffectSynchronizer jumpEffect;

    private static final int JUMP_EFFECT_AMPLIFIER_DEFAULT = 3;
    private static final int JUMP_EFFECT_AMPLIFIER_BOOSTED = 5;

    public RendelithicArmor(IPlayer player)
    {
        super(player);
        nauseaEffect = new NauseaTemporaryEffect(player, this, 200).availableOnAnyArmorElement();
        slownessEffect = new SlownessArmorEffect(player, this, 5, 400).availableOnAnyArmorElement();
        levitationEffect = new LevitationSwitchingEffect(player, this, 1).setupAvailability(this::isLevitationAvailable);

        JumpBoostArmorEffect jumpEffect = new JumpBoostArmorEffect(player, this, JUMP_EFFECT_AMPLIFIER_DEFAULT);
        jumpEffect.availableIfSlotsSet(EquipmentSlot.FEET, EquipmentSlot.LEGS);
        SpeedSwitchingEffect speedEffect = new SpeedSwitchingEffect(player, this, 0);
        this.jumpEffect = new SwitchingEffectSynchronizer(jumpEffect);
        this.jumpEffect.addDependentEffect(speedEffect);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        jumpEffect.updateSwitchState();
        levitationEffect.updateSwitchState();
    }

    @Override
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
        if (isFireDamage(event.getSource()))
        {
            float realDamage = getRealDamage(event.getAmount());
            event.setAmount(realDamage);
            event.setCanceled(realDamage <= 0);
        }
    }

    @Override
    public void onLivingAttackEvent(LivingAttackEvent event)
    {
        DamageSource damageSource = event.getSource();
        if (isFireDamage(damageSource))
        {
            float realDamage = getRealDamage(event.getAmount());
            event.setCanceled(realDamage <= 0);
        }
        else
        {
            boolean hasHealthRegeneration = player.isArmorModifiedWithHealthRegenerator(this);
            if (hasHealthRegeneration && damageSource == DamageSource.WITHER)
            {
                event.setCanceled(true);
                player.getEntity().removeEffect(MobEffects.WITHER);
            }
        }
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        jumpEffect.updateEffectAvailability();
        nauseaEffect.updateEffectAvailability();
        slownessEffect.updateEffectAvailability();
        levitationEffect.updateEffectAvailability();

        updatePotionEffects();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        // none
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
        if (jumpEffect.isEffectOn() && jumpEffect.isActive())
        {
            if (player.areBootsModifiedWithStrengthBooster(this))
            {
                event.setDamageMultiplier(0.6f);
            }
        }
    }

    @Override
    public void onCreeperCheck()
    {
        // none
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            jumpEffect.trySwitch(getJumpBoostAmplifier());
        }

        if (pressedKeyDescription.equals(KeyRegistry.LEVITATION.getName()))
        {
            if (levitationEffect.isEffectAvailable())
            {
                if (!slownessEffect.isActive() || levitationEffect.isEffectOn())
                {
                    levitationEffect.trySwitch();
                }
            }
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        updatePotionEffects();
    }

    @Override
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        // none
    }

    @Override
    public void onBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event)
    {
        if (player.getEntity().isInWater())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.02f);
        }
        else if (jumpEffect.isEffectOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }
    }

    @Override
    public void onBeingInWater()
    {
        checkForNausea();
    }

    @Override
    public void onBeingUnderRain()
    {
        checkForNausea();
    }

    @Override
    public String getModelName()
    {
        return ArmorRegistry.RENDELITHIC_BOOTS.get().getArmorModelName();
    }

    @Override
    public int getPriority()
    {
        return 2;
    }

    private void updatePotionEffects()
    {
        jumpEffect.updateEffectActivity(getJumpBoostAmplifier());

        checkForNausea();

        if (!slownessEffect.isActive())
        {
            levitationEffect.updateEffectActivity();
        }
    }

    private int getJumpBoostAmplifier()
    {
        var amplifier = player.areBootsModifiedWithStrengthBooster(this)
                ? JUMP_EFFECT_AMPLIFIER_BOOSTED
                : JUMP_EFFECT_AMPLIFIER_DEFAULT;

        if (player.isOverworld())
            return amplifier - 1;
        else
            return amplifier;
    }

    private void checkForNausea()
    {
        Player entity = player.getEntity();
        if (entity.isInWaterOrRain())
        {
//            if (nauseaEffect.isActive())
//            {
//                double x = entity.posX;
//                double y = entity.posY;
//                double z = entity.posZ;
//                entity.setPositionAndUpdate(x, y + 0.2, z);
//            }

            if (!slownessEffect.isActive())
            {
                nauseaEffect.trySwitch();
                slownessEffect.trySwitch();
                if (levitationEffect.isEffectOn())
                {
                    levitationEffect.trySwitchOff();
                }
            }
        }
    }

    private float getRealDamage(float initialDamageAmount)
    {
        float helmetProtection = player.isArmorElementPutOn(this, EquipmentSlot.HEAD) ? initialDamageAmount * 0.2f : 0;
        float bootsProtection = player.isArmorElementPutOn(this, EquipmentSlot.FEET) ? initialDamageAmount * 0.2f : 0;
        float chestplateProtection = player.isArmorElementPutOn(this, EquipmentSlot.CHEST) ? initialDamageAmount * 0.35f : 0;
        float leggingsProtection = player.isArmorElementPutOn(this, EquipmentSlot.LEGS) ? initialDamageAmount * 0.3f : 0;
        float realDamage = initialDamageAmount - helmetProtection - bootsProtection - chestplateProtection - leggingsProtection;
        return realDamage > 0 ? realDamage : 0;
    }

    private boolean isFireDamage(DamageSource damageSource)
    {
        boolean isDamagedByInFire = damageSource == DamageSource.IN_FIRE;
        boolean isDamagedByOnFire = damageSource == DamageSource.ON_FIRE;
        boolean isDamagedByLava = damageSource == DamageSource.LAVA;
        return isDamagedByInFire || isDamagedByLava || isDamagedByOnFire;
    }

    private boolean isLevitationAvailable(IPlayer player, IArmor armor)
    {
        return player.isFullArmorSetPutOn(armor) && player.isArmorModifiedWithLevitation(armor);
    }
}
