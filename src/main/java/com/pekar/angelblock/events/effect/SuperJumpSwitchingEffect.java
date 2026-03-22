package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.SwitchingArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.potions.PotionRegistry;

public class SuperJumpSwitchingEffect extends SwitchingArmorEffect
{
    public SuperJumpSwitchingEffect(IPlayer player, IPlayerArmor armor)
    {
        super(player, armor, PotionRegistry.ARMOR_SUPER_JUMP_MODE_EFFECT, 0);
        setup().showIcon();
    }
}
