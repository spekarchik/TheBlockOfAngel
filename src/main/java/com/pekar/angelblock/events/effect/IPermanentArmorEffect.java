package com.pekar.angelblock.events.effect;

public interface IPermanentArmorEffect extends IArmorEffect
{
    void tryActivate();
    void tryActivate(int amplifier);
    void updateActivity(int amplifier);
}
