package com.pekar.angelblock.events.armor;

import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public interface IArmorEvents
{
    void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event);
    void onLivingHurtEvent(LivingIncomingDamageEvent event);
    void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event);
    void onLivingJumpEvent(LivingEvent.LivingJumpEvent event);
    void onLivingFallEvent(LivingFallEvent event);
    void onKeyInputEvent(String pressedKeyDescription);
    void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event);
    void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event);
    void onBreakSpeed(PlayerEvent.BreakSpeed event);
    void onBeingInWater();
    void onBeingUnderRain();
    void onCreeperCheck();
}
