package com.pekar.angelblock.events.effect.base;

public interface IArmorEffectBase
{
    State getState();
    void updateActivity();
    void updateAvailability();
    void updateSwitchState();
}
