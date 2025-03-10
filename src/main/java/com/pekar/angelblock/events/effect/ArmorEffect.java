package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.BiPredicate;

abstract class ArmorEffect<T extends IArmorEffect> implements EffectSetup<T>, IArmorEffect
{
    protected final IPlayer player;
    protected final IArmor armor;
    protected final Holder<MobEffect> effectType;
    private State state = State.OFF;
    private boolean isAvailable;
    protected final int defaultAmplifier;
    private boolean showIcon;
    private BiPredicate<IPlayer, IArmor> availabilityPredicate = (p, a) -> false;

    protected ArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        this.player = player;
        this.armor = armor;
        this.effectType = effectType;
        this.defaultAmplifier = defaultAmplifier;
    }

    @Override
    public final State getState()
    {
        return state;
    }

    @Override
    public final boolean isActive()
    {
        return isOn() && player.isEffectActive(effectType);
    }

    @Override
    public boolean isAnotherActive()
    {
        return !isOn() && player.isEffectActive(effectType);
    }

    @Override
    public final boolean isAvailable()
    {
        return isAvailable;
    }

    @Override
    public final void updateAvailability()
    {
        isAvailable = availabilityPredicate.test(player, armor);
    }

    @Override
    public void updateActivity()
    {
        updateActivity(defaultAmplifier, MobEffectInstance.INFINITE_DURATION);
    }

    @Override
    public final void updateSwitchState()
    {
        setSwitchState(player.isEffectActive(effectType));
    }

    protected void setSwitchState(boolean isOn)
    {
        setState(isOn ? State.ON : State.OFF);
    }

    protected boolean isOn()
    {
        return state.isOn();
    }

    protected final void updateActivity(int amplifier, int duration)
    {
        if (isAvailable() && isOn())
        {
            if (!isActive() || shouldPersist())
            {
                setEffect(amplifier, duration);
            }
        }
        else
        {
            tryRemove();
        }
    }

    protected boolean shouldPersist()
    {
        return false;
    }

    protected abstract void setEffect(int amplifier, int duration);

    protected final void setState(State state)
    {
        this.state = state;
    }

    protected void tryActivate(int amplifier, int duration)
    {
        setState(State.ON);
        updateActivity(amplifier, duration);
    }

    protected void tryRemove()
    {
        if (canClearEffect())
        {
            if (isActive()) player.clearEffect(effectType);
            if (state.isOn()) setState(State.OFF);
        }
    }

    protected boolean canClearEffect()
    {
        return true;
    }

    protected boolean getShowIcon()
    {
        return showIcon;
    }

    @Override
    public final void setShowIcon(boolean value)
    {
        showIcon = value;
    }

    @Override
    public final void setAvailabilityPredicate(BiPredicate<IPlayer, IArmor> value)
    {
        availabilityPredicate = value;
    }

    @Override
    public final BiPredicate<IPlayer, IArmor> getAvailabilityPredicate()
    {
        return availabilityPredicate;
    }
}
