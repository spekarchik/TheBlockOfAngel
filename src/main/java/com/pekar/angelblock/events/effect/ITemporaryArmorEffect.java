package com.pekar.angelblock.events.effect;

public interface ITemporaryArmorEffect extends ITemporaryBaseArmorEffect, IArmorEffect
{
    void tryRemove();
    boolean isArmorEffect();
}
