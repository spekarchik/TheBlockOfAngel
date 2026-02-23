package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.mob.IMob;

import java.util.function.BiPredicate;

public interface IArmorEffectWithOptions<M extends IMob> extends IArmorEffect
{
    boolean getShowIcon();
    void setShowIcon(boolean showIcon);
    void setupAvailability(BiPredicate<M, IArmor> value);
}
