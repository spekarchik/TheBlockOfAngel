package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.SwitchingArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class JumpBoostSwitchingArmorEffect extends SwitchingArmorEffect
{
    public JumpBoostSwitchingArmorEffect(IPlayer player, IPlayerArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.JUMP, amplifier);
        setup().availableOnBootsWithJumpBooster().showIcon();
    }
}
