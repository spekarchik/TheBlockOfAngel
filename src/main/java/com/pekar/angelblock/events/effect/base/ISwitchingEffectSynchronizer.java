package com.pekar.angelblock.events.effect.base;

public interface ISwitchingEffectSynchronizer extends IArmorEffectBase, ISwitcher
{
    void addDependentEffect(IExtendedSwitchingArmorEffect effect);
    void addDependentInvertedEffect(IExtendedSwitchingArmorEffect effect);
    void trySwitch(int masterEffectAmplifier);
    void trySwitchOn(int masterEffectAmplifier);
    void updateActivity(int masterEffectAmplifier);
    void updateDependentEffectsActivity();
    boolean isMasterActive();
    boolean isMasterAvailable();
}
