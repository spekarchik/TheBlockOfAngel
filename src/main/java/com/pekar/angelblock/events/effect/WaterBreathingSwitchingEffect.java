package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class WaterBreathingSwitchingEffect extends SwitchingArmorEffect
{
    public WaterBreathingSwitchingEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.WATER_BREATHING, 0);
    }
}
