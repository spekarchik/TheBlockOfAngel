package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.damagesource.DamageTypes;
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
    private final ITemporaryPersistentArmorEffect jumpNegativeEffect;
    private final ISwitchingArmorEffect nightVisionEffect;
    private final ISwitchingArmorEffect glowingEffect;
    private final ISwitchingEffectSynchronizer jumpEffect;

    private boolean hasWaterBreathingBeenUsed;

    private static final int HEAL_REGENERATION_EFFECT_DURATION = 300;
    private static final int MONSTER_SLOWDOWNED_EFFECT_DURATION = 100;
    private static final int REGENERATION_NEGATIVE_EFFECT_DURATION = 1200;
    private static final int ATTACKING_MONSTER_GLOWING_EFFECT_DURATION = 400;
    private static final int JUMP_EFFECT_AMPLIFIER_DEFAULT = 2;
    private static final int JUMP_EFFECT_AMPLIFIER_BOOSTED = 6;
    private static final int WATER_BREATHING_EFFECT_DURATION = 600;

    public LimoniteArmor(IPlayer player)
    {
        super(player);

        nightVisionEffect = new NightVisionSwitchingArmorEffect(player, this).availableOnHelmetWithDetector().asArmorEffect();
        glowingEffect = new GlowingSwitchingArmorEffect(player, this).availableIfSlotSet(EquipmentSlot.CHEST).asArmorEffect();
        luckEffect = new LuckPermanentArmorEffect(player, this).setupAvailability(this::isLuckEffectAvailable).asArmorEffect();
        healthBoostEffect = new HealthBoostPermanentArmorEffect(player, this, 1);
        regenerationEffect = new RegenerationTemporaryArmorEffect(player, this, 0, HEAL_REGENERATION_EFFECT_DURATION);
        jumpNegativeEffect = new JumpNegativeArmorEffect(player, this, 1, REGENERATION_NEGATIVE_EFFECT_DURATION);
        waterBreathingEffect = new WaterBreathingTemporaryEffect(player, this, 0, WATER_BREATHING_EFFECT_DURATION);

        var jumpEffect = new JumpBoostSwitchingArmorEffect(player, this, JUMP_EFFECT_AMPLIFIER_DEFAULT);
        jumpEffect.setupAvailability(this::availableOnBootsWithNoHeavyJump);
        var speedEffect = new SpeedSwitchingEffect(player, this, 0);
        var slowFallingEffect = new SlowFallingSwitchingEffect(player, this);
        slowFallingEffect.availableIfSlotSet(EquipmentSlot.CHEST);

        this.jumpEffect = new SwitchingEffectSynchronizer(jumpEffect);
        this.jumpEffect.addDependentEffect(speedEffect);
        this.jumpEffect.addDependentEffect(slowFallingEffect);
    }

    @Override
    protected void updateAvailability()
    {
        jumpNegativeEffect.updateAvailability();

        nightVisionEffect.updateAvailability();
        waterBreathingEffect.updateAvailability();
        luckEffect.updateAvailability();
        glowingEffect.updateAvailability();
        healthBoostEffect.updateAvailability();
        regenerationEffect.updateAvailability();
        jumpEffect.updateAvailability();
    }

    @Override
    protected void updateEffectStates()
    {
        nightVisionEffect.updateSwitchState();
        glowingEffect.updateSwitchState();
        waterBreathingEffect.updateSwitchState();

        if (!jumpNegativeEffect.isAnyActive())
        {
            jumpEffect.updateSwitchState();
        }
    }

    @Override
    protected void updateActivity(EquipmentSlot slot)
    {
        jumpNegativeEffect.updateActivity();
    }

    @Override
    public void updateActivityForFeetSlot()
    {
        if (!jumpNegativeEffect.isActive())
        {
            jumpEffect.updateActivity(getJumpEffectAmplifier());
        }
        else
        {
            jumpEffect.updateDependentEffectsActivity();
        }
    }

    @Override
    public void updateActivityForLegsSlot()
    {
        healthBoostEffect.updateActivity();
        regenerationEffect.updateActivity();
    }

    @Override
    public void updateActivityForChestSlot()
    {
        luckEffect.updateActivity();
        glowingEffect.updateActivity();
    }

    @Override
    public void updateActivityForHeadSlot()
    {
        nightVisionEffect.updateActivity();
        waterBreathingEffect.updateActivity();
    }

    @Override
    protected void onEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        var entityPlayer = player.getEntity();
        if (playerNeedsToRestoreHealth(entityPlayer, event.getSlot(), event.getFrom(), event.getTo()))
        {
            restorePlayerHealth(entityPlayer);
        }
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        var damageSource = event.getSource();
        boolean isAnyArmorElementPutOn = player.isAnyArmorElementPutOn(this);

        if (isFreezeDamage(damageSource))
        {
            event.setCanceled(isAnyArmorElementPutOn);
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
                var random = player.getEntity().getRandom();
                if (random.nextFloat() < 0.4f)
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
                event.setNewDamage(damageAmount * 0.5F);
            }
        }
        else if (isVulnerable(damageSource))
        {
            event.setNewDamage(event.getNewDamage() * 1.5F);
        }
        else if (damageSource.is(DamageTypes.WIND_CHARGE))
        {
            event.setNewDamage(event.getNewDamage() * 3.0F);
        }
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
            if (waterBreathingEffect.isAvailable() && waterBreathingEffect.isActive())
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
            if (regenerationEffect.isAvailable() && !regenerationEffect.isAnyActive() && player.getEntity().getHealth() < player.getEntity().getMaxHealth())
            {
                jumpEffect.trySwitchOff();
                jumpNegativeEffect.tryActivate();

                regenerationEffect.tryActivate();
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.NIGHT_VISION.getName()))
        {
            nightVisionEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
        {
            glowingEffect.trySwitch();
            // for tests
            //switchArmorDamage();
        }

        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            if (!jumpNegativeEffect.isAnyActive())
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
        if (!hasWaterBreathingBeenUsed && !waterBreathingEffect.isAnyActive() && player.getEntity().isUnderWater())
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

        if (entityPlayer.getHealth() < 10F && entityPlayer.getFoodData().getFoodLevel() > 0)
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

    private int getJumpEffectAmplifier()
    {
        return player.areBootsModifiedWithJumpBooster(this) ? JUMP_EFFECT_AMPLIFIER_BOOSTED : JUMP_EFFECT_AMPLIFIER_DEFAULT;
    }

    private boolean isLuckEffectAvailable(IPlayer player, IArmor armor)
    {
        return player.isChestPlateModifiedWithLuck(armor);
    }
}
