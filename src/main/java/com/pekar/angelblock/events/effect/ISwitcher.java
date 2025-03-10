package com.pekar.angelblock.events.effect;

public interface ISwitcher
{
    boolean isOn();

    void trySwitchTo(boolean switchOn);
    void trySwitch();
    void trySwitchOn();
    void trySwitchOff();

    void invertSwitchState();
}
