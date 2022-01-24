package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffect;

abstract class PermanentArmorEffect extends ArmorEffect
{
    public PermanentArmorEffect(IPlayer player, IArmor armor, MobEffect effectType, int defaultAmplifier)
    {
        super(player, armor, effectType, defaultAmplifier);
    }

    @Override
    public boolean isEffectOn()
    {
        return true;
    }

    @Override
    public boolean trySwitch(int amplifier)
    {
        return true;
    }
}
