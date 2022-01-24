package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class StrengthArmorEffect extends PermanentArmorEffect
{
    public StrengthArmorEffect(IPlayer player, IArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.DAMAGE_BOOST, amplifier);
    }

    @Override
    protected boolean isAvailable()
    {
        return player.isArmorElementPutOn(armor.getChestPlateName());
    }
}
