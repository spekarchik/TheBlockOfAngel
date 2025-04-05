package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class SlownessNegativeArmorEffect extends NegativeTemporaryArmorEffect
{
    public SlownessNegativeArmorEffect(IPlayer player, IArmor armor, int defaultAmplifier, int duration)
    {
        super(player, armor, MobEffects.SLOWNESS, defaultAmplifier, duration);
        showIcon();
    }
}
