package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class JumpBoostSwitchingArmorEffect extends SwitchingArmorEffect
{
    public JumpBoostSwitchingArmorEffect(IPlayer player, IArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.JUMP_BOOST, amplifier);
        availableOnBootsWithJumpBooster();
        showIcon();
    }
}
