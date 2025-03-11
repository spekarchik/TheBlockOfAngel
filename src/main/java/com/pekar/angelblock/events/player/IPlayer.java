package com.pekar.angelblock.events.player;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.effect.ITemporaryArmorEffect;
import com.pekar.angelblock.events.effect.ITemporaryBaseArmorEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public interface IPlayer extends IPlayerEvents
{
    Iterable<IArmor> getArmorTypesUsed();

    boolean isArmorElementPutOn(IArmor armor, EquipmentSlot equipmentSlot);
    boolean isFullArmorSetPutOn(IArmor armor);
    boolean isAllArmorElementsPutOn(IArmor armor, EquipmentSlot ... equipmentSlots);
    boolean isAnyArmorElementPutOn(IArmor armor);
    boolean isHelmetModifiedWithDetector(IArmor armor);
    boolean isChestPlateModifiedWithStrengthBooster(IArmor armor);
    boolean isChestPlateModifiedWithLuck(IArmor armor);
    boolean isChestPlateModifiedWithSlowFalling(IArmor armor);
    boolean areLeggingsModifiedWithHealthRegenerator(IArmor armor);
    boolean areBootsModifiedWithJumpBooster(IArmor armor);
    boolean areBootsModifiedWithSeaPower(IArmor armor);
    void updateArmorUsed();

    boolean isEffectActive(Holder<MobEffect> effect);
    boolean hasArmorEffect(Holder<MobEffect> effect);
    boolean hasAnotherEffect(Holder<MobEffect> effect);

    IModMobEffectInstance setEffect(Holder<MobEffect> effect, int amplifier);
    IModMobEffectInstance setEffect(Holder<MobEffect> effect, int amplifier, boolean showIcon);
    IModMobEffectInstance setEffect(Holder<MobEffect> effect, int duration, int amplifier);
    IModMobEffectInstance setEffect(Holder<MobEffect> effect, int duration, int amplifier, boolean showIcon);
    IModMobEffectInstance setEffect(ITemporaryBaseArmorEffect armorEffect, int duration, int amplifier);
    IModMobEffectInstance setEffect(ITemporaryBaseArmorEffect armorEffect, int duration, int amplifier, boolean showIcon);
    MobEffectInstance getEffectInstance(Holder<MobEffect> effect);
    void clearEffect(Holder<MobEffect> effect);

    String getPlayerName();
    Player getEntity();

    boolean isOverworld();
    boolean isNether();
    boolean isEnd();

    void updateEntity(Player entity);
    void sendMessage(String message);
}
