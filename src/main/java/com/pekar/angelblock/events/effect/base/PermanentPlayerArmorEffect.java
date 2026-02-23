package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class PermanentPlayerArmorEffect extends PermanentArmorEffect<IPlayer>
{
    protected PermanentPlayerArmorEffect(IPlayer mob, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        super(mob, armor, effectType, defaultAmplifier);
    }
}
