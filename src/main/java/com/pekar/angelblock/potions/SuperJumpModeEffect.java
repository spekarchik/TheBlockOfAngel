package com.pekar.angelblock.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class SuperJumpModeEffect extends MobEffect
{
    protected SuperJumpModeEffect()
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
        return duration <= 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier)
    {
        return true;
    }
}
