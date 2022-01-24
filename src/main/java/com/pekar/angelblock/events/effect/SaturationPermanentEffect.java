package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class SaturationPermanentEffect extends PermanentArmorEffect
{
    public SaturationPermanentEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.SATURATION, 0);
    }

    @Override
    protected boolean isAvailable()
    {
        return player.isFullArmorSetPutOn(armor.getArmorElementNames());
    }
}
