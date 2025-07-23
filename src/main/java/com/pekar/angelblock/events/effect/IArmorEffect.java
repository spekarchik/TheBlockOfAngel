package com.pekar.angelblock.events.effect;

public interface IArmorEffect extends IArmorEffectBase
{
    boolean isActive();
    boolean isAnotherActive();
    boolean isAnyActive();
    boolean isAvailable();
    boolean isUnavailable();
    boolean isInfinite();
}
