package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class SwitchingEffectSynchronizer implements IArmorEffect
{
    private final SwitchingArmorEffect masterEffect;
    private final List<SwitchingArmorEffect> dependentEffects = new ArrayList<>();
    private final List<SwitchingArmorEffect> dependentInvertedEffects = new ArrayList<>();

    public SwitchingEffectSynchronizer(SwitchingArmorEffect masterEffect)
    {
        this.masterEffect = masterEffect;
    }

    @Override
    public boolean isEffectOn()
    {
        return masterEffect.isEffectOn();
    }

    @Override
    public boolean isActive()
    {
        return masterEffect.isActive();
    }

    @Override
    public boolean isEffectAvailable()
    {
        return masterEffect.isEffectAvailable();
    }

    @Override
    public boolean updateEffectAvailability()
    {
        boolean result = masterEffect.updateEffectAvailability();

        for (SwitchingArmorEffect effect : dependentEffects)
        {
            effect.updateEffectAvailability();
        }

        for (SwitchingArmorEffect effect : dependentInvertedEffects)
        {
            effect.updateEffectAvailability();
        }

        return result;
    }

    @Override
    public void updateEffectActivity()
    {
        updateEffectActivity(masterEffect.defaultAmplifier);
    }

    public void updateDependentEffectsActivity()
    {
        updateDependentSwitchStates();

        for (SwitchingArmorEffect effect : dependentEffects)
        {
            effect.updateEffectActivity();
        }

        for (SwitchingArmorEffect effect : dependentInvertedEffects)
        {
            effect.updateEffectActivity();
        }
    }

    @Override
    public void updateEffectActivity(int amplifier)
    {
        masterEffect.updateEffectActivity(amplifier);
        updateDependentEffectsActivity();
    }

    @Override
    public boolean trySwitch()
    {
        return trySwitch(masterEffect.defaultAmplifier);
    }

    @Override
    public boolean trySwitch(int amplifier)
    {
        boolean result = masterEffect.trySwitch(amplifier);

        switchDependentEffects();

        return result;
    }

    @Override
    public boolean trySwitchOff()
    {
        boolean result = masterEffect.trySwitchOff();

        for (SwitchingArmorEffect effect : dependentEffects)
        {
            effect.trySwitchOff();
        }

        for (SwitchingArmorEffect effect : dependentInvertedEffects)
        {
            effect.trySwitchOn();
        }

        return result;
    }

    @Override
    public boolean trySwitchOn(int amplifier)
    {
        boolean result = masterEffect.trySwitchOn(amplifier);

        for (SwitchingArmorEffect effect : dependentEffects)
        {
            effect.trySwitchOn();
        }

        for (SwitchingArmorEffect effect : dependentInvertedEffects)
        {
            effect.trySwitchOff();
        }

        return result;
    }

    @Override
    public boolean trySwitchOn()
    {
        return trySwitchOn(masterEffect.defaultAmplifier);
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

    @Override
    public MobEffect getEffect()
    {
        return masterEffect.getEffect();
    }

    public void addDependentEffect(SwitchingArmorEffect effect)
    {
        dependentEffects.add(effect);
        effect.setSwitchState(masterEffect.isOn);
    }

    public void addDependentInvertedEffect(SwitchingArmorEffect effect)
    {
        dependentInvertedEffects.add(effect);
        effect.setSwitchState(!masterEffect.isOn);
    }

    @Override
    public IArmorEffect setupAvailability(BiPredicate<IPlayer, IArmor> predicate)
    {
        return this;
    }

    @Override
    public IArmorEffect availableOnHelmetWithDetector()
    {
        return this;
    }

    @Override
    public IArmorEffect availableOnBootsWithStrengthBooster()
    {
        return this;
    }

    @Override
    public IArmorEffect availableOnBootsWithSeaPower()
    {
        return this;
    }

    @Override
    public IArmorEffect availableOnChestPlateWithStrengthBooster()
    {
        return this;
    }

    @Override
    public IArmorEffect availableOnLeggingsWithHealthRegenerator()
    {
        return this;
    }

    @Override
    public IArmorEffect availableOnFullArmorSet()
    {
        return this;
    }

    @Override
    public IArmorEffect availableOnAnyArmorElement()
    {
        return this;
    }

    @Override
    public IArmorEffect availableIfSlotSet(EquipmentSlot slot)
    {
        return this;
    }

    @Override
    public IArmorEffect availableIfSlotsSet(EquipmentSlot... slot)
    {
        return this;
    }

    @Override
    public IArmorEffect showIcon()
    {
        return this;
    }

    private void switchDependentEffects()
    {
        for (SwitchingArmorEffect effect : dependentEffects)
        {
            if (masterEffect.isEffectOn())
            {
                effect.trySwitchOn();
            }
            else
            {
                effect.trySwitchOff();
            }
        }

        for (SwitchingArmorEffect effect : dependentInvertedEffects)
        {
            if (masterEffect.isEffectOn())
            {
                effect.trySwitchOff();
            }
            else
            {
                effect.trySwitchOn();
            }
        }
    }

    private void updateDependentSwitchStates()
    {
        for (SwitchingArmorEffect effect : dependentEffects)
        {
            effect.setSwitchState(masterEffect.isOn);
        }

        for (SwitchingArmorEffect effect : dependentInvertedEffects)
        {
            effect.setSwitchState(!masterEffect.isOn);
        }
    }
}
