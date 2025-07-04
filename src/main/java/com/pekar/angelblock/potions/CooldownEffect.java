package com.pekar.angelblock.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class CooldownEffect extends MobEffect
{
    protected CooldownEffect()
    {
        super(MobEffectCategory.NEUTRAL, 0);
    }

    @Override
    public boolean isInstantenous()
    {
        return false;
    }
}
