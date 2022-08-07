package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class SpeedSwitchingEffect extends SwitchingArmorEffect
{
    public SpeedSwitchingEffect(IPlayer player, IArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.MOVEMENT_SPEED, amplifier);
        availableOnBootsWithStrengthBooster();
    }
}
