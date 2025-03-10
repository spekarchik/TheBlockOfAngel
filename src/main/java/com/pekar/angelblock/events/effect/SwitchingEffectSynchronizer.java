package com.pekar.angelblock.events.effect;

import java.util.ArrayList;
import java.util.List;

public class SwitchingEffectSynchronizer implements ISwitchingEffectSynchronizer
{
    private final IExtendedSwitchingArmorEffect masterEffect;
    private final List<IExtendedSwitchingArmorEffect> dependentEffects = new ArrayList<>();
    private final List<IExtendedSwitchingArmorEffect> dependentInvertedEffects = new ArrayList<>();

    public SwitchingEffectSynchronizer(IExtendedSwitchingArmorEffect masterEffect)
    {
        this.masterEffect = masterEffect;
    }

    @Override
    public final boolean isOn()
    {
        return masterEffect.isOn();
    }

    @Override
    public final State getState()
    {
        return masterEffect.getState();
    }

    @Override
    public void updateAvailability()
    {
        masterEffect.updateAvailability();

        for (var effect : dependentEffects)
        {
            effect.updateAvailability();
        }

        for (var effect : dependentInvertedEffects)
        {
            effect.updateAvailability();
        }
    }

    @Override
    public void updateActivity()
    {
        masterEffect.updateActivity();
        updateDependentEffectsActivity();
    }

    @Override
    public void updateActivity(int masterEffectAmplifier)
    {
        masterEffect.updateActivity(masterEffectAmplifier);
        updateDependentEffectsActivity();
    }

    @Override
    public void updateDependentEffectsActivity()
    {
        updateDependentSwitchStates();

        for (var effect : dependentEffects)
        {
            effect.updateActivity();
        }

        for (var effect : dependentInvertedEffects)
        {
            effect.updateActivity();
        }
    }

    @Override
    public boolean isMasterActive()
    {
        return masterEffect.isActive();
    }

    @Override
    public boolean isMasterAvailable()
    {
        return masterEffect.isAvailable();
    }

    @Override
    public void trySwitch()
    {
        masterEffect.trySwitch();
        switchDependentEffects();
    }

    @Override
    public final void trySwitchTo(boolean switchOn)
    {
        if (switchOn) trySwitchOn();
        else trySwitchOff();
    }

    @Override
    public void trySwitchOff()
    {
        masterEffect.trySwitchOff();

        for (var effect : dependentEffects)
        {
            effect.trySwitchOff();
        }

        for (var effect : dependentInvertedEffects)
        {
            effect.trySwitchOn();
        }
    }

    @Override
    public void trySwitchOn()
    {
        masterEffect.trySwitchOn();

        for (var effect : dependentEffects)
        {
            effect.trySwitchOn();
        }

        for (var effect : dependentInvertedEffects)
        {
            effect.trySwitchOff();
        }
    }

    @Override
    public void trySwitch(int masterEffectAmplifier)
    {
        masterEffect.trySwitch(masterEffectAmplifier);
        switchDependentEffects();
    }

    @Override
    public void trySwitchOn(int masterEffectAmplifier)
    {
        masterEffect.trySwitchOn(masterEffectAmplifier);

        for (var effect : dependentEffects)
        {
            effect.trySwitchOn();
        }

        for (var effect : dependentInvertedEffects)
        {
            effect.trySwitchOff();
        }
    }

    @Override
    public void invertSwitchState()
    {
        masterEffect.invertSwitchState();
        updateDependentSwitchStates();
    }

    @Override
    public void updateSwitchState()
    {
        masterEffect.updateSwitchState();
        updateDependentSwitchStates();
    }

    protected void setSwitchState(boolean isOn)
    {
        masterEffect.setSwitchState(isOn);
        updateDependentSwitchStates();
    }

    public void addDependentEffect(IExtendedSwitchingArmorEffect effect)
    {
        dependentEffects.add(effect);
        effect.setSwitchState(masterEffect.isOn());
    }

    public void addDependentInvertedEffect(IExtendedSwitchingArmorEffect effect)
    {
        dependentInvertedEffects.add(effect);
        effect.setSwitchState(!masterEffect.isOn());
    }

    private void switchDependentEffects()
    {
        boolean isMasterEffectAvailable = masterEffect.isAvailable();

        if (isMasterEffectAvailable)
        {
            if (masterEffect.isOn())
            {
                for (var effect : dependentEffects)
                    effect.trySwitchOn();

                for (var effect : dependentInvertedEffects)
                    effect.trySwitchOff();
            }
            else
            {
                for (var effect : dependentEffects)
                    effect.trySwitchOff();

                for (var effect : dependentInvertedEffects)
                    effect.trySwitchOn();
            }
        }
        else
        {
            for (var effect : dependentEffects)
                effect.trySwitchOff();

            for (var effect : dependentInvertedEffects)
                effect.trySwitchOff();
        }
    }

    private void updateDependentSwitchStates()
    {
        for (var effect : dependentEffects)
        {
            effect.setSwitchState(masterEffect.isOn());
        }

        for (var effect : dependentInvertedEffects)
        {
            effect.setSwitchState(!masterEffect.isOn());
        }
    }
}
