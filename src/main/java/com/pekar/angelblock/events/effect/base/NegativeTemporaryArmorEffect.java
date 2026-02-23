package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class NegativeTemporaryArmorEffect extends TemporaryPersistentArmorEffect<IPlayer>
{
    public NegativeTemporaryArmorEffect(IPlayer player, IPlayerArmor armor, Holder<MobEffect> effectType, int defaultAmplifier, int duration)
    {
        super(player, armor, effectType, defaultAmplifier, duration);
        setup().alwaysAvailable();
    }
}
