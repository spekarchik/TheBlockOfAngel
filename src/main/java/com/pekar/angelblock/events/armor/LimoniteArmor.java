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
    private final IArmorEffect nightVisionEffect;
    private final IArmorEffect glowingEffect;
    private final IArmorEffect luckEffect;
    private final IArmorEffect healthBoostEffect;
    private final IArmorEffect regenerationEffect;
    private final IArmorEffect slownessEffect;
    private final IArmorEffect jumpNegativeEffect;
    private final SwitchingEffectSynchronizer jumpEffect;

    private static final int REGENERATION_EFFECT_DURATION = 300;
    private static final int MONSTER_SLOWDOWNED_EFFECT_DURATION = 100;
    private static final int REGENERATION_NEGATIVE_EFFECT_DURATION = 1200;
    private static final int ATTACKING_MONSTER_GLOWING_EFFECT_DURATION = 1200;
    private static final int JUMP_EFFECT_AMPLIFIER_DEFAULT = 2;
    private static final int JUMP_EFFECT_AMPLIFIER_BOOSTED = 6;

    public LimoniteArmor(IPlayer player)
    {
        super(player);

        nightVisionEffect = new NightVisionArmorEffect(player, this);
        glowingEffect = new GlowingArmorEffect(player, this);
        luckEffect = new LuckArmorEffect(player, this).setupAvailability(this::isLuckEffectAvailable);
        healthBoostEffect = new HealthBoostArmorEffect(player, this, 1);
        regenerationEffect = new RegenerationArmorEffect(player, this, 0, REGENERATION_EFFECT_DURATION);
        slownessEffect = new SlownessArmorEffect(player, this, 1, REGENERATION_NEGATIVE_EFFECT_DURATION);
        jumpNegativeEffect = new JumpNegativeArmorEffect(player, this, REGENERATION_NEGATIVE_EFFECT_DURATION);

        var jumpEffect = new JumpBoostArmorEffect(player, this, JUMP_EFFECT_AMPLIFIER_DEFAULT);
        jumpEffect.availableIfSlotsSet(EquipmentSlot.FEET, EquipmentSlot.LEGS);
        var speedEffect = new SpeedSwitchingEffect(player, this, 0);
        var slowFallingEffect = new SlowFallingSwitchingEffect(player, this);
        slowFallingEffect.setupAvailability(jumpEffect);

        this.jumpEffect = new SwitchingEffectSynchronizer(jumpEffect);
        this.jumpEffect.addDependentEffect(speedEffect);
        this.jumpEffect.addDependentEffect(slowFallingEffect);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        nightVisionEffect.updateSwitchState();
        glowingEffect.updateSwitchState();

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
        jumpEffect.updateEffectAvailability();
        nightVisionEffect.updateEffectAvailability();
        luckEffect.updateEffectAvailability();
        glowingEffect.updateEffectAvailability();
        healthBoostEffect.updateEffectAvailability();
        regenerationEffect.updateEffectAvailability();
        slownessEffect.updateEffectAvailability();
        jumpNegativeEffect.updateEffectAvailability();

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
            event.setDamageMultiplier(0);
        }
    }

    @Override
    public void onCreeperCheck()
    {
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
            if (regenerationEffect.isEffectAvailable() && player.getEntity().getHealth() < player.getEntity().getMaxHealth())
            {
                jumpEffect.trySwitchOff();
                slownessEffect.trySwitch();
                jumpNegativeEffect.trySwitch();

                if (!regenerationEffect.isActive())
                {
                    regenerationEffect.trySwitch();
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
        if (jumpEffect.isEffectOn())
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
        // none
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
        return ArmorRegistry.LIMONITE_BOOTS.get().getArmorFamilyName();
    }

    @Override
    public int getPriority()
    {
        return 4;
    }

    private void updatePotionEffects()
    {
        nightVisionEffect.updateEffectActivity();
        luckEffect.updateEffectActivity();
        glowingEffect.updateEffectActivity();
        healthBoostEffect.updateEffectActivity();
        regenerationEffect.updateEffectActivity();
        slownessEffect.updateEffectActivity();
        jumpNegativeEffect.updateEffectActivity();

        if (!slownessEffect.isActive())
        {
            jumpEffect.updateEffectActivity(getJumpEffectAmplifier());
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
        return player.isFullArmorSetPutOn(armor) && player.isChestPlateModifiedWithLuck(armor);
    }
}
