package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.armor.ModArmor;
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

    T getSelf();

    default T alwaysAvailable()
    {
        setAvailabilityPredicate((player, armor) -> true);
        return getSelf();
    }

    default T setupAvailability(IEffectSetup<T> copyFrom)
    {
        setAvailabilityPredicate(((EffectSetup<T>) copyFrom).getAvailabilityPredicate());
        return getSelf();
    }

    default T setupUnavailability(IEffectSetup<T> copyFrom)
    {
        setUnavailabilityPredicate(((EffectSetup<T>) copyFrom).getUnavailabilityPredicate());
        return getSelf();
    }

    default T setupAvailability(BiPredicate<IPlayer, IArmor> predicate)
    {
        setAvailabilityPredicate(predicate);
        return getSelf();
    }

    default T setupUnavailability(BiPredicate<IPlayer, IArmor> predicate)
    {
        setUnavailabilityPredicate(predicate);
        return getSelf();
    }

    default T availableOnHelmetWithDetector()
    {
        setAvailabilityPredicate((player, armor) -> player.isHelmetModifiedWithDetector(armor) && !player.getEntity().hasEffect(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT));
        return getSelf();
    }

    default T availableOnHelmetWithNightVision()
    {
        setAvailabilityPredicate((player, armor) -> player.isHelmetModifiedWithNightVision(armor) && !player.getEntity().hasEffect(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT));
        return getSelf();
    }

    default T availableOnBootsWithJumpBooster()
    {
        setAvailabilityPredicate(IPlayer::areBootsModifiedWithJumpBooster);
        return getSelf();
    }

    default T availableOnBootsWithSeaPower()
    {
        setAvailabilityPredicate(IPlayer::areBootsModifiedWithSeaPower);
        return getSelf();
    }

    default T availableOnChestPlateWithStrengthBooster()
    {
        setAvailabilityPredicate(IPlayer::isChestPlateModifiedWithStrengthBooster);
        return getSelf();
    }

    default T availableOnChestPlateWithSlowFalling()
    {
        setAvailabilityPredicate(IPlayer::isChestPlateModifiedWithSlowFalling);
        return getSelf();
    }

    default T availableOnLeggingsWithHealthRegenerator()
    {
        setAvailabilityPredicate(IPlayer::areLeggingsModifiedWithHealthRegenerator);
        return getSelf();
    }

    default T availableOnFullArmorSet()
    {
        setAvailabilityPredicate(IPlayer::isFullArmorSetPutOn);
        return getSelf();
    }

    default T availableOnAnyArmorElement()
    {
        setAvailabilityPredicate(IPlayer::isAnyArmorElementPutOn);
        return getSelf();
    }

    default T availableIfSlotSet(EquipmentSlot slot)
    {
        setAvailabilityPredicate((player1, armor1) -> player1.isArmorElementPutOn(armor1, slot));
        return getSelf();
    }

    default T availableIfSlotsSet(EquipmentSlot... slots)
    {
        setAvailabilityPredicate((player1, armor1) -> player1.isAllArmorElementsPutOn(armor1, slots));
        return getSelf();
    }

    @Override
    default T unavailableIfNotModArmor(EquipmentSlot slot)
    {
        setUnavailabilityPredicate(((player, armor) -> {
            var stack = player.getEntity().getItemBySlot(slot);
            return stack.isEmpty() || !(stack.getItem() instanceof ModArmor);
        }));

        return getSelf();
    }

    @Override
    default T showIcon()
    {
        setShowIcon(true);
        return getSelf();
    }

    @Override
    default T hideIcon()
    {
        setShowIcon(false);
        return getSelf();
    }
}
