package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.SwitchingArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class SpeedSwitchingEffect extends SwitchingArmorEffect
{
    public SpeedSwitchingEffect(IPlayer player, IPlayerArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.SPEED, amplifier);
        setup().availableOnBootsWithJumpBooster();
    }
}
