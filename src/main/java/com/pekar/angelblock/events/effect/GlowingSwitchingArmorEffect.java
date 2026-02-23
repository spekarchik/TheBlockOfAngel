package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.SwitchingArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class GlowingSwitchingArmorEffect extends SwitchingArmorEffect
{
    public GlowingSwitchingArmorEffect(IPlayer player, IPlayerArmor armor)
    {
        super(player, armor, MobEffects.GLOWING, 0);
        setup().showIcon();
    }
}
