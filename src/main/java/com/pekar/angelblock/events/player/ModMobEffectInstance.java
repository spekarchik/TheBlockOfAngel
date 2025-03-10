package com.pekar.angelblock.events.player;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ModMobEffectInstance extends MobEffectInstance
{
    private final Runnable onEffectEnded;

    public ModMobEffectInstance(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon, Runnable onEffectEnded)
    {
        super(effect, duration, amplifier, ambient, visible, showIcon);
        this.onEffectEnded = onEffectEnded;
    }

    public ModMobEffectInstance(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon)
    {
        this(effect, duration, amplifier, ambient, visible, showIcon, null);
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

        if (isUpdated)
            onEffectEnded.run();

        return isUpdated;
    }
}
