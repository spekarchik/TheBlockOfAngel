package com.pekar.angelblock.events.effect.base;

public interface ITemporaryArmorEffect extends ITemporaryBaseArmorEffect, IArmorEffect
{
    void tryRemove();
    boolean isArmorEffect();
}
