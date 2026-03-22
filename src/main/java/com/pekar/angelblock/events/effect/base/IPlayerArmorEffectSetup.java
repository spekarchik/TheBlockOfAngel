package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.entity.EquipmentSlot;

public interface IPlayerArmorEffectSetup<E extends IArmorEffectWithOptions<IPlayer, IPlayerArmor>> extends IArmorEffectSetup<E, IPlayer, IPlayerArmor>
{
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnHelmetWithDetector();
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnHelmetWithNightVision();
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnBootsWithJumpBooster();
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnBootsWithSeaPower();
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnChestPlateWithStrengthBooster();
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnChestPlateWithSlowFalling();
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnLeggingsWithHealthRegenerator();
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnFullArmorSet();
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnAnyArmorElement();
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableIfSlotSet(EquipmentSlot slot);
    IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableIfSlotsSet(EquipmentSlot... slot);
}
