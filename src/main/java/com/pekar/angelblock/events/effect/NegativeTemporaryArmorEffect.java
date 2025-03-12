package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class NegativeTemporaryArmorEffect extends TemporaryPersistentArmorEffect
{
    public NegativeTemporaryArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier, int duration)
    {
        super(player, armor, effectType, defaultAmplifier, duration);
        alwaysAvailable();
    }
}
