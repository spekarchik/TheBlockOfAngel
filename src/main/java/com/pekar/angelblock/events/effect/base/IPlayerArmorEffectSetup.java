package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.entity.EquipmentSlot;

public interface IPlayerArmorEffectSetup<E extends IArmorEffectWithOptions<IPlayer>> extends IArmorEffectSetup<E, IPlayer>
{
    IArmorEffectSetup<E, IPlayer> availableOnHelmetWithDetector();
    IArmorEffectSetup<E, IPlayer> availableOnHelmetWithNightVision();
    IArmorEffectSetup<E, IPlayer> availableOnBootsWithJumpBooster();
    IArmorEffectSetup<E, IPlayer> availableOnBootsWithSeaPower();
    IArmorEffectSetup<E, IPlayer> availableOnChestPlateWithStrengthBooster();
    IArmorEffectSetup<E, IPlayer> availableOnChestPlateWithSlowFalling();
    IArmorEffectSetup<E, IPlayer> availableOnLeggingsWithHealthRegenerator();
    IArmorEffectSetup<E, IPlayer> availableOnFullArmorSet();
    IArmorEffectSetup<E, IPlayer> availableOnAnyArmorElement();
    IArmorEffectSetup<E, IPlayer> availableIfSlotSet(EquipmentSlot slot);
    IArmorEffectSetup<E, IPlayer> availableIfSlotsSet(EquipmentSlot... slot);
}
