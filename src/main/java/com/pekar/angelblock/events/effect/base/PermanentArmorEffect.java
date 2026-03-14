package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.mob.IMob;
import com.pekar.angelblock.events.mob.IModMobEffectInstance;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

class PermanentArmorEffect<M extends IMob, A extends IArmor>
        extends ArmorEffect<M, A>
        implements IPermanentArmorEffect
{
    protected PermanentArmorEffect(M mob, A armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        super(mob, armor, effectType, defaultAmplifier);
    }

    @Override
    protected IModMobEffectInstance setEffect(int amplifier, int duration)
    {
        return mob.setEffect(effectType, amplifier, getShowIcon());
    }

    @Override
    public final void tryActivate()
    {
        this.tryActivate(defaultAmplifier);
    }

    @Override
    public void tryActivate(int amplifier)
    {
        updateAvailability();
        super.tryActivateInternal(amplifier, MobEffectInstance.INFINITE_DURATION);
    }

    @Override
    public void updateActivity(int amplifier)
    {
        super.updateActivity(amplifier, MobEffectInstance.INFINITE_DURATION);
    }

    @Override
    protected boolean isOn()
    {
        return true;
    }
}
