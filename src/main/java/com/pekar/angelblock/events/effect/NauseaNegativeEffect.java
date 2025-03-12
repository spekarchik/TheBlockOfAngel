package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class NauseaNegativeEffect extends NegativeTemporaryArmorEffect
{
    public NauseaNegativeEffect(IPlayer player, IArmor armor, int duration)
    {
        super(player, armor, MobEffects.CONFUSION, 12, duration);
    }
}
