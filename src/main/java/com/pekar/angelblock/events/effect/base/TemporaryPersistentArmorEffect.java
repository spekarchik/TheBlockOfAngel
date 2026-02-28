package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.effect.State;
import com.pekar.angelblock.events.mob.IMob;
import com.pekar.angelblock.events.mob.IModMobEffectInstance;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

abstract class TemporaryPersistentArmorEffect<M extends IMob, A extends IArmor>
        extends TemporaryBaseArmorEffect<M, A>
        implements ITemporaryPersistentArmorEffect
{
    protected TemporaryPersistentArmorEffect(M mob, A armor, Holder<MobEffect> effectType, int defaultAmplifier, int defaultDuration)
    {
        super(mob, armor, effectType, defaultAmplifier, defaultDuration);
    }

    @Override
    protected boolean isOn()
    {
        return true;
    }

    @Override
    protected IModMobEffectInstance setEffect(int amplifier, int duration)
    {
        return mob.setEffect(this, duration, amplifier, getShowIcon());
    }

    @Override
    protected boolean canClearEffect()
    {
        return false;
    }

    @Override
    protected boolean shouldPersist()
    {
        return true;
    }

    @Override
    public void tryActivate(int amplifier, int duration)
    {
        super.tryActivateInternal(amplifier, duration);
    }

    @Override
    public void updateActivity()
    {
        // ignore: super.updateActivity(p1, p2) should only be called from tryActivate()
    }

    @Override
    public void onDurationEnd()
    {
        setState(State.OFF);
        clearEffectInstance();
        onDeactivated();
    }
}
