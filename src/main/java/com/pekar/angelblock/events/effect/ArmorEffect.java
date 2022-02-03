package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffect;

abstract class ArmorEffect implements IArmorEffect
{
    protected IPlayer player;
    protected IArmor armor;
    protected MobEffect effectType;
    protected boolean isOn;
    protected boolean isAvailable;
    protected int defaultAmplifier;

    protected ArmorEffect(IPlayer player, IArmor armor, MobEffect effectType, int defaultAmplifier)
    {
        this.player = player;
        this.armor = armor;
        this.effectType = effectType;
        this.defaultAmplifier = defaultAmplifier;
    }

    @Override
    public boolean isEffectOn()
    {
        return isOn;
    }

    @Override
    public final boolean isActive()
    {
        return player.isEffectActive(effectType);
    }

    @Override
    public final boolean isEffectAvailable()
    {
        return isAvailable;
    }

    @Override
    public final boolean updateEffectAvailability()
    {
        return isAvailable = isAvailable();
    }

    @Override
    public final boolean trySwitch()
    {
        return trySwitch(defaultAmplifier);
    }

    @Override
    public boolean trySwitch(int amplifier)
    {
        boolean isOnOld = isOn;
        isOn = isAvailable ? !isOn : false;
        updateEffectActivity(amplifier);
        return isOn != isOnOld;
    }

    @Override
    public final void updateSwitchState()
    {
        isOn = isActive();
    }

    @Override
    public final void invertSwitchState()
    {
        isOn = !isOn;
    }

    protected final void setSwitchState(boolean isOn)
    {
        this.isOn = isOn;
    }

    @Override
    public final void updateEffectActivity(int amplifier)
    {
        if (isEffectOn() && isEffectAvailable())
        {
            if (isActive() && canResetEffect())
            {
                player.clearEffect(effectType);
            }

            if (!isActive() || canResetEffect())
            {
                player.setEffect(effectType, amplifier);
            }
        }
        else
        {
            trySwitchOff();
        }
    }

    @Override
    public final void updateEffectActivity()
    {
        updateEffectActivity(defaultAmplifier);
    }

    @Override
    public boolean trySwitchOff()
    {
        if (canClearEffect() && isActive())
        {
            isOn = false;
            player.clearEffect(effectType);
        }

        return !isActive();
    }

    @Override
    public boolean trySwitchOn(int amplifier)
    {
        isOn = true;
        updateEffectActivity(amplifier);
        return isActive();
    }

    @Override
    public final boolean trySwitchOn()
    {
        return trySwitchOn(defaultAmplifier);
    }

    @Override
    public final MobEffect getEffect()
    {
        return effectType;
    }

    protected boolean canClearEffect()
    {
        return true;
    }

    protected boolean canResetEffect()
    {
        return true;
    }

    protected abstract boolean isAvailable();
}
