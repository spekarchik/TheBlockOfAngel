package com.pekar.angelblock.events.player;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.mob.IMob;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public interface IPlayer extends IMob, IPlayerEvents
{
    Iterable<IPlayerArmor> getArmorTypesUsed();

    boolean isArmorElementPutOn(IArmor armor, EquipmentSlot equipmentSlot);
    boolean isFullArmorSetPutOn(IArmor armor);
    boolean isAllArmorElementsPutOn(IArmor armor, EquipmentSlot ... equipmentSlots);
    boolean isAnyArmorElementPutOn(IArmor armor);
    boolean isAnyArmorElementInclBrokenPutOn(IArmor armor);
    boolean isHelmetModifiedWithDetector(IArmor armor);
    boolean isHelmetModifiedWithNightVision(IArmor armor);
    boolean isChestPlateModifiedWithStrengthBooster(IArmor armor);
    boolean isChestPlateModifiedWithLuck(IArmor armor);
    boolean isChestPlateModifiedWithSlowFalling(IArmor armor);
    boolean areLeggingsModifiedWithHealthRegenerator(IArmor armor);
    boolean areBootsModifiedWithJumpBooster(IArmor armor);
    boolean areBootsModifiedWithSeaPower(IArmor armor);
    void updateArmorUsed();

    String getPlayerName();
    Player getPlayerEntity();

    void updateEntity(Player entity);
    void sendMessage(String message);
}
