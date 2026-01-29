package com.pekar.angelblock.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public abstract class ModMobEffect extends MobEffect
{
    protected ModMobEffect(MobEffectCategory category, int color)
    {
        super(category, color);
    }

    public abstract void removeUnderlyingEffectFor(LivingEntity entity);
}
