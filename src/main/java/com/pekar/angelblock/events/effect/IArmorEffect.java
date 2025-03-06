package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.BiPredicate;

public interface IArmorEffect
{
    boolean isEffectOn();
    boolean isActive();
    boolean isEffectAvailable();
    boolean updateEffectAvailability();
    void updateEffectActivity();
    void updateEffectActivity(int amplifier);
    boolean trySwitch();
    boolean trySwitchTo(boolean switchOn);
    boolean trySwitch(int amplifier);
    boolean trySwitchOff();
    boolean trySwitchOn(int amplifier);
    boolean trySwitchOn();
    void invertSwitchState();
    void updateSwitchState();

    IArmorEffect setupAvailability(BiPredicate<IPlayer, IArmor> predicate);
    IArmorEffect setupAvailability(IArmorEffect copyFrom);
    IArmorEffect alwaysAvailable();
    IArmorEffect availableOnHelmetWithDetector();
    IArmorEffect availableOnBootsWithStrengthBooster();
    IArmorEffect availableOnBootsWithSeaPower();
    IArmorEffect availableOnChestPlateWithStrengthBooster();
    IArmorEffect availableOnChestPlateWithLevitation();
    IArmorEffect availableOnLeggingsWithHealthRegenerator();
    IArmorEffect availableOnFullArmorSet();
    IArmorEffect availableOnAnyArmorElement();
    IArmorEffect availableIfSlotSet(EquipmentSlot slot);
    IArmorEffect availableIfSlotsSet(EquipmentSlot ...slot);
    IArmorEffect showIcon();
    IArmorEffect hideIcon();

    Holder<MobEffect> getEffect();
}
