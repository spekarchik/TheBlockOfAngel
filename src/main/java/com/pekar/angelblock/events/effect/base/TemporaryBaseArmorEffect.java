package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.mob.IMob;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

abstract class TemporaryBaseArmorEffect<M extends IMob, A extends IArmor> extends ArmorEffect<M, A> implements ITemporaryBaseArmorEffect
{
    protected final int defaultDuration;

    public TemporaryBaseArmorEffect(M mob, A armor, Holder<MobEffect> effectType, int defaultAmplifier, int defaultDuration)
    {
        super(mob, armor, effectType, defaultAmplifier);
        this.defaultDuration = defaultDuration;
    }

    @Override
    public void tryActivate()
    {
        updateAvailability();
        tryActivateInternal(defaultAmplifier, defaultDuration);
    }

    @Override
    public final void tryActivate(int duration)
    {
        updateAvailability();
        tryActivateInternal(defaultAmplifier, duration);
    }

    @Override
    public void updateActivity()
    {
        super.updateActivity(defaultAmplifier, defaultDuration);
    }

    @Override
    public final Holder<MobEffect> getEffect()
    {
        return effectType;
    }
}
