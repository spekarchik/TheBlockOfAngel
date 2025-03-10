package com.pekar.angelblock.events.effect;

public interface IArmorEffectBase
{
    State getState();
    void updateActivity();
    void updateAvailability();
    void updateSwitchState();
}
