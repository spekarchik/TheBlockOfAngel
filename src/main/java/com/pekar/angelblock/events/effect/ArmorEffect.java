package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IModMobEffectInstance;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.events.player.ModMobEffectInstance;
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
    private boolean isNotAvailable;
    protected final int defaultAmplifier;
    private boolean showIcon;
    private IModMobEffectInstance effectInstance;
    private BiPredicate<IPlayer, IArmor> availabilityPredicate = (p, a) -> false;
    private BiPredicate<IPlayer, IArmor> unavailabilityPredicate = (p, a) -> !availabilityPredicate.test(p, a);

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
        return player.hasArmorEffect(effectType) && effectInstance != null && effectInstance.equals(player.getEffectInstance(effectType));
    }

    @Override
    public boolean isAnotherActive()
    {
        return player.isEffectActive(effectType) && !isActive();
    }

    @Override
    public boolean isAnyActive()
    {
        return player.isEffectActive(effectType);
    }

    @Override
    public final boolean isAvailable()
    {
        return isAvailable;
    }

    @Override
    public final boolean isUnavailable()
    {
        return isNotAvailable;
    }

    @Override
    public final void updateAvailability()
    {
        isAvailable = availabilityPredicate.test(player, armor);
        isNotAvailable = unavailabilityPredicate.test(player, armor);
    }

    @Override
    public void updateActivity()
    {
        updateActivity(defaultAmplifier, MobEffectInstance.INFINITE_DURATION);
    }

    @Override
    public final void updateSwitchState()
    {
        boolean canHandleEffect = isAvailable() && player.hasArmorEffect(effectType);
        setSwitchState(canHandleEffect);
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
                effectInstance = setEffect(amplifier, duration);
                onActivated();
            }
        }
        else //if (isUnavailable())
        {
            tryRemove();
        }
    }

    protected boolean shouldPersist()
    {
        return false;
    }

    protected abstract IModMobEffectInstance setEffect(int amplifier, int duration);

    @Override
    public void onActivated()
    {
    }

    @Override
    public void onDeactivated()
    {
    }

    protected final void setState(State state)
    {
        this.state = state;
    }

    protected void tryActivateInternal(int amplifier, int duration)
    {
        setState(State.ON);
        updateActivity(amplifier, duration);
    }

    private void tryRemove(boolean forceRemove)
    {
        if (canClearEffect())
        {
            if (isActive() || (isAnyActive() && (forceRemove || (isAvailable() && isInfinite() && !isMagicItemEffect()))))
            {
                player.clearEffect(effectType);
                effectInstance = null;
                onDeactivated();
            }

            if (state.isOn()) setState(State.OFF);
        }
    }

    protected void tryRemove()
    {
        tryRemove(false);
    }

    @Override
    public final void forceRemove()
    {
        tryRemove(true);
    }

    protected boolean canClearEffect()
    {
        return true;
    }

    protected boolean getShowIcon()
    {
        return showIcon;
    }

    protected final void clearEffectInstance()
    {
        effectInstance = null;
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

    @Override
    public void setUnavailabilityPredicate(BiPredicate<IPlayer, IArmor> value)
    {
        unavailabilityPredicate = value;
    }

    @Override
    public BiPredicate<IPlayer, IArmor> getUnavailabilityPredicate()
    {
        return unavailabilityPredicate;
    }

    @Override
    public final boolean isInfinite()
    {
        return player.getEffectInstance(effectType).isInfiniteDuration();
    }

    private boolean isMagicItemEffect()
    {
        return player.getEffectInstance(effectType) instanceof ModMobEffectInstance modMobEffectInstance && modMobEffectInstance.isMagicItemEffect();
    }
}
