package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffect;

abstract class TemporaryArmorEffect extends ArmorEffect
{
    protected final int duration;

    public TemporaryArmorEffect(IPlayer player, IArmor armor, MobEffect effectType, int defaultAmplifier, int duration)
    {
        super(player, armor, effectType, defaultAmplifier);
        this.duration = duration;
    }

    @Override
    public boolean trySwitch(int amplifier)
    {
        if (!isAvailable) return false;
        player.setEffect(effectType, duration, amplifier);
        return true;
    }

    @Override
    public boolean isEffectOn()
    {
        return false;
    }
}
