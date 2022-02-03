package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class HealthBoostArmorEffect extends PermanentArmorEffect
{
    public HealthBoostArmorEffect(IPlayer player, IArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.HEALTH_BOOST, amplifier);
        availableOnFullArmorSet();
    }

    @Override
    protected boolean canResetEffect()
    {
        return false;
    }
}
