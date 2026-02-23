package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.TemporaryPlayerArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class RegenerationTemporaryArmorEffect extends TemporaryPlayerArmorEffect
{
    public RegenerationTemporaryArmorEffect(IPlayer player, IPlayerArmor armor, int amplifier, int duration)
    {
        super(player, armor, MobEffects.REGENERATION, amplifier, duration);
        setup().availableOnLeggingsWithHealthRegenerator().showIcon();
    }
}
