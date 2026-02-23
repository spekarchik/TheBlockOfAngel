package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.SwitchingArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class SlowFallingSwitchingEffect extends SwitchingArmorEffect
{
    public SlowFallingSwitchingEffect(IPlayer player, IPlayerArmor armor)
    {
        super(player, armor, MobEffects.SLOW_FALLING, 0);
        setup().showIcon();
    }
}
