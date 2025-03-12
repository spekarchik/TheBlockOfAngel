package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class SlownessPermanentArmorEffect extends PermanentArmorEffect
{
    public SlownessPermanentArmorEffect(IPlayer player, IArmor armor, int defaultAmplifier)
    {
        super(player, armor, MobEffects.MOVEMENT_SLOWDOWN, defaultAmplifier);
    }
}
