package com.pekar.angelblock.events.effect;

public interface IArmorEffect extends IArmorEffectBase
{
    boolean isActive();
    boolean isAnotherActive();
    boolean isAvailable();
}
