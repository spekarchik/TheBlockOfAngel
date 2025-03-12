package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IModMobEffectInstance;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

class SwitchingArmorEffect extends ArmorEffect<ISwitchingArmorEffect> implements IExtendedSwitchingArmorEffect
{
    protected SwitchingArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        super(player, armor, effectType, defaultAmplifier);
    }

    @Override
    protected IModMobEffectInstance setEffect(int amplifier, int duration)
    {
        return player.setEffect(effectType, amplifier, getShowIcon());
    }

    @Override
    public final boolean isOn()
    {
        return getState().isOn();
    }

    @Override
    public final void trySwitch()
    {
        trySwitch(defaultAmplifier);
    }

    @Override
    public void trySwitch(int amplifier)
    {
        var invertedState = getState().isOn() ? State.OFF : State.ON;
        trySwitchTo(isAvailable() && invertedState.isOn(), amplifier);
    }

    @Override
    public void trySwitchOn(int amplifier)
    {
        if (isAnotherActive()) return;
        tryActivateInternal(amplifier, MobEffectInstance.INFINITE_DURATION);
    }

    @Override
    public void updateActivity(int amplifier)
    {
        super.updateActivity(amplifier, MobEffectInstance.INFINITE_DURATION);
    }

    @Override
    public void trySwitchOff()
    {
        tryRemove();
    }

    @Override
    public final void trySwitchTo(boolean switchOn)
    {
        trySwitchTo(switchOn, defaultAmplifier);
    }

    protected final void trySwitchTo(boolean switchOn, int amplifier)
    {
        if (switchOn) trySwitchOn(amplifier);
        else trySwitchOff();
    }

    @Override
    public final void trySwitchOn()
    {
        trySwitchOn(defaultAmplifier);
    }

    @Override
    public ISwitchingArmorEffect getSelf()
    {
        return this;
    }

    @Override
    public void setSwitchState(boolean isOn)
    {
        super.setSwitchState(isOn);
    }
}
