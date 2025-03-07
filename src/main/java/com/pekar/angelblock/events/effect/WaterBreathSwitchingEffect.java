package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class WaterBreathSwitchingEffect extends SwitchingArmorEffect
{
    public WaterBreathSwitchingEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.WATER_BREATHING, 0);
    }
}
