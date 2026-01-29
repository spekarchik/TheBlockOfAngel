package com.pekar.angelblock.events.effect;

public interface ISwitchingArmorEffect extends IArmorEffect, ISwitcher
{
    void trySwitch(int amplifier);
    void trySwitchOn(int amplifier);
    void updateActivity(int amplifier);
}
