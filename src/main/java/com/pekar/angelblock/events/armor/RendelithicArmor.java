package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class RendelithicArmor extends Armor
{
    private final ITemporaryPersistentArmorEffect nauseaEffect;
    private final ITemporaryPersistentArmorEffect slownessEffect;
    private final ISwitchingArmorEffect slowFallingEffect;
    private final ISwitchingArmorEffect glowingEffect;
    private final ISwitchingEffectSynchronizer jumpEffect;

    private static final int JUMP_EFFECT_AMPLIFIER_DEFAULT = 3;
    private static final int JUMP_EFFECT_AMPLIFIER_BOOSTED = 5;

    public RendelithicArmor(IPlayer player)
    {
        super(player);
        nauseaEffect = new NauseaNegativeEffect(player, this, 200).showIcon();
        slownessEffect = new SlownessNegativeArmorEffect(player, this, 5, 400);
        slowFallingEffect = new SlowFallingSwitchingEffect(player, this).availableOnChestPlateWithSlowFalling();
        glowingEffect = new GlowingSwitchingArmorEffect(player, this).availableOnChestPlateWithSlowFalling();

        JumpBoostSwitchingArmorEffect jumpEffect = new JumpBoostSwitchingArmorEffect(player, this, JUMP_EFFECT_AMPLIFIER_DEFAULT);
        jumpEffect.availableIfSlotSet(EquipmentSlot.FEET);
        SpeedSwitchingEffect speedEffect = new SpeedSwitchingEffect(player, this, 0);
        this.jumpEffect = new SwitchingEffectSynchronizer(jumpEffect);
        this.jumpEffect.addDependentEffect(speedEffect);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        jumpEffect.updateSwitchState();
        slowFallingEffect.updateSwitchState();
        glowingEffect.updateSwitchState();
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        var damageSource = event.getSource();

        if (isFireDamage(damageSource))
        {
            float realDamage = getRealDamage(event.getAmount());
            event.setCanceled(realDamage <= 0);

            if (player.isFullArmorSetPutOn(this))
                event.getEntity().clearFire();
        }
        else if (isLavaDamage(damageSource) && player.isFullArmorSetPutOn(this))
        {
            event.setCanceled(true);
        }
        else
        {
            boolean hasHealthRegeneration = player.areLeggingsModifiedWithHealthRegenerator(this);
            if (hasHealthRegeneration && damageSource.is(DamageTypes.WITHER))
            {
                event.setCanceled(true);
                player.getEntity().removeEffect(MobEffects.WITHER);
            }
        }
    }

    @Override
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
        var damageSource = event.getSource();

        if (isFireDamage(damageSource))
        {
            float realDamage = getRealDamage(event.getNewDamage());
            event.setNewDamage(realDamage);
        }
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        jumpEffect.updateAvailability();
        nauseaEffect.updateAvailability();
        slownessEffect.updateAvailability();
        slowFallingEffect.updateAvailability();
        glowingEffect.updateAvailability();

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
        // Note: the JUMP_BOOST effect provides itself damage protection
    }

    @Override
    public void onCreeperCheck()
    {
        if (player.getEntity() instanceof ServerPlayer serverPlayer)
            breakBlockUnderPlayer(serverPlayer,true, isIcePredicate, Blocks.WATER.defaultBlockState(), playIceBreakSound, 32);
    }

    @Override
    public void onEffectAddedEvent(MobEffectEvent.Added event)
    {
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
            if (slowFallingEffect.isAvailable())
            {
                if (!slownessEffect.isActive() || slowFallingEffect.isOn())
                {
                    slowFallingEffect.trySwitch();
                }
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
        {
            glowingEffect.trySwitch();
        }

//        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
//        {
//            damageArmor(true);
//        }
//
//        if (pressedKeyDescription.equals(KeyRegistry.REGENERATION.getName()))
//        {
//            damageArmor(false);
//        }
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
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        if (player.getEntity().isInWater())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.02f);
        }
        else if (jumpEffect.isOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }
    }

    @Override
    public void onBeingInLava()
    {
        // none
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
    public String getFamilyName()
    {
        return ArmorRegistry.RENDELITHIC_BOOTS.get().getArmorFamilyName();
    }

    @Override
    public int getPriority()
    {
        return 2;
    }

    private void updatePotionEffects()
    {
        jumpEffect.updateActivity(getJumpBoostAmplifier());
        glowingEffect.updateActivity();

        checkForNausea();

        if (!slownessEffect.isActive())
        {
            slowFallingEffect.updateActivity();
        }
    }

    private int getJumpBoostAmplifier()
    {
        return player.areBootsModifiedWithJumpBooster(this)
                ? JUMP_EFFECT_AMPLIFIER_BOOSTED
                : JUMP_EFFECT_AMPLIFIER_DEFAULT;
    }

    private void checkForNausea()
    {
        Player entity = player.getEntity();
        if (entity.isInWaterOrRain())
        {
            if (!slownessEffect.isActive())
            {
                nauseaEffect.tryActivate();
                slownessEffect.tryActivate();
                if (slowFallingEffect.isOn())
                {
                    slowFallingEffect.trySwitchOff();
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
}
