package com.pekar.angelblock.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class SwordExplosionModeMobEffect extends MobEffect
{
    protected SwordExplosionModeMobEffect()
    {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    @Override
    public boolean isInstantenous()
    {
        return false;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier)
    {
        return duration <= 0;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier)
    {
        // do nothing
    }
}
