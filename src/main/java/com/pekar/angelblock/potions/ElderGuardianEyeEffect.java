package com.pekar.angelblock.potions;

import com.pekar.angelblock.events.player.ModMobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class ElderGuardianEyeEffect extends ModMobEffect
{
    protected ElderGuardianEyeEffect()
    {
        super(MobEffectCategory.NEUTRAL, 0);
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
        if (entity.hasEffect(MobEffects.NIGHT_VISION)) return true;

        var nightVisionEffectInstance = new ModMobEffectInstance(MobEffects.NIGHT_VISION, MobEffectInstance.INFINITE_DURATION, 0, false, false, false);
        entity.addEffect(nightVisionEffectInstance, entity);

        return true;
    }

    @Override
    public void removeUnderlyingEffectFor(LivingEntity entity)
    {
        if (entity.hasEffect(MobEffects.NIGHT_VISION))
        {
            entity.removeEffect(MobEffects.NIGHT_VISION);
        }
    }
}
