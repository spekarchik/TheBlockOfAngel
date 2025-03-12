package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class DolphinsGraceSwitchingEffect extends SwitchingArmorEffect
{
    public DolphinsGraceSwitchingEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.DOLPHINS_GRACE, 0);
        availableOnBootsWithSeaPower();
        showIcon();
    }
}
