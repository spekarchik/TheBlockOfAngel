package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.events.effect.IArmorEffect;

public interface ITemporaryArmorEffect extends IArmorEffect
{
    boolean trySwitchForDuration(int duration);
    boolean trySwitchForDuration(int amplifier, int duration);
    boolean isArmorEffect();
    void resetIsArmorEffect();
}
