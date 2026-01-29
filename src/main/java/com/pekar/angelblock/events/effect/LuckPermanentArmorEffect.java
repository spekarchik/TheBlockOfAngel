package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class LuckPermanentArmorEffect extends PermanentArmorEffect
{
    public LuckPermanentArmorEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.LUCK, 1);
    }
}
