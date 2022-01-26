package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class HasteSwitchingEffect extends SwitchingArmorEffect
{
    public HasteSwitchingEffect(IPlayer player, IArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.DIG_SPEED, amplifier);
    }

    @Override
    protected boolean isAvailable()
    {
        return player.isArmorElementPutOn(armor.getChestPlateName());
    }
}
