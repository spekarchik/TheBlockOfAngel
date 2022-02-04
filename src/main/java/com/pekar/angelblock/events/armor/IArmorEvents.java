package com.pekar.angelblock.events.armor;

import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

public interface IArmorEvents
{
    void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event);
    void onLivingHurtEvent(LivingHurtEvent event);
    void onLivingAttackEvent(LivingAttackEvent event);
    void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event);
    void onLivingJumpEvent(LivingEvent.LivingJumpEvent event);
    void onLivingFallEvent(LivingFallEvent event);
    void onKeyInputEvent(String pressedKeyDescription);
    void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event);
    void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event);
    void onBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event);
    void onBeingInWater();
    void onCreeperCheck();
}
