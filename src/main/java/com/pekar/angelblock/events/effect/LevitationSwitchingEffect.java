package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class LevitationSwitchingEffect extends SwitchingArmorEffect
{
    private final boolean isAvailableOnFullArmorSet;

    public LevitationSwitchingEffect(IPlayer player, IArmor armor, int amplifier, boolean isAvailableOnFullArmorSet)
    {
        super(player, armor, MobEffects.LEVITATION, amplifier);
        this.isAvailableOnFullArmorSet = isAvailableOnFullArmorSet;
    }

    @Override
    protected boolean isAvailable()
    {
        return isAvailableOnFullArmorSet
            ? player.isFullArmorSetPutOn(armor.getArmorElementNames())
                : player.isAllArmorElementsPutOn(armor.getBootsName(), armor.getLeggingsName());
    }
}
