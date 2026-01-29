package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.potions.PotionRegistry;

public class SuperJumpSwitchingEffect extends SwitchingArmorEffect
{
    public SuperJumpSwitchingEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, PotionRegistry.ARMOR_SUPER_JUMP_MODE_EFFECT, 0);
        showIcon();
    }
}
