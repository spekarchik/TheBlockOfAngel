package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
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

    public RendelithicArmor(IPlayer player)
    {
        super(player);
        nauseaEffect = new NauseaTemporaryEffect(player, this, 200);
        slownessEffect = new SlownessArmorEffect(player, this, 5, 400, true);
        levitationEffect = new LevitationSwitchingEffect(player, this, 1, true);

        JumpBoostArmorEffect jumpEffect = new JumpBoostArmorEffect(player, this, 5);
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
            boolean isFullArmorSetPutOn = player.isFullArmorSetPutOn(getArmorElementNames());
            if (isFullArmorSetPutOn && damageSource == DamageSource.WITHER)
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
            event.setDamageMultiplier(0.6f);
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
    public void onBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event)
    {
        if (player.getEntity().isInWater())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.02f);
        }
        else if (jumpEffect.isEffectOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.5f);
        }
    }

    @Override
    public void onBeingInWater()
    {
        checkForNausea();
    }

    @Override
    public String getHelmetName()
    {
        return ArmorRegistry.RENDELITHIC_HELMET.get().getRegistryName().getPath();
    }

    @Override
    public String getChestPlateName()
    {
        return ArmorRegistry.RENDELITHIC_CHESTPLATE.get().getRegistryName().getPath();
    }

    @Override
    public String getLeggingsName()
    {
        return ArmorRegistry.RENDELITHIC_LEGGINGS.get().getRegistryName().getPath();
    }

    @Override
    public String getBootsName()
    {
        return ArmorRegistry.RENDELITHIC_BOOTS.get().getRegistryName().getPath();
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
        if (player.isNether())
            return 5;
        else if (player.isEnd())
            return 5;
        else
            return 4;
    }

    private void checkForNausea()
    {
        Player entity = player.getEntity();
        if (entity.isInWater())
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
        float helmetProtection = player.isArmorElementPutOn(getHelmetName()) ? initialDamageAmount * 0.2f : 0;
        float bootsProtection = player.isArmorElementPutOn(getBootsName()) ? initialDamageAmount * 0.2f : 0;
        float chestplateProtection = player.isArmorElementPutOn(getChestPlateName()) ? initialDamageAmount * 0.35f : 0;
        float leggingsProtection = player.isArmorElementPutOn(getLeggingsName()) ? initialDamageAmount * 0.3f : 0;
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
}
