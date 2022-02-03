package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.BiPredicate;

abstract class ArmorEffect implements IArmorEffect
{
    protected IPlayer player;
    protected IArmor armor;
    protected MobEffect effectType;
    protected boolean isOn;
    protected boolean isAvailable;
    protected int defaultAmplifier;
    private BiPredicate<IPlayer, IArmor> availabilityPredicate = (p, a) -> false;

    protected ArmorEffect(IPlayer player, IArmor armor, MobEffect effectType, int defaultAmplifier)
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
        if (isEffectOn() && isEffectAvailable())
        {
            if (isActive() && canResetEffect())
            {
                player.clearEffect(effectType);
            }

            if (!isActive() || canResetEffect())
            {
                player.setEffect(effectType, amplifier);
            }
        }
        else
        {
            trySwitchOff();
        }
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
    public final MobEffect getEffect()
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
    public final IArmorEffect availableOnFullArmorSet()
    {
        availabilityPredicate = (player1, armor1) ->
                player1.isFullArmorSetPutOn(armor1.getArmorElementNames());

        return this;
    }

    @Override
    public final IArmorEffect availableOnAnyArmorElement()
    {
        availabilityPredicate = (player1, armor1) ->
                player1.isAnyArmorElementPutOn(armor1.getArmorElementNames());

        return this;
    }

    @Override
    public final IArmorEffect availableOnBootsAndLeggings()
    {
        availabilityPredicate = (player1, armor1) ->
                player1.isAllArmorElementsPutOn(armor1.getBootsName(), armor1.getLeggingsName());

        return this;
    }

    @Override
    public final IArmorEffect availableOnHelmetAndChestplate()
    {
        availabilityPredicate = (player1, armor1) ->
                player1.isAllArmorElementsPutOn(armor1.getHelmetName(), armor1.getChestPlateName());

        return this;
    }

    @Override
    public final IArmorEffect availableIfSlotSet(EquipmentSlot slot)
    {
        availabilityPredicate = (player1, armor1) ->
        {
            var requiredElement = switch (slot)
                    {
                        case HEAD -> armor1.getHelmetName();
                        case CHEST -> armor1.getChestPlateName();
                        case LEGS -> armor1.getLeggingsName();
                        case FEET -> armor1.getBootsName();
                        default -> null;
                    };

            return player1.isArmorElementPutOn(requiredElement);
        };

        return this;
    }

    @Override
    public final IArmorEffect availableIfSlotsSet(EquipmentSlot... slots)
    {
        availabilityPredicate = (player1, armor1) ->
        {
            for (var slot : slots)
            {
                switch (slot)
                {
                    case HEAD:
                        if (!player1.isArmorElementPutOn(armor1.getHelmetName())) return false;
                        break;
                    case CHEST:
                        if (!player1.isArmorElementPutOn(armor1.getChestPlateName())) return false;
                        break;
                    case LEGS:
                        if (!player1.isArmorElementPutOn(armor1.getLeggingsName())) return false;
                        break;
                    case FEET:
                        if (!player1.isArmorElementPutOn(armor1.getBootsName())) return false;
                        break;
                    default:
                };

            }

            return true;
        };

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
