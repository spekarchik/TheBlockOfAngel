package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.effect.State;

public interface IArmorEffectBase
{
    State getState();
    void updateActivity();
    void updateAvailability();
    void updateSwitchState();
}
