package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class LimoniteArmor extends Armor
{
    private final IPermanentArmorEffect luckEffect;
    private final IPermanentArmorEffect healthBoostEffect;
    private final ITemporaryArmorEffect regenerationEffect;
    private final ITemporaryArmorEffect waterBreathingEffect;
    private final ITemporaryPersistentArmorEffect slownessEffect;
    private final ITemporaryPersistentArmorEffect jumpNegativeEffect;
    private final ISwitchingArmorEffect nightVisionEffect;
    private final ISwitchingArmorEffect glowingEffect;
    private final ISwitchingEffectSynchronizer jumpEffect;

    private boolean hasWaterBreathingBeenUsed;

    private static final int HEAL_REGENERATION_EFFECT_DURATION = 300;
    private static final int MONSTER_SLOWDOWNED_EFFECT_DURATION = 100;
    private static final int REGENERATION_NEGATIVE_EFFECT_DURATION = 1200;
    private static final int ATTACKING_MONSTER_GLOWING_EFFECT_DURATION = 1200;
    private static final int JUMP_EFFECT_AMPLIFIER_DEFAULT = 2;
    private static final int JUMP_EFFECT_AMPLIFIER_BOOSTED = 6;
    private static final int WATER_BREATHING_EFFECT_DURATION = 600;

    public LimoniteArmor(IPlayer player)
    {
        super(player);

        nightVisionEffect = new NightVisionSwitchingArmorEffect(player, this);
        glowingEffect = new GlowingSwitchingArmorEffect(player, this).availableIfSlotSet(EquipmentSlot.CHEST);
        luckEffect = new LuckPermanentArmorEffect(player, this).setupAvailability(this::isLuckEffectAvailable);
        healthBoostEffect = new HealthBoostPermanentArmorEffect(player, this, 1);
        regenerationEffect = new RegenerationTemporaryArmorEffect(player, this, 0, HEAL_REGENERATION_EFFECT_DURATION);
        slownessEffect = new SlownessNegativeArmorEffect(player, this, 1, REGENERATION_NEGATIVE_EFFECT_DURATION);
        jumpNegativeEffect = new JumpNegativeArmorEffect(player, this, REGENERATION_NEGATIVE_EFFECT_DURATION);
        waterBreathingEffect = new WaterBreathingTemporaryEffect(player, this, 0, WATER_BREATHING_EFFECT_DURATION);

        var jumpEffect = new JumpBoostSwitchingArmorEffect(player, this, JUMP_EFFECT_AMPLIFIER_DEFAULT);
        jumpEffect.availableIfSlotSet(EquipmentSlot.FEET);
        var speedEffect = new SpeedSwitchingEffect(player, this, 0);
        var slowFallingEffect = new SlowFallingSwitchingEffect(player, this);
        slowFallingEffect.availableIfSlotSet(EquipmentSlot.CHEST);

        this.jumpEffect = new SwitchingEffectSynchronizer(jumpEffect);
        this.jumpEffect.addDependentEffect(speedEffect);
        this.jumpEffect.addDependentEffect(slowFallingEffect);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        nightVisionEffect.updateSwitchState();
        glowingEffect.updateSwitchState();
        waterBreathingEffect.updateSwitchState();

        if (!slownessEffect.isActive())
        {
            jumpEffect.updateSwitchState();
        }
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        var damageSource = event.getSource();

        if (isFreezeDamage(damageSource))
        {
            event.setCanceled(true);
        }
        else if (isThornOrMagicDamage(damageSource))
        {
            event.setCanceled(player.areLeggingsModifiedWithHealthRegenerator(this));
        }
        else if (player.isEffectActive(MobEffects.POISON) && player.areLeggingsModifiedWithHealthRegenerator(this))
        {
            player.clearEffect(MobEffects.POISON);
            event.setCanceled(damageSource.getMsgId().equals("magic")); // Bee's poison
        }

        boolean isFullArmorSet = player.isFullArmorSetPutOn(this);

        if (!isFullArmorSet) return;
        if (!(damageSource.getEntity() instanceof LivingEntity entityAttackedBy)) return;

        if (isSlowMovementAffected(entityAttackedBy))
        {
            boolean isWitch = entityAttackedBy instanceof Witch;
            float distance = player.getEntity().distanceTo(entityAttackedBy);
            if (!isWitch && distance > 2f)
            {
                entityAttackedBy.setRemainingFireTicks(5 * Utils.TICKS_PER_SECOND);
            }
            else
            {
                entityAttackedBy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, MONSTER_SLOWDOWNED_EFFECT_DURATION, 2));
            }
        }

        var effect = new MobEffectInstance(MobEffects.GLOWING, ATTACKING_MONSTER_GLOWING_EFFECT_DURATION, 0, false, false, false);
        entityAttackedBy.addEffect(effect);
    }

    @Override
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
        var damageSource = event.getSource();
        var attacker = damageSource.getEntity();

        if (isBiting(attacker))
        {
            if (player.isFullArmorSetPutOn(this))
            {
                float damageAmount = event.getNewDamage();
                event.setNewDamage(damageAmount * 0.2F);
            }
        }
        else if (isVulnerable(damageSource))
        {
            event.setNewDamage(event.getNewDamage() * 1.5F);
        }
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        jumpEffect.updateAvailability();
        nightVisionEffect.updateAvailability();
        luckEffect.updateAvailability();
        glowingEffect.updateAvailability();
        healthBoostEffect.updateAvailability();
        regenerationEffect.updateAvailability();
        slownessEffect.updateAvailability();
        jumpNegativeEffect.updateAvailability();
        waterBreathingEffect.updateAvailability();

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
        // none
    }

    @Override
    public void onCreeperCheck()
    {
        var playerEntity = player.getEntity();
        if (!playerEntity.isUnderWater())
        {
            hasWaterBreathingBeenUsed = false;
            if (waterBreathingEffect.isActive() && waterBreathingEffect.isArmorEffect())
            {
                waterBreathingEffect.tryRemove();
            }
        }

        boolean isHelmetModifiedWithDetector = player.isHelmetModifiedWithDetector(this);
        detectCreepers(isHelmetModifiedWithDetector, player.isFullArmorSetPutOn(this));
    }

    @Override
    public void onEffectAddedEvent(MobEffectEvent.Added event)
    {
        // none
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        if (pressedKeyDescription.equals(KeyRegistry.REGENERATION.getName()))
        {
            if (regenerationEffect.isAvailable() && player.getEntity().getHealth() < player.getEntity().getMaxHealth())
            {
                jumpEffect.trySwitchOff();
                slownessEffect.tryActivate();
                jumpNegativeEffect.tryActivate();

                if (!regenerationEffect.isActive())
                {
                    regenerationEffect.tryActivate();
                }
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.NIGHT_VISION.getName()))
        {
            nightVisionEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
        {
            glowingEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            if (!slownessEffect.isActive())
            {
                jumpEffect.trySwitch(getJumpEffectAmplifier());
            }
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        // none
    }

    @Override
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        // none
    }

    @Override
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        if (jumpEffect.isOn())
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
        if (!hasWaterBreathingBeenUsed && !waterBreathingEffect.isActive() && player.getEntity().isUnderWater())
        {
            hasWaterBreathingBeenUsed = true;
            waterBreathingEffect.tryActivate();
        }
    }

    @Override
    public void onBeingUnderRain()
    {
        if (!player.isFullArmorSetPutOn(this)) return;
        var entityPlayer = player.getEntity();

        if (entityPlayer.getHealth() < entityPlayer.getMaxHealth())
        {
            regenerationEffect.tryActivate(UNDER_RAIN_REGENERATION_EFFECT_DURATION);
            entityPlayer.causeFoodExhaustion(EXHAUSTION_INCREMENT);
        }
    }

    @Override
    public String getFamilyName()
    {
        return ArmorRegistry.LIMONITE_BOOTS.get().getArmorFamilyName();
    }

    @Override
    public int getPriority()
    {
        return 4;
    }

    private void updatePotionEffects()
    {
        nightVisionEffect.updateActivity();
        luckEffect.updateActivity();
        glowingEffect.updateActivity();
        healthBoostEffect.updateActivity();
        regenerationEffect.updateActivity();
        slownessEffect.updateActivity();
        jumpNegativeEffect.updateActivity();
        waterBreathingEffect.updateActivity();

        if (!slownessEffect.isActive())
        {
            jumpEffect.updateActivity(getJumpEffectAmplifier());
        }
        else
        {
            jumpEffect.updateDependentEffectsActivity();
        }
    }

    private int getJumpEffectAmplifier()
    {
        return player.areBootsModifiedWithJumpBooster(this) ? JUMP_EFFECT_AMPLIFIER_BOOSTED : JUMP_EFFECT_AMPLIFIER_DEFAULT;
    }

    private boolean isLuckEffectAvailable(IPlayer player, IArmor armor)
    {
        return player.isChestPlateModifiedWithLuck(armor);
    }
}
