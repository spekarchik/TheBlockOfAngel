package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class SlownessArmorEffect extends TemporaryArmorEffect
{
    private final boolean isAvailableOnAnyArmorElement;

    public SlownessArmorEffect(IPlayer player, IArmor armor, int defaultAmplifier, int duration, boolean isAvailableOnAnyArmorElement)
    {
        super(player, armor, MobEffects.MOVEMENT_SLOWDOWN, defaultAmplifier, duration);
        this.isAvailableOnAnyArmorElement = isAvailableOnAnyArmorElement;
    }

    @Override
    protected boolean canClearEffect()
    {
        return false;
    }

    @Override
    protected boolean isAvailable()
    {
        return isAvailableOnAnyArmorElement
                ? player.isAnyArmorElementPutOn(armor.getArmorElementNames())
                : player.isFullArmorSetPutOn(armor.getArmorElementNames());
    }
}
