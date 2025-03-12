package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class GlowingSwitchingArmorEffect extends SwitchingArmorEffect
{
    public GlowingSwitchingArmorEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.GLOWING, 0);
        showIcon();
    }
}
