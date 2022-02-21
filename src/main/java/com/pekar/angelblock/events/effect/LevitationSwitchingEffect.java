package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class LevitationSwitchingEffect extends SwitchingArmorEffect
{
    public LevitationSwitchingEffect(IPlayer player, IArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.LEVITATION, amplifier);
        showIcon = true;
    }
}
