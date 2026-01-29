package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.BiPredicate;

public interface IEffectSetup<T extends IArmorEffect>
{
    IEffectSetup<T> setupAvailability(BiPredicate<IPlayer, IArmor> predicate);
    IEffectSetup<T> setupUnavailability(BiPredicate<IPlayer, IArmor> predicate);
    IEffectSetup<T> setupAvailability(IEffectSetup<T> copyFrom);
    IEffectSetup<T> setupUnavailability(IEffectSetup<T> copyFrom);

    IEffectSetup<T> alwaysAvailable();
    IEffectSetup<T> availableOnHelmetWithDetector();
    IEffectSetup<T> availableOnHelmetWithNightVision();
    IEffectSetup<T> availableOnBootsWithJumpBooster();
    IEffectSetup<T> availableOnBootsWithSeaPower();
    IEffectSetup<T> availableOnChestPlateWithStrengthBooster();
    IEffectSetup<T> availableOnChestPlateWithSlowFalling();
    IEffectSetup<T> availableOnLeggingsWithHealthRegenerator();
    IEffectSetup<T> availableOnFullArmorSet();
    IEffectSetup<T> availableOnAnyArmorElement();
    IEffectSetup<T> availableIfSlotSet(EquipmentSlot slot);
    IEffectSetup<T> availableIfSlotsSet(EquipmentSlot... slot);

    IEffectSetup<T> unavailableIfNotModArmor(EquipmentSlot slot);

    IEffectSetup<T> showIcon();
    IEffectSetup<T> hideIcon();

    T asArmorEffect();
}
