package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.SwitchingArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class NightVisionSwitchingArmorEffect extends SwitchingArmorEffect
{
    public NightVisionSwitchingArmorEffect(IPlayer player, IPlayerArmor armor)
    {
        super(player, armor, MobEffects.NIGHT_VISION, 0);
        setup().showIcon();
    }
}
