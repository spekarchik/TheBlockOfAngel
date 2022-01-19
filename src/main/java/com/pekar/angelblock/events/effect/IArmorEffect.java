package com.pekar.angelblock.events.effect;

import net.minecraft.world.effect.MobEffect;

public interface IArmorEffect
{
    boolean isEffectOn();
    boolean isActive();
    boolean isEffectAvailable();
    boolean updateEffectAvailability();
    void updateEffectActivity();
    void updateEffectActivity(int amplifier);
    boolean trySwitch();
    boolean trySwitch(int amplifier);
    boolean trySwitchOff();
    boolean trySwitchOn(int amplifier);
    boolean trySwitchOn();
    void invertSwitchState();
    void updateSwitchState();
    void setSwitchState(boolean isOn);
    MobEffect getEffect();
}
