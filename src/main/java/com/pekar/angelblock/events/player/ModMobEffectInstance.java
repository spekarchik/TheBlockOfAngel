package com.pekar.angelblock.events.player;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ModMobEffectInstance extends MobEffectInstance implements IModMobEffectInstance
{
    private final Runnable onEffectEnded;
    private final boolean isCrystalEffect;

    public ModMobEffectInstance(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon, boolean isCrystalEffect, Runnable onEffectEnded)
    {
        super(effect, duration, amplifier, ambient, visible, showIcon);
        this.onEffectEnded = onEffectEnded;
        this.isCrystalEffect = isCrystalEffect;
    }

    public ModMobEffectInstance(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon, boolean isCrystalEffect)
    {
        this(effect, duration, amplifier, ambient, visible, showIcon, isCrystalEffect, null);
    }

    public boolean isCrystalEffect()
    {
        return isCrystalEffect;
    }

    @Override
    public boolean tick(LivingEntity entity, Runnable onExpirationRunnable)
    {
        if (onEffectEnded != null && getDuration() == 1)
        {
            onEffectEnded.run();
        }

        return super.tick(entity, onExpirationRunnable);
    }

    @Override
    public boolean update(MobEffectInstance other)
    {
        boolean isUpdated = super.update(other);

        if (isUpdated && onEffectEnded != null)
            onEffectEnded.run();

        return isUpdated;
    }
}
