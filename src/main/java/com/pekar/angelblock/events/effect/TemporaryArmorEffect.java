package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IModMobEffectInstance;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

class TemporaryArmorEffect extends TemporaryBaseArmorEffect<ITemporaryArmorEffect> implements ITemporaryArmorEffect
{
    protected TemporaryArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier, int defaultDuration)
    {
        super(player, armor, effectType, defaultAmplifier, defaultDuration);
    }

    @Override
    public void tryRemove()
    {
        super.tryRemove();
    }

    @Override
    public final boolean isArmorEffect()
    {
        return player.hasArmorEffect(effectType);
    }

    @Override
    protected IModMobEffectInstance setEffect(int amplifier, int duration)
    {
        return player.setEffect(this, duration, amplifier, getShowIcon());
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

    @Override
    public ITemporaryArmorEffect getSelf()
    {
        return this;
    }
}
