package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

abstract class TemporaryArmorEffect extends ArmorEffect
{
    protected final int duration;

    public TemporaryArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier, int duration)
    {
        super(player, armor, effectType, defaultAmplifier);
        this.duration = duration;
    }

    @Override
    public boolean trySwitch(int amplifier)
    {
        if (!isAvailable) return false;
        player.setEffect(effectType, duration, amplifier, showIcon);
        return true;
    }

    @Override
    public boolean trySwitchOn(int amplifier)
    {
        return trySwitch(amplifier);
    }

    @Override
    public boolean isEffectOn()
    {
        return false;
    }
}
