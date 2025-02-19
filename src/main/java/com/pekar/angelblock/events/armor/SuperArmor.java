package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class SuperArmor extends Armor
{
    private final IArmorEffect nightVisionEffect;
    private final SwitchingEffectSynchronizer jumpEffect;
    private final IArmorEffect glowingEffect;
    private final IArmorEffect luckEffect;
    private final IArmorEffect regenerationEffect;
    private final IArmorEffect healthBoostEffect;
    private final IArmorEffect slownessEffect;
    private final IArmorEffect jumpNegativeEffect;
    private final IArmorEffect levitationEffect;
    private final IArmorEffect slowFallingEffect;
    private final IArmorEffect superJumpEffect;
    private final IArmorEffect dolphinsGraceEffect;
    private boolean isSlowFallingActivatedOnGround = true;

    private static final int REGENERATION_EFFECT_DURATION = 300;
    private static final int SLOWNESS_EFFECT_DURATION = 600;
    private static final int MONSTER_SLOWDOWNED_EFFECT_DURATION = 100;
    private static final int ATTACKING_MONSTER_GLOWING_EFFECT_DURATION = 1200;

    private static final int LEVITATION_UP_AMPLIFIER = 3;

    public SuperArmor(IPlayer player)
    {
        super(player);
        nightVisionEffect = new NightVisionArmorEffect(player, this).availableIfSlotSet(EquipmentSlot.HEAD);
        glowingEffect = new GlowingArmorEffect(player, this);

        luckEffect = new LuckArmorEffect(player, this);
        regenerationEffect = new RegenerationArmorEffect(player, this, 1, REGENERATION_EFFECT_DURATION);
        slownessEffect = new SlownessArmorEffect(player, this, 2, SLOWNESS_EFFECT_DURATION).availableOnAnyArmorElement();
        jumpNegativeEffect = new JumpNegativeArmorEffect(player, this, SLOWNESS_EFFECT_DURATION).availableOnFullArmorSet();
        healthBoostEffect = new HealthBoostArmorEffect(player, this, 2);
        levitationEffect = new LevitationSwitchingEffect(player, this, LEVITATION_UP_AMPLIFIER).availableOnFullArmorSet();
        slowFallingEffect = new SlowFallingSwitchingEffect(player, this).availableOnFullArmorSet();
        superJumpEffect = new SuperJumpSwitchingEffect(player, this).setupAvailability(this::isSuperJumpEffectAvailable);
        dolphinsGraceEffect = new DolphinsGraceEffect(player, this);

        var jumpEffect = new JumpBoostArmorEffect(player, this, 5);
        jumpEffect.availableIfSlotsSet(EquipmentSlot.FEET, EquipmentSlot.LEGS);
        var speedEffect = new SpeedSwitchingEffect(player, this, 1);
        speedEffect.availableIfSlotsSet(EquipmentSlot.FEET, EquipmentSlot.LEGS);
        var strengthEffect = new StrengthSwitchingEffect(player, this, 2);
        var waterBreathingEffect = new WaterBreathSwitchingEffect(player, this);
        var hasteEffect = new HasteSwitchingEffect(player, this, 1);
        hasteEffect.availableIfSlotSet(EquipmentSlot.CHEST);

        this.jumpEffect = new SwitchingEffectSynchronizer(jumpEffect);
        this.jumpEffect.addDependentEffect(speedEffect);
        this.jumpEffect.addDependentInvertedEffect(strengthEffect);
        this.jumpEffect.addDependentInvertedEffect(waterBreathingEffect);
        this.jumpEffect.addDependentInvertedEffect(hasteEffect);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        isSlowFallingActivatedOnGround = player.getEntity().onGround();

        nightVisionEffect.updateSwitchState();
        glowingEffect.updateSwitchState();

        if (!slownessEffect.isActive())
        {
            jumpEffect.updateSwitchState();
            levitationEffect.updateSwitchState();
            slowFallingEffect.updateSwitchState();
            superJumpEffect.updateSwitchState();
            dolphinsGraceEffect.updateSwitchState();
        }
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        DamageSource damageSource = event.getSource();

        if (isFireOrHotFloorDamage(damageSource))
        {
            float realDamage = getRealDamage(event.getAmount());
            event.setAmount(realDamage);
            event.setCanceled(realDamage <= 0);
        }
        else if (isFreezeDamage(damageSource))
        {
            boolean areBootsWorn = player.isArmorElementPutOn(this, EquipmentSlot.FEET);
            event.setCanceled(areBootsWorn);
        }
        else if (hasImmunity(damageSource) && player.isArmorModifiedWithHealthRegenerator(this))
        {
            event.setCanceled(true);
        }
        else if (damageSource.is(DamageTypes.WITHER) && player.isArmorModifiedWithHealthRegenerator(this))
        {
            event.setCanceled(true);
            player.getEntity().removeEffect(MobEffects.WITHER);
        }
        else
        {
            boolean isFullArmorSet = player.isFullArmorSetPutOn(this);
            if (isFullArmorSet)
            {
                if (isExplosionDamage(damageSource))
                {
                    event.setAmount(event.getAmount() * 0.5f);
                }
                else if (isLightningBoltDamage(damageSource))
                {
                    event.setCanceled(true);
                }
                else
                {
                    var attacker = damageSource.getEntity();

                    if (isBiting(attacker))
                    {
                        float damageAmount = event.getAmount();
                        event.setAmount(damageAmount * 0.2F);
                    }
                }

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

        }
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        jumpEffect.updateEffectAvailability();
        nightVisionEffect.updateEffectAvailability();
        luckEffect.updateEffectAvailability();
        glowingEffect.updateEffectAvailability();
        regenerationEffect.updateEffectAvailability();
        healthBoostEffect.updateEffectAvailability();
        slownessEffect.updateEffectAvailability();
        jumpNegativeEffect.updateEffectAvailability();
        levitationEffect.updateEffectAvailability();
        slowFallingEffect.updateEffectAvailability();
        superJumpEffect.updateEffectAvailability();
        dolphinsGraceEffect.updateEffectAvailability();

        updatePotionEffects();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        updateSlowFallingEffect();

        if (!player.isArmorElementPutOn(this, EquipmentSlot.LEGS)) return;

        if (jumpEffect.isActive() || slownessEffect.isActive()) return;

        if (slowFallingEffect.isEffectOn() && slowFallingEffect.isActive())
        {
            player.setEffect(MobEffects.JUMP, 30, 6);
        }
        else if (superJumpEffect.isEffectOn() && superJumpEffect.isActive())
        {
            player.setEffect(MobEffects.JUMP, 20, 30);
        }
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
        if (!player.isArmorElementPutOn(this, EquipmentSlot.FEET)) return;

        if (superJumpEffect.isEffectOn() && superJumpEffect.isActive())
        {
            event.setDamageMultiplier(0.3f);
        }
        else if (jumpEffect.isEffectOn() && jumpEffect.isActive())
        {
            event.setDamageMultiplier(0.3f);
        }
        else
        {
            event.setDamageMultiplier(0.6f);
        }
    }

    @Override
    public void onCreeperCheck()
    {
        updateSlowFallingEffect();

        boolean isHelmetModifiedWithDetector = player.isArmorModifiedWithDetector(this);

        detectCreepers(isHelmetModifiedWithDetector, false);
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        if (pressedKeyDescription.equals(KeyRegistry.NIGHT_VISION.getName()))
        {
            nightVisionEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
        {
            glowingEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.REGENERATION.getName()))
        {
            if (regenerationEffect.isEffectAvailable() && player.getEntity().getHealth() < player.getEntity().getMaxHealth())
            {
                slownessEffect.trySwitch();
                jumpEffect.trySwitchOff();
                levitationEffect.trySwitchOff();
                slowFallingEffect.trySwitchOff();
                jumpNegativeEffect.trySwitch();

                if (!regenerationEffect.isActive())
                {
                    regenerationEffect.trySwitch();

                    if (regenerationEffect.isActive() && !player.getEntity().onGround() && slowFallingEffect.isEffectAvailable())
                    {
                        slowFallingEffect.trySwitchOn();
                        isSlowFallingActivatedOnGround = false;
                    }
                }
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            if (!slownessEffect.isActive() && jumpEffect.isEffectAvailable())
            {
                jumpEffect.trySwitch();
            }

            slowFallingEffect.trySwitchOff();
            levitationEffect.trySwitchOff();
        }

        if (pressedKeyDescription.equals(KeyRegistry.LEVITATION.getName()))
        {
            if (!slownessEffect.isActive())
            {
                if (jumpEffect.isEffectAvailable() && jumpEffect.isEffectOn())
                {
                    levitationEffect.trySwitch(getLevitationAmplifier());
                }
                else
                {
                    slowFallingEffect.trySwitch();

                    if (slowFallingEffect.isEffectOn())
                        isSlowFallingActivatedOnGround = player.getEntity().onGround();
                    else
                        isSlowFallingActivatedOnGround = true;
                }
            }

            if (slowFallingEffect.isActive())
            {
                player.getEntity().stopFallFlying();
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.SUPER_JUMP.getName()))
        {
            superJumpEffect.trySwitch();
            dolphinsGraceEffect.trySwitch();
        }

        if (levitationEffect.isEffectOn())
        {
            slowFallingEffect.trySwitchOff();
        }

        if (slowFallingEffect.isEffectOn())
        {
            levitationEffect.trySwitchOff();
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
        if (jumpEffect.isEffectOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }
    }

    @Override
    public void onBeingInWater()
    {
        if (slowFallingEffect.isEffectOn())
            slowFallingEffect.trySwitchOff();
    }

    @Override
    public void onBeingUnderRain()
    {
        if (!player.isFullArmorSetPutOn(this)) return;

        if (player.getEntity().getHealth() < player.getEntity().getMaxHealth())
        {
            regenerationEffect.trySwitch();
        }
    }

    @Override
    public String getFamilyName()
    {
        return ArmorRegistry.SUPER_BOOTS.get().getArmorFamilyName();
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

    private void updatePotionEffects()
    {
        nightVisionEffect.updateEffectActivity();
        luckEffect.updateEffectActivity();
        glowingEffect.updateEffectActivity();
        regenerationEffect.updateEffectActivity();
        healthBoostEffect.updateEffectActivity();
        slownessEffect.updateEffectActivity();
        jumpNegativeEffect.updateEffectActivity();
        superJumpEffect.updateEffectActivity();
        dolphinsGraceEffect.updateEffectActivity();

        levitationEffect.updateEffectActivity(getLevitationAmplifier());
        slowFallingEffect.updateEffectActivity();

        if (!slownessEffect.isActive())
        {
            jumpEffect.updateEffectActivity();
        }
        else
        {
            jumpEffect.updateDependentEffectsActivity();
        }
    }

    private boolean hasImmunity(DamageSource damageSource)
    {
        return isThornOrMagicDamage(damageSource) || damageSource.is(DamageTypes.DRAGON_BREATH);
    }

    private int getLevitationAmplifier()
    {
        return LEVITATION_UP_AMPLIFIER;
    }

    private boolean isSuperJumpEffectAvailable(IPlayer player, IArmor armor)
    {
        if (!player.isFullArmorSetPutOn(this) || !player.areBootsModifiedWithStrengthBooster(this))
            return false;

        var boots = player.getEntity().getItemBySlot(EquipmentSlot.FEET);
        var leggings = player.getEntity().getItemBySlot(EquipmentSlot.LEGS);

        int bootsDamage = boots.getDamageValue();
        int leggingsDamage = leggings.getDamageValue();
        int maxBootsDamageToJump = boots.getMaxDamage() / 2;
        int maxLeggingsDamageToJump = leggings.getMaxDamage() / 2;

        return bootsDamage < maxBootsDamageToJump && leggingsDamage < maxLeggingsDamageToJump;
    }

    private void updateSlowFallingEffect()
    {
        if (!isSlowFallingActivatedOnGround && slowFallingEffect.isEffectOn() && player.getEntity().onGround())
        {
            slowFallingEffect.trySwitchOff();
            isSlowFallingActivatedOnGround = true;
        }
    }
}
