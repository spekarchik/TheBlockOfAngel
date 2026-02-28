package com.pekar.angelblock.events.player;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.mob.IMob;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public interface IPlayer extends IMob, IPlayerEvents
{
    Iterable<IPlayerArmor> getArmorTypesUsed();

    boolean isArmorElementPutOn(IPlayerArmor armor, EquipmentSlot equipmentSlot);
    boolean isFullArmorSetPutOn(IPlayerArmor armor);
    boolean isAllArmorElementsPutOn(IPlayerArmor armor, EquipmentSlot ... equipmentSlots);
    boolean isAnyArmorElementPutOn(IPlayerArmor armor);
    boolean isAnyArmorElementInclBrokenPutOn(IPlayerArmor armor);
    boolean isHelmetModifiedWithDetector(IPlayerArmor armor);
    boolean isHelmetModifiedWithNightVision(IPlayerArmor armor);
    boolean isChestPlateModifiedWithStrengthBooster(IPlayerArmor armor);
    boolean isChestPlateModifiedWithLuck(IPlayerArmor armor);
    boolean isChestPlateModifiedWithSlowFalling(IPlayerArmor armor);
    boolean areLeggingsModifiedWithHealthRegenerator(IPlayerArmor armor);
    boolean areBootsModifiedWithJumpBooster(IPlayerArmor armor);
    boolean areBootsModifiedWithSeaPower(IPlayerArmor armor);
    void updateArmorUsed();

    String getPlayerName();
    Player getPlayerEntity();

    void updateEntity(Player entity);
    void sendMessage(String message);
}
