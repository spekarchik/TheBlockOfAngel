package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.NegativeTemporaryArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class BlindnessNegativeArmorEffect extends NegativeTemporaryArmorEffect
{
    public BlindnessNegativeArmorEffect(IPlayer player, IPlayerArmor armor, int duration)
    {
        super(player, armor, MobEffects.BLINDNESS, 0, duration);
        setup().showIcon();
    }
}
