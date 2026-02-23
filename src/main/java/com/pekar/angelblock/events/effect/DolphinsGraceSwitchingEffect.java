package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.SwitchingArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class DolphinsGraceSwitchingEffect extends SwitchingArmorEffect
{
    public DolphinsGraceSwitchingEffect(IPlayer player, IPlayerArmor armor)
    {
        super(player, armor, MobEffects.DOLPHINS_GRACE, 0);
        setup().availableOnBootsWithSeaPower().showIcon();
    }
}
