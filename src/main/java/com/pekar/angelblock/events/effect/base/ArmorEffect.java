package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.effect.PlayerArmorEffectSetup;
import com.pekar.angelblock.events.effect.State;
import com.pekar.angelblock.events.mob.IMob;
import com.pekar.angelblock.events.mob.IModMobEffectInstance;
import com.pekar.angelblock.events.mob.ModMobEffectInstance;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.BiPredicate;

abstract class ArmorEffect<M extends IMob> implements IArmorEffectWithOptions<M>
{
    protected final M mob;
    protected final IArmor armor;
    protected final Holder<MobEffect> effectType;
    private State state = State.OFF;
    private boolean isAvailable;
    private boolean isNotAvailable;
    protected final int defaultAmplifier;
    private boolean showIcon;
    private IModMobEffectInstance effectInstance;
    protected BiPredicate<M, IArmor> availabilityPredicate = (m, a) -> false;

    protected ArmorEffect(M mob, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        this.mob = mob;
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
    public IPlayerArmorEffectSetup<IArmorEffectWithOptions<IPlayer>> setup()
    {
        if (mob instanceof IPlayer)
            return new PlayerArmorEffectSetup<>((IArmorEffectWithOptions<IPlayer>)this);
        else
            return null;
    }

    @Override
    public <E extends IArmorEffectWithOptions<IPlayer>> IPlayerArmorEffectSetup<E> setup(E effect)
    {
        if (mob instanceof IPlayer)
            return new PlayerArmorEffectSetup<>(effect);
        else
            return null;
    }

    @Override
    public <MM extends IMob> IArmorEffectSetup<IArmorEffectWithOptions<MM>, MM> setupBasic()
    {
        return new ArmorEffectSetup<>((IArmorEffectWithOptions<MM>)this);
    }

    @Override
    public <E extends IArmorEffectWithOptions<MM>, MM extends IMob> IArmorEffectSetup<IArmorEffectWithOptions<MM>, MM> setupBasic(E effect)
    {
        return new ArmorEffectSetup<>(effect);
    }

    @Override
    public final boolean isActive()
    {
        return mob.hasArmorEffect(effectType) && effectInstance != null && effectInstance.equals(mob.getEffectInstance(effectType));
    }

    @Override
    public boolean isAnotherActive()
    {
        return mob.isEffectActive(effectType) && !isActive();
    }

    @Override
    public boolean isAnyActive()
    {
        return mob.isEffectActive(effectType);
    }

    @Override
    public final boolean isAvailable()
    {
        return isAvailable;
    }

    @Override
    public final void updateAvailability()
    {
        isAvailable = availabilityPredicate.test(mob, armor);
    }

    @Override
    public void updateActivity()
    {
        updateActivity(defaultAmplifier, MobEffectInstance.INFINITE_DURATION);
    }

    @Override
    public final void updateSwitchState()
    {
        boolean canHandleEffect = isAvailable() && mob.hasArmorEffect(effectType);
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
                mob.clearEffect(effectType);
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

    @Override
    public boolean getShowIcon()
    {
        return showIcon;
    }

    @Override
    public void setShowIcon(boolean showIcon)
    {
        this.showIcon = showIcon;
    }

    protected final void clearEffectInstance()
    {
        effectInstance = null;
    }

    @Override
    public final void setupAvailability(BiPredicate<M, IArmor> value)
    {
        availabilityPredicate = value;
    }

    @Override
    public final boolean isInfinite()
    {
        return mob.getEffectInstance(effectType).isInfiniteDuration();
    }

    private boolean isMagicItemEffect()
    {
        return mob.getEffectInstance(effectType) instanceof ModMobEffectInstance modMobEffectInstance && modMobEffectInstance.isMagicItemEffect();
    }
}
