package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.armor.ModHumanoidArmor;
import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.BiPredicate;

interface EffectSetup<T extends IArmorEffect> extends IEffectSetup<T>
{
    void setShowIcon(boolean value);

    void setAvailabilityPredicate(BiPredicate<IPlayer, IArmor> value);

    BiPredicate<IPlayer, IArmor> getAvailabilityPredicate();

    void setUnavailabilityPredicate(BiPredicate<IPlayer, IArmor> value);

    BiPredicate<IPlayer, IArmor> getUnavailabilityPredicate();

    default IEffectSetup<T> alwaysAvailable()
    {
        setAvailabilityPredicate((player, armor) -> true);
        return this;
    }

    default IEffectSetup<T> setupAvailability(IEffectSetup<T> copyFrom)
    {
        setAvailabilityPredicate(((EffectSetup<T>) copyFrom).getAvailabilityPredicate());
        return this;
    }

    default IEffectSetup<T> setupUnavailability(IEffectSetup<T> copyFrom)
    {
        setUnavailabilityPredicate(((EffectSetup<T>) copyFrom).getUnavailabilityPredicate());
        return this;
    }

    default IEffectSetup<T> setupAvailability(BiPredicate<IPlayer, IArmor> predicate)
    {
        setAvailabilityPredicate(predicate);
        return this;
    }

    default IEffectSetup<T> setupUnavailability(BiPredicate<IPlayer, IArmor> predicate)
    {
        setUnavailabilityPredicate(predicate);
        return this;
    }

    default IEffectSetup<T> availableOnHelmetWithDetector()
    {
        setAvailabilityPredicate((player, armor) -> player.isHelmetModifiedWithDetector(armor) && !player.getEntity().hasEffect(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT));
        return this;
    }

    default IEffectSetup<T> availableOnHelmetWithNightVision()
    {
        setAvailabilityPredicate((player, armor) -> player.isHelmetModifiedWithNightVision(armor) && !player.getEntity().hasEffect(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT));
        return this;
    }

    default IEffectSetup<T> availableOnBootsWithJumpBooster()
    {
        setAvailabilityPredicate(IPlayer::areBootsModifiedWithJumpBooster);
        return this;
    }

    default IEffectSetup<T> availableOnBootsWithSeaPower()
    {
        setAvailabilityPredicate(IPlayer::areBootsModifiedWithSeaPower);
        return this;
    }

    default IEffectSetup<T> availableOnChestPlateWithStrengthBooster()
    {
        setAvailabilityPredicate(IPlayer::isChestPlateModifiedWithStrengthBooster);
        return this;
    }

    default IEffectSetup<T> availableOnChestPlateWithSlowFalling()
    {
        setAvailabilityPredicate(IPlayer::isChestPlateModifiedWithSlowFalling);
        return this;
    }

    default IEffectSetup<T> availableOnLeggingsWithHealthRegenerator()
    {
        setAvailabilityPredicate(IPlayer::areLeggingsModifiedWithHealthRegenerator);
        return this;
    }

    default IEffectSetup<T> availableOnFullArmorSet()
    {
        setAvailabilityPredicate(IPlayer::isFullArmorSetPutOn);
        return this;
    }

    default IEffectSetup<T> availableOnAnyArmorElement()
    {
        setAvailabilityPredicate(IPlayer::isAnyArmorElementPutOn);
        return this;
    }

    default IEffectSetup<T> availableIfSlotSet(EquipmentSlot slot)
    {
        setAvailabilityPredicate((player1, armor1) -> player1.isArmorElementPutOn(armor1, slot));
        return this;
    }

    default IEffectSetup<T> availableIfSlotsSet(EquipmentSlot... slots)
    {
        setAvailabilityPredicate((player1, armor1) -> player1.isAllArmorElementsPutOn(armor1, slots));
        return this;
    }

    @Override
    default IEffectSetup<T> unavailableIfNotModArmor(EquipmentSlot slot)
    {
        setUnavailabilityPredicate(((player, armor) -> {
            var stack = player.getEntity().getItemBySlot(slot);
            return stack.isEmpty() || !(stack.getItem() instanceof ModHumanoidArmor);
        }));

        return this;
    }

    @Override
    default IEffectSetup<T> showIcon()
    {
        setShowIcon(true);
        return this;
    }

    @Override
    default IEffectSetup<T> hideIcon()
    {
        setShowIcon(false);
        return this;
    }
}
