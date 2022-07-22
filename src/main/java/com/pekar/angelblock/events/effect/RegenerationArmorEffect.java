package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class RegenerationArmorEffect extends TemporaryArmorEffect
{
    public RegenerationArmorEffect(IPlayer player, IArmor armor, int amplifier, int duration)
    {
        super(player, armor, MobEffects.REGENERATION, amplifier, duration);
        showIcon = true;
    }
}
