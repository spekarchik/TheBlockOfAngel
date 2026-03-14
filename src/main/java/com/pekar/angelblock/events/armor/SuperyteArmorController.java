package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.PlayerArmorType;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.effect.base.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Witch;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class SuperyteArmorController extends PlayerArmor
{
    private final ISwitchingArmorEffect nightVisionEffect;
    private final ISwitchingEffectSynchronizer jumpEffect;
    private final ISwitchingArmorEffect glowingEffect;
    private final IPermanentArmorEffect luckEffect;
    private final IPermanentArmorEffect waterBreathingEffect;
    private final IPermanentArmorEffect hasteEffect;
    private final ITemporaryArmorEffect regenerationEffect;
    private final IPermanentArmorEffect healthBoostEffect;
    private final ITemporaryPersistentArmorEffect jumpNegativeEffect;
    private final ISwitchingArmorEffect levitationEffect;
    private final ISwitchingArmorEffect slowFallingEffect;
    private final ISwitchingEffectSynchronizer superJumpEffect;

    private boolean isSlowFallingActivatedOnGround = true;

    private static final int HEAL_REGENERATION_EFFECT_DURATION = 300;
    private static final int REGENERATION_EFFECT_UNDER_RAIN_AMPLIFIER = 0;
    private static final int REGENERATION_EFFECT_HEAL_AMPLIFIER = 1;
    private static final int SLOWNESS_EFFECT_DURATION = 600;
    private static final int MONSTER_SLOWDOWNED_EFFECT_DURATION = 100;
    private static final int ATTACKING_MONSTER_GLOWING_EFFECT_DURATION = 1200;

    private static final int LEVITATION_UP_AMPLIFIER = 3;
    private static final int SUPER_JUMP_AMPLIFIER = 30;

    public SuperyteArmorController(IPlayer player)
    {
        super(player, PlayerArmorType.SUPERYTE);

        nightVisionEffect = new NightVisionSwitchingArmorEffect(player, this);
        nightVisionEffect.setup().setupAvailability(this::isNightVisionAvailable);
        glowingEffect = new GlowingSwitchingArmorEffect(player, this);
        glowingEffect.setup().availableIfSlotsSet(EquipmentSlot.CHEST);

        luckEffect = new LuckPermanentArmorEffect(player, this);
        luckEffect.setup().availableIfSlotSet(EquipmentSlot.CHEST);
        regenerationEffect = new RegenerationTemporaryArmorEffect(player, this, REGENERATION_EFFECT_HEAL_AMPLIFIER, HEAL_REGENERATION_EFFECT_DURATION);
        jumpNegativeEffect = new JumpNegativeArmorEffect(player, this, 2, SLOWNESS_EFFECT_DURATION);
        healthBoostEffect = new HealthBoostPermanentArmorEffect(player, this, 2);
        levitationEffect = new LevitationSwitchingEffect(player, this, LEVITATION_UP_AMPLIFIER);
        levitationEffect.setup().availableIfSlotSet(EquipmentSlot.CHEST);
        slowFallingEffect = new SlowFallingSwitchingEffect(player, this);
        slowFallingEffect.setup().availableIfSlotSet(EquipmentSlot.CHEST);

        var superJumpEffect = new SuperJumpSwitchingEffect(player, this);
        superJumpEffect.setupAvailability(this::isSuperJumpEffectAvailable);
        var dolphinsGraceEffect = new DolphinsGraceSwitchingEffect(player, this);
        this.superJumpEffect = new SwitchingEffectSynchronizer(superJumpEffect);
        this.superJumpEffect.addDependentEffect(dolphinsGraceEffect);

        var jumpEffect = new JumpBoostSwitchingArmorEffect(player, this, 5);
        jumpEffect.setupAvailability(this::availableOnBootsWithNoHeavyJump);
        var speedEffect = new SpeedSwitchingEffect(player, this, 1);
        speedEffect.setupAvailability(this::isSpeedEffectAvailable);
        var strengthEffect = new StrengthSwitchingEffect(player, this, 2);
        waterBreathingEffect = new WaterBreathingPermanentEffect(player, this);
        hasteEffect = new HastePermanentArmorEffect(player, this);
        hasteEffect.setup().availableIfSlotSet(EquipmentSlot.CHEST);

        this.jumpEffect = new SwitchingEffectSynchronizer(jumpEffect);
        this.jumpEffect.addDependentEffect(speedEffect);
        this.jumpEffect.addDependentInvertedEffect(strengthEffect);
    }

    @Override
    protected void onLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        isSlowFallingActivatedOnGround = player.getPlayerEntity().onGround();
    }

    @Override
    protected void updateAvailability()
    {
        jumpNegativeEffect.updateAvailability();
        waterBreathingEffect.updateAvailability();
        hasteEffect.updateAvailability();

        slowFallingEffect.updateAvailability();
        glowingEffect.updateAvailability();
        levitationEffect.updateAvailability();
        luckEffect.updateAvailability();
        healthBoostEffect.updateAvailability();
        regenerationEffect.updateAvailability();
        nightVisionEffect.updateAvailability();

        jumpEffect.updateAvailability();
        superJumpEffect.updateAvailability();
    }

    @Override
    protected void updateEffectStates()
    {
        nightVisionEffect.updateSwitchState();
        glowingEffect.updateSwitchState();

        if (!jumpNegativeEffect.isAnyActive())
        {
            jumpEffect.updateSwitchState();
            levitationEffect.updateSwitchState();
            slowFallingEffect.updateSwitchState();
            superJumpEffect.updateSwitchState();
        }
    }

    @Override
    protected void updateActivityForHeadSlot()
    {
        nightVisionEffect.updateActivity();
        waterBreathingEffect.updateActivity();
    }

    @Override
    protected void updateActivityForFeetSlot()
    {

    }

    @Override
    protected void updateActivityForLegsSlot()
    {
        healthBoostEffect.updateActivity();
        regenerationEffect.updateActivity();
    }

    @Override
    protected void updateActivityForChestSlot()
    {
        slowFallingEffect.updateActivity();
        glowingEffect.updateActivity();
        levitationEffect.updateActivity(LEVITATION_UP_AMPLIFIER);
        luckEffect.updateActivity();
        hasteEffect.updateActivity();
    }

    @Override
    protected void updateActivity(EquipmentSlot slot)
    {
        superJumpEffect.updateActivity();
        if (!jumpNegativeEffect.isActive())
        {
            jumpEffect.updateActivity();
        }
        else
        {
            jumpEffect.updateDependentEffectsActivity();
        }
    }

    @Override
    protected void onEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        var entityPlayer = player.getPlayerEntity();
        if (playerNeedsToRestoreHealth(entityPlayer, event.getSlot(), event.getFrom(), event.getTo()))
        {
            restorePlayerHealth(entityPlayer);
        }
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        DamageSource damageSource = event.getSource();
        boolean isFullArmorSet = player.isFullArmorSetPutOn(this);

        if (isFireDamage(damageSource))
        {
            if (!isStandingInSoulFire())
            {
                float realDamage = getRealDamage(event.getAmount());
                event.setCanceled(realDamage <= 0);
            }

            if (isFullArmorSet)
                event.getEntity().clearFire();
        }
        else if (isHotFloorDamage(damageSource) || isFreezeDamage(damageSource))
        {
            boolean areBootsWorn = player.isArmorElementPutOn(this, EquipmentSlot.FEET);
            event.setCanceled(areBootsWorn);
        }
        else if (hasImmunity(damageSource))
        {
            event.setCanceled(player.areLeggingsModifiedWithHealthRegenerator(this));
        }
        else if (damageSource.is(DamageTypes.WITHER))
        {
            if (player.areLeggingsModifiedWithHealthRegenerator(this))
            {
                event.setCanceled(true);
                player.getPlayerEntity().removeEffect(MobEffects.WITHER);
            }
        }
        else if (isLightningBoltDamage(damageSource))
        {
            if (isFullArmorSet)
                event.setCanceled(true);
        }

        if (player.isEffectActive(MobEffects.POISON) && player.areLeggingsModifiedWithHealthRegenerator(this))
        {
            player.clearEffect(MobEffects.POISON);
        }

        if (isFullArmorSet)
        {
            if (!(damageSource.getEntity() instanceof LivingEntity entityAttackedBy)) return;

            if (isSlowMovementAffected(entityAttackedBy))
            {
                boolean isWitch = entityAttackedBy instanceof Witch;
                float distance = player.getPlayerEntity().distanceTo(entityAttackedBy);
                if (!isWitch && distance > 2f)
                {
                    var random = player.getPlayerEntity().getRandom();
                    if (random.nextFloat() < 0.4f)
                        entityAttackedBy.setRemainingFireTicks(5 * Utils.TICKS_PER_SECOND);
                }
                else
                {
                    entityAttackedBy.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, MONSTER_SLOWDOWNED_EFFECT_DURATION, 2));
                }
            }

            var effect = new MobEffectInstance(MobEffects.GLOWING, ATTACKING_MONSTER_GLOWING_EFFECT_DURATION, 0, false, false, false);
            entityAttackedBy.addEffect(effect);
        }
    }

    @Override
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
        DamageSource damageSource = event.getSource();
        boolean isFullArmorSet = player.isFullArmorSetPutOn(this);

        if (isFireDamage(damageSource) && !isStandingInSoulFire())
        {
            float realDamage = getRealDamage(event.getNewDamage());
            event.setNewDamage(realDamage);
        }
        else if (isLavaDamage(damageSource))
        {
            if (isFullArmorSet)
            {
                event.setNewDamage(0.05f * event.getNewDamage());
            }
        }
        else if (isExplosionDamage(damageSource))
        {
            if (player.isChestPlateModifiedWithStrengthBooster(this))
                event.setNewDamage(event.getNewDamage() * 0.5f);
        }
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        updateSlowFallingEffect();

        if (!player.isArmorElementPutOn(this, EquipmentSlot.FEET)) return;

        if (jumpEffect.isMasterActive() || jumpNegativeEffect.isAnyActive()) return;

        if (slowFallingEffect.isOn() && slowFallingEffect.isActive())
        {
            player.setEffect(MobEffects.JUMP_BOOST, 30, 6);
        }
        else if (superJumpEffect.isMasterActive())
        {
            player.setEffect(MobEffects.JUMP_BOOST, 20, SUPER_JUMP_AMPLIFIER);
        }
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
    }

    @Override
    public void onHeavyTick()
    {
        updateSlowFallingEffect();

        var playerEntity = player.getPlayerEntity();
        if (playerEntity.isFallFlying() && !canFly())
        {
            playerEntity.stopFallFlying();
        }

        boolean isHelmetModifiedWithDetector = player.isHelmetModifiedWithDetector(this);

        detectCreepers(isHelmetModifiedWithDetector, false);
    }

    @Override
    public void onEffectAddedEvent(MobEffectEvent.Added event)
    {
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        var playerEntity = player.getPlayerEntity();

        if (pressedKeyDescription.equals(KeyRegistry.NIGHT_VISION.getName()))
        {
            nightVisionEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
        {
            glowingEffect.trySwitch();
            // for tests
            //switchArmorDamage();
            //damageMainHandItem();
        }

        if (pressedKeyDescription.equals(KeyRegistry.REGENERATION.getName()))
        {
            if (regenerationEffect.isAvailable() && !regenerationEffect.isAnyActive() && playerEntity.getHealth() < playerEntity.getMaxHealth())
            {
                jumpEffect.trySwitchOff();
                levitationEffect.trySwitchOff();
                slowFallingEffect.trySwitchOff();
                jumpNegativeEffect.tryActivate();

                regenerationEffect.tryActivate();

                if (regenerationEffect.isActive() && !playerEntity.onGround() && slowFallingEffect.isAvailable())
                {
                    slowFallingEffect.trySwitchOn();
                    isSlowFallingActivatedOnGround = false;
                }
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            if (!jumpNegativeEffect.isAnyActive())
            {
                jumpEffect.trySwitch();
            }

            if (jumpEffect.isMasterActive() && slowFallingEffect.isActive() && player.getPlayerEntity().onGround() && !levitationEffect.isAnyActive())
            {
                slowFallingEffect.trySwitchOff();
                levitationEffect.trySwitchOn();
            }
            else
            {
                if (!jumpEffect.isMasterActive() && levitationEffect.isActive())
                {
                    levitationEffect.trySwitchOff();

                    if (slowFallingEffect.isAvailable())
                    {
                        slowFallingEffect.trySwitchOn();
                        isSlowFallingActivatedOnGround = playerEntity.onGround();
                    }
                }
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.LEVITATION.getName()))
        {
            if (!levitationEffect.isOn() && levitationEffect.isAnyActive() && levitationEffect.isInfinite())
                levitationEffect.forceRemove();

            if (!jumpNegativeEffect.isAnyActive())
            {
                if (jumpEffect.isMasterAvailable() && jumpEffect.isOn())
                {
                    if (levitationEffect.isAvailable() && levitationEffect.isActive())
                    {
                        jumpEffect.trySwitchOff();
                        levitationEffect.trySwitchOff();

                        if (slowFallingEffect.isAvailable())
                        {
                            slowFallingEffect.trySwitchOn();
                            isSlowFallingActivatedOnGround = playerEntity.onGround();
                        }
                    }
                    else if (!playerEntity.onGround())
                    {
                        if (slowFallingEffect.isAvailable())
                        {
                            slowFallingEffect.trySwitchOn();
                            isSlowFallingActivatedOnGround = false;
                        }
                    }
                    else if (!playerEntity.isInLava() && !levitationEffect.isAnyActive())
                    {
                        levitationEffect.trySwitch(LEVITATION_UP_AMPLIFIER);
                    }
                }
                else
                {
                    slowFallingEffect.trySwitch();

                    if (slowFallingEffect.isOn())
                        isSlowFallingActivatedOnGround = playerEntity.onGround();
                    else
                        isSlowFallingActivatedOnGround = true;
                }
            }

            if (slowFallingEffect.isActive())
            {
                playerEntity.stopFallFlying();
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.SUPER_JUMP.getName()))
        {
            superJumpEffect.trySwitch();
        }

        if (levitationEffect.isOn())
        {
            slowFallingEffect.trySwitchOff();
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        // none, until we don't need to change effect amplifiers between dimensions
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
        if (slowFallingEffect.isOn())
        {
            slowFallingEffect.trySwitchOff();
            isSlowFallingActivatedOnGround = true;
        }
    }

    @Override
    public void onBeingInWater()
    {
        if (slowFallingEffect.isOn())
        {
            slowFallingEffect.trySwitchOff();
            isSlowFallingActivatedOnGround = true;
        }
    }

    @Override
    public void onBeingUnderRain()
    {
        if (!player.isFullArmorSetPutOn(this)) return;
        var entityPlayer = player.getPlayerEntity();

        if (entityPlayer.getHealth() < 10F && entityPlayer.getFoodData().getFoodLevel() > 0)
        {
            regenerationEffect.tryActivate(REGENERATION_EFFECT_UNDER_RAIN_AMPLIFIER, UNDER_RAIN_REGENERATION_EFFECT_DURATION);
            entityPlayer.causeFoodExhaustion(EXHAUSTION_INCREMENT);
        }
    }

    @Override
    public int getPriority()
    {
        return 6;
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

    private boolean hasImmunity(DamageSource damageSource)
    {
        return isThornOrMagicDamage(damageSource) || damageSource.is(DamageTypes.DRAGON_BREATH);
    }

    private boolean isSuperJumpEffectAvailable(IPlayer player, IArmor armor)
    {
        if (!player.areBootsModifiedWithJumpBooster(this)) return false;
        if (player.getPlayerEntity().hasEffect(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT)) return false;

        var boots = player.getPlayerEntity().getItemBySlot(EquipmentSlot.FEET);
        int bootsDamage = boots.getDamageValue();
        int maxBootsDamageToJump = boots.getMaxDamage() / 2;

        return bootsDamage < maxBootsDamageToJump;
    }

    private boolean canFly()
    {
        var playerEntity = player.getPlayerEntity();
        var chestplate = playerEntity.getItemBySlot(EquipmentSlot.CHEST);
        int maxDamageToFly = chestplate.getMaxDamage() / 2;
        int chestDamage = chestplate.getDamageValue();

        return chestDamage < maxDamageToFly;
    }

    private void updateSlowFallingEffect()
    {
        if (!isSlowFallingActivatedOnGround && slowFallingEffect.isOn() && player.getPlayerEntity().onGround())
        {
            slowFallingEffect.trySwitchOff();
            isSlowFallingActivatedOnGround = true;
        }
    }

    private boolean isNightVisionAvailable(IPlayer player, IPlayerArmor armor)
    {
        return player.isArmorElementPutOn(this, EquipmentSlot.HEAD) && !player.isEffectActive(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT);
    }

    private boolean isSpeedEffectAvailable(IPlayer player, IPlayerArmor playerArmor)
    {
        return availableOnBootsWithNoHeavyJump(player, playerArmor)
                && !player.isEffectActive(MobEffects.SLOWNESS)
                && !player.isEffectActive(PotionRegistry.ENERGY_CRYSTAL_EFFECT);
    }
}
