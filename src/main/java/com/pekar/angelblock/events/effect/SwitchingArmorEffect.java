package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

abstract class SwitchingArmorEffect extends ArmorEffect
{
    public SwitchingArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        super(player, armor, effectType, defaultAmplifier);
    }
}
