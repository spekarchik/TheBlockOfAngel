package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.BiPredicate;

abstract class ArmorEffect implements IArmorEffect
{
    protected IPlayer player;
    protected IArmor armor;
    protected Holder<MobEffect> effectType;
    protected boolean isOn;
    protected boolean wasAvailable;
    protected boolean isAvailable;
    protected int defaultAmplifier;
    protected boolean showIcon;
    private BiPredicate<IPlayer, IArmor> availabilityPredicate = (p, a) -> false;

    protected ArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        this.player = player;
        this.armor = armor;
        this.effectType = effectType;
        this.defaultAmplifier = defaultAmplifier;
    }

    @Override
    public boolean isEffectOn()
    {
        return isOn;
    }

    @Override
    public final boolean isActive()
    {
        return player.isEffectActive(effectType);
    }

    @Override
    public final boolean isEffectAvailable()
    {
        return isAvailable;
    }

    @Override
    public final boolean updateEffectAvailability()
    {
        wasAvailable = isAvailable;
        return isAvailable = availabilityPredicate.test(player, armor);
    }

    @Override
    public final boolean trySwitch()
    {
        return trySwitch(defaultAmplifier);
    }

    @Override
    public boolean trySwitch(int amplifier)
    {
        boolean isOnOld = isOn;
        isOn = isAvailable ? !isOn : false;
        updateEffectActivity(amplifier);
        return isOn != isOnOld;
    }

    @Override
    public final void updateSwitchState()
    {
        isOn = isActive();
    }

    @Override
    public final void invertSwitchState()
    {
        isOn = !isOn;
    }

    protected final void setSwitchState(boolean isOn)
    {
        this.isOn = isOn;
    }

    @Override
    public final void updateEffectActivity(int amplifier)
    {
        if (isEffectAvailable() || wasAvailable)
        {
            if (isEffectOn() && isEffectAvailable())
            {
                if (isActive() && canResetEffect())
                {
                    player.clearEffect(effectType);
                }

                if (!isActive() || canResetEffect())
                {
                    player.setEffect(effectType, amplifier, showIcon);
                }
            }
            else
            {
                trySwitchOff();
            }
        }

        wasAvailable = isAvailable;
    }

    @Override
    public final void updateEffectActivity()
    {
        updateEffectActivity(defaultAmplifier);
    }

    @Override
    public boolean trySwitchOff()
    {
        if (canClearEffect() && isActive())
        {
            isOn = false;
            player.clearEffect(effectType);
        }

        return !isActive();
    }

    @Override
    public boolean trySwitchOn(int amplifier)
    {
        isOn = true;
        updateEffectActivity(amplifier);
        return isActive();
    }

    @Override
    public final boolean trySwitchOn()
    {
        return trySwitchOn(defaultAmplifier);
    }

    @Override
    public final Holder<MobEffect> getEffect()
    {
        return effectType;
    }

    @Override
    public final IArmorEffect setupAvailability(BiPredicate<IPlayer, IArmor> predicate)
    {
        availabilityPredicate = predicate;
        return this;
    }

    @Override
    public IArmorEffect availableOnHelmetWithDetector()
    {
        availabilityPredicate = IPlayer::isArmorModifiedWithDetector;
        return this;
    }

    @Override
    public IArmorEffect availableOnBootsWithStrengthBooster()
    {
        availabilityPredicate = IPlayer::areBootsModifiedWithStrengthBooster;
        return this;
    }

    @Override
    public IArmorEffect availableOnBootsWithSeaPower()
    {
        availabilityPredicate = IPlayer::areBootsModifiedWithSeaPower;
        return this;
    }

    @Override
    public IArmorEffect availableOnChestPlateWithStrengthBooster()
    {
        availabilityPredicate = IPlayer::isChestPlateModifiedWithStrengthBooster;
        return this;
    }

    @Override
    public IArmorEffect availableOnLeggingsWithHealthRegenerator()
    {
        availabilityPredicate = IPlayer::isArmorModifiedWithHealthRegenerator;
        return this;
    }

    @Override
    public final IArmorEffect availableOnFullArmorSet()
    {
        availabilityPredicate = IPlayer::isFullArmorSetPutOn;
        return this;
    }

    @Override
    public final IArmorEffect availableOnAnyArmorElement()
    {
        availabilityPredicate = IPlayer::isAnyArmorElementPutOn;
        return this;
    }

    @Override
    public final IArmorEffect availableIfSlotSet(EquipmentSlot slot)
    {
        availabilityPredicate = (player1, armor1) -> player1.isArmorElementPutOn(armor1, slot);
        return this;
    }

    @Override
    public final IArmorEffect availableIfSlotsSet(EquipmentSlot... slots)
    {
        availabilityPredicate = (player1, armor1) -> player1.isAllArmorElementsPutOn(armor1, slots);
        return this;
    }

    @Override
    public IArmorEffect showIcon()
    {
        showIcon = true;
        return this;
    }

    protected boolean canClearEffect()
    {
        return true;
    }

    protected boolean canResetEffect()
    {
        return true;
    }
}
