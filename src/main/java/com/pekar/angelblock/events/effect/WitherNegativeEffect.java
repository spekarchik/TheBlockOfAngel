package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class WitherNegativeEffect extends NegativeTemporaryArmorEffect
{
    public WitherNegativeEffect(IPlayer player, IArmor armor, int defaultAmplifier, int duration)
    {
        super(player, armor, MobEffects.WITHER, defaultAmplifier, duration);
    }
}
