package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class SlownessArmorEffect extends TemporaryArmorEffect
{
    public SlownessArmorEffect(IPlayer player, IArmor armor, int defaultAmplifier, int duration)
    {
        super(player, armor, MobEffects.MOVEMENT_SLOWDOWN, defaultAmplifier, duration);
        showIcon();
    }

    @Override
    protected boolean canClearEffect()
    {
        return false;
    }
}
