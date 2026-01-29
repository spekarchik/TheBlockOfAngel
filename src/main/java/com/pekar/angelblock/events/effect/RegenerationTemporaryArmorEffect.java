package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class RegenerationTemporaryArmorEffect extends TemporaryArmorEffect
{
    public RegenerationTemporaryArmorEffect(IPlayer player, IArmor armor, int amplifier, int duration)
    {
        super(player, armor, MobEffects.REGENERATION, amplifier, duration);
        availableOnLeggingsWithHealthRegenerator();
        showIcon();
    }
}
