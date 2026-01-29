package com.pekar.angelblock.events.effect;

public enum State
{
    OFF,
    ON;

    public boolean isOff()
    {
        return this == OFF;
    }

    public boolean isOn()
    {
        return this == ON;
    }
}
