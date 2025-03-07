package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
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
    public boolean trySwitchTo(boolean switchOn)
    {
        return switchOn ? trySwitchOn() : trySwitchOff();
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
    public Holder<MobEffect> getEffect()
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
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect setupAvailability(IArmorEffect copyFrom)
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect alwaysAvailable()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect availableOnHelmetWithDetector()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect availableOnBootsWithJumpBooster()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect availableOnBootsWithSeaPower()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect availableOnChestPlateWithStrengthBooster()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect availableOnChestPlateWithSlowFalling()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect availableOnLeggingsWithHealthRegenerator()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect availableOnFullArmorSet()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect availableOnAnyArmorElement()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect availableIfSlotSet(EquipmentSlot slot)
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect availableIfSlotsSet(EquipmentSlot... slot)
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect showIcon()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    @Override
    public IArmorEffect hideIcon()
    {
        throw new UnsupportedOperationException("Method not supported for SwitchingEffectSynchronizer.");
    }

    private void switchDependentEffects()
    {
        boolean isMasterEffectAvailable = masterEffect.isEffectAvailable();

        for (SwitchingArmorEffect effect : dependentEffects)
        {
            if (isMasterEffectAvailable)
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
            else
            {
                effect.trySwitch();
            }
        }

        for (SwitchingArmorEffect effect : dependentInvertedEffects)
        {
            if (isMasterEffectAvailable)
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
            else
            {
                effect.trySwitch();
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
