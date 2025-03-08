package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.armor.ITemporaryArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

abstract class TemporaryArmorEffect extends ArmorEffect implements ITemporaryArmorEffect
{
    protected final int defaultDuration;
    protected boolean isArmorEffect;

    public TemporaryArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier, int defaultDuration)
    {
        super(player, armor, effectType, defaultAmplifier);
        this.defaultDuration = defaultDuration;
    }

    @Override
    public boolean trySwitch(int amplifier)
    {
        trySwitchForDuration(amplifier, defaultDuration);
        return true;
    }

    @Override
    public boolean trySwitchForDuration(int duration)
    {
        trySwitchForDuration(defaultAmplifier, duration);
        return true;
    }

    @Override
    public boolean trySwitchForDuration(int amplifier, int duration)
    {
        if (!isAvailable) return false;
        isArmorEffect = true;
        player.setEffect(this, duration, amplifier, showIcon);
        return true;
    }

    @Override
    public boolean trySwitchOn(int amplifier)
    {
        return trySwitch(amplifier);
    }

    @Override
    public boolean trySwitchOff()
    {
        if (!isArmorEffect) return false;
        isArmorEffect = false;
        return super.trySwitchOff();
    }

    @Override
    public boolean isEffectOn()
    {
        return false;
    }

    @Override
    public boolean isArmorEffect()
    {
        return isArmorEffect;
    }

    @Override
    public void resetIsArmorEffect()
    {
        isArmorEffect = false;
    }
}
