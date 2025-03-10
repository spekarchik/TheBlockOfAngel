package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.BiPredicate;

public interface IEffectSetup<T extends IArmorEffect>
{
    T setupAvailability(BiPredicate<IPlayer, IArmor> predicate);
    T setupAvailability(IEffectSetup<T> copyFrom);
    T alwaysAvailable();
    T availableOnHelmetWithDetector();
    T availableOnBootsWithJumpBooster();
    T availableOnBootsWithSeaPower();
    T availableOnChestPlateWithStrengthBooster();
    T availableOnChestPlateWithSlowFalling();
    T availableOnLeggingsWithHealthRegenerator();
    T availableOnFullArmorSet();
    T availableOnAnyArmorElement();
    T availableIfSlotSet(EquipmentSlot slot);
    T availableIfSlotsSet(EquipmentSlot... slot);
    T showIcon();
    T hideIcon();
}
