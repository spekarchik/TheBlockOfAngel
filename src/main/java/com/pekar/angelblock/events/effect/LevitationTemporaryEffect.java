package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class LevitationTemporaryEffect extends TemporaryArmorEffect
{
    public LevitationTemporaryEffect(IPlayer player, IArmor armor, int defaultAmplifier, int duration)
    {
        super(player, armor, MobEffects.LEVITATION, defaultAmplifier, duration);
    }

}
