package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class BlindnessArmorEffect extends TemporaryArmorEffect
{
    public BlindnessArmorEffect(IPlayer player, IArmor armor, int duration)
    {
        super(player, armor, MobEffects.BLINDNESS, 0, duration);
    }

    @Override
    protected boolean canClearEffect()
    {
        return false;
    }

    @Override
    protected boolean isAvailable()
    {
        return player.isFullArmorSetPutOn(armor.getArmorElementNames());
    }
}
