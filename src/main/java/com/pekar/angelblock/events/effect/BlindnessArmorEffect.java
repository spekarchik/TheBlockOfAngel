package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class BlindnessArmorEffect extends NegativeTemporaryArmorEffect
{
    public BlindnessArmorEffect(IPlayer player, IArmor armor, int duration)
    {
        super(player, armor, MobEffects.BLINDNESS, 0, duration);
        showIcon();
    }

    @Override
    protected boolean canClearEffect()
    {
        return false;
    }
}
