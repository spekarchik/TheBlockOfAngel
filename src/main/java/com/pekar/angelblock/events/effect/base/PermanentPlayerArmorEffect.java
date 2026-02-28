package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class PermanentPlayerArmorEffect extends PermanentArmorEffect<IPlayer, IPlayerArmor>
{
    protected PermanentPlayerArmorEffect(IPlayer mob, IPlayerArmor armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        super(mob, armor, effectType, defaultAmplifier);
    }
}
