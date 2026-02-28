package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.effect.State;
import com.pekar.angelblock.events.mob.IMob;
import com.pekar.angelblock.events.mob.IModMobEffectInstance;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

class TemporaryArmorEffect<M extends IMob, A extends IArmor>
        extends TemporaryBaseArmorEffect<M, A>
        implements ITemporaryArmorEffect
{
    protected TemporaryArmorEffect(M mob, A armor, Holder<MobEffect> effectType, int defaultAmplifier, int defaultDuration)
    {
        super(mob, armor, effectType, defaultAmplifier, defaultDuration);
    }

    @Override
    public void tryRemove()
    {
        super.tryRemove();
    }

    @Override
    public final boolean isArmorEffect()
    {
        return mob.hasArmorEffect(effectType);
    }

    @Override
    protected IModMobEffectInstance setEffect(int amplifier, int duration)
    {
        return mob.setEffect(this, duration, amplifier, getShowIcon());
    }

    @Override
    public void tryActivate(int amplifier, int duration)
    {
        if (isAnotherActive()) return;
        super.tryActivateInternal(amplifier, duration);
    }

    @Override
    public final void onDurationEnd()
    {
        setState(State.OFF);
        clearEffectInstance();
    }
}
