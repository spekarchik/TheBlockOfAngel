package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class HealthBoostArmorEffect extends PermanentArmorEffect
{
    public HealthBoostArmorEffect(IPlayer player, IArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.HEALTH_BOOST, amplifier);
    }

    @Override
    protected boolean isAvailable()
    {
        return player.isFullArmorSetPutOn(armor.getArmorElementNames());
    }

    @Override
    protected boolean canResetEffect()
    {
        return false;
    }
}
