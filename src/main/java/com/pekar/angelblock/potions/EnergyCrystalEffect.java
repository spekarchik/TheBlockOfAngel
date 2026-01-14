package com.pekar.angelblock.potions;

import com.pekar.angelblock.events.player.ModMobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class EnergyCrystalEffect extends ModMobEffect
{
    private final int MOVEMENT_SPEED_AMPLIFIER = 3;

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
        if (entity.hasEffect(MobEffects.MOVEMENT_SPEED) && entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() >= MOVEMENT_SPEED_AMPLIFIER) return true;

        var nightVisionEffectInstance = new ModMobEffectInstance(MobEffects.MOVEMENT_SPEED, MobEffectInstance.INFINITE_DURATION, MOVEMENT_SPEED_AMPLIFIER, false, false, false);
        entity.addEffect(nightVisionEffectInstance, entity);

        return true;
    }

    @Override
    public void removeEffectFor(LivingEntity entity)
    {
        if (entity.hasEffect(MobEffects.MOVEMENT_SPEED))
        {
            entity.removeEffect(MobEffects.MOVEMENT_SPEED);
        }
    }
}
