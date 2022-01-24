package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class GlowingArmorEffect extends SwitchingArmorEffect
{
    public GlowingArmorEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.GLOWING, 0);
    }

    @Override
    protected boolean isAvailable()
    {
        return player.isFullArmorSetPutOn(armor.getArmorElementNames());
    }
}
