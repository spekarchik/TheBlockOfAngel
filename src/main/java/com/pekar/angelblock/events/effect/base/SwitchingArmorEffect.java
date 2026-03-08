package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.mob.IModMobEffectInstance;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class SwitchingArmorEffect extends ArmorEffect<IPlayer, IPlayerArmor> implements IExtendedSwitchingArmorEffect
{
    protected SwitchingArmorEffect(IPlayer player, IPlayerArmor armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        super(player, armor, effectType, defaultAmplifier);
    }

    @Override
    protected IModMobEffectInstance setEffect(int amplifier, int duration)
    {
        return mob.setEffect(effectType, amplifier, getShowIcon());
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
        if (isAnotherActive())
        {
            if (isInfinite()) updateActivity(amplifier);
            return;
        }

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
    public void setSwitchState(boolean isOn)
    {
        super.setSwitchState(isOn);
    }
}
