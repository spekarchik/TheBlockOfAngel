package com.pekar.angelblock.events.player;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ModMobEffectInstance extends MobEffectInstance implements IModMobEffectInstance
{
    private final Runnable onEffectEnded;
    private final boolean isMagicItemEffect;

    public ModMobEffectInstance(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon, boolean isMagicItemEffect, Runnable onEffectEnded)
    {
        super(effect, duration, amplifier, ambient, visible, showIcon);
        this.onEffectEnded = onEffectEnded;
        this.isMagicItemEffect = isMagicItemEffect;
    }

    public ModMobEffectInstance(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon, boolean isMagicItemEffect)
    {
        this(effect, duration, amplifier, ambient, visible, showIcon, isMagicItemEffect, null);
    }

    public ModMobEffectInstance(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon)
    {
        this(effect, duration, amplifier, ambient, visible, showIcon, false, null);
    }

    public boolean isMagicItemEffect()
    {
        return isMagicItemEffect;
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
