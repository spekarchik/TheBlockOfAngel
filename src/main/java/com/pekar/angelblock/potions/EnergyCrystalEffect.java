package com.pekar.angelblock.potions;

import com.pekar.angelblock.events.mob.ModMobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class EnergyCrystalEffect extends ModMobEffect
{
    private final int SPEED_AMPLIFIER = 3;
    private final int HASTE_AMPLIFIER = 0;

    public EnergyCrystalEffect()
    {
        super(MobEffectCategory.NEUTRAL, 0xFFFF00); // Yellow color
    }

    @Override
    public boolean isInstantenous()
    {
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier)
    {
        return duration > 0 || duration == MobEffectInstance.INFINITE_DURATION;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier)
    {
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < SPEED_AMPLIFIER)
        {
            var speedEffectInstance = new ModMobEffectInstance(MobEffects.MOVEMENT_SPEED, MobEffectInstance.INFINITE_DURATION, SPEED_AMPLIFIER, false, true, false);
            entity.addEffect(speedEffectInstance, entity);
        }

        if (!entity.hasEffect(MobEffects.DIG_SPEED) || entity.getEffect(MobEffects.DIG_SPEED).getAmplifier() < HASTE_AMPLIFIER)
        {
            var hasteEffectInstance = new ModMobEffectInstance(MobEffects.DIG_SPEED, MobEffectInstance.INFINITE_DURATION, HASTE_AMPLIFIER, false, true, false);
            entity.addEffect(hasteEffectInstance);
        }

        return true;
    }

    @Override
    public void removeUnderlyingEffectFor(LivingEntity entity)
    {
        if (entity.hasEffect(MobEffects.MOVEMENT_SPEED))
        {
            var speedEffect = entity.getEffect(MobEffects.MOVEMENT_SPEED);
            if (speedEffect.getAmplifier() == SPEED_AMPLIFIER && speedEffect.isVisible())
                entity.removeEffect(MobEffects.MOVEMENT_SPEED);
        }

        if (entity.hasEffect(MobEffects.DIG_SPEED))
        {
            var hasteEffect = entity.getEffect(MobEffects.DIG_SPEED);
            if (hasteEffect.getAmplifier() == HASTE_AMPLIFIER && hasteEffect.isVisible())
                entity.removeEffect(MobEffects.DIG_SPEED);
        }
    }
}
