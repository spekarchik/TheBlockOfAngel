package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.PermanentPlayerArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class HealthBoostPermanentArmorEffect extends PermanentPlayerArmorEffect
{
    public HealthBoostPermanentArmorEffect(IPlayer player, IPlayerArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.HEALTH_BOOST, amplifier);
        setup().availableOnLeggingsWithHealthRegenerator();
    }
}
