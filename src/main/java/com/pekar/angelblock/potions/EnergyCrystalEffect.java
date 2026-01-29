package com.pekar.angelblock.potions;

import com.pekar.angelblock.events.player.ModMobEffectInstance;
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
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier)
    {
        if (!entity.hasEffect(MobEffects.SPEED) || entity.getEffect(MobEffects.SPEED).getAmplifier() < SPEED_AMPLIFIER)
        {
            var speedEffectInstance = new ModMobEffectInstance(MobEffects.SPEED, MobEffectInstance.INFINITE_DURATION, SPEED_AMPLIFIER, false, true, false);
            entity.addEffect(speedEffectInstance, entity);
        }

        if (!entity.hasEffect(MobEffects.HASTE) || entity.getEffect(MobEffects.HASTE).getAmplifier() < HASTE_AMPLIFIER)
        {
            var hasteEffectInstance = new ModMobEffectInstance(MobEffects.HASTE, MobEffectInstance.INFINITE_DURATION, HASTE_AMPLIFIER, false, true, false);
            entity.addEffect(hasteEffectInstance);
        }

        return true;
    }

    @Override
    public void removeUnderlyingEffectFor(LivingEntity entity)
    {
        if (entity.hasEffect(MobEffects.SPEED))
        {
            var speedEffect = entity.getEffect(MobEffects.SPEED);
            if (speedEffect.getAmplifier() == SPEED_AMPLIFIER && speedEffect.isVisible())
                entity.removeEffect(MobEffects.SPEED);
        }

        if (entity.hasEffect(MobEffects.HASTE))
        {
            var hasteEffect = entity.getEffect(MobEffects.HASTE);
            if (hasteEffect.getAmplifier() == HASTE_AMPLIFIER && hasteEffect.isVisible())
                entity.removeEffect(MobEffects.HASTE);
        }
    }
}
