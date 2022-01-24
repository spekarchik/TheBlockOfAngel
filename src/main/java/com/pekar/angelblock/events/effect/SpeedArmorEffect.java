package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class SpeedArmorEffect extends SwitchingArmorEffect
{
    public SpeedArmorEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.MOVEMENT_SPEED, 0);
    }

    @Override
    protected boolean isAvailable()
    {
        return player.isAllArmorElementsPutOn(armor.getBootsName(), armor.getLeggingsName());
    }
}
