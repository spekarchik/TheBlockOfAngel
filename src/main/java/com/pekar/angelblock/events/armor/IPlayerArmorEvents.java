package com.pekar.angelblock.events.armor;

import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public interface IPlayerArmorEvents
{
    void onArmorHurtEvent(ArmorHurtEvent event);
    void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event);
    void onLivingHurtEvent(LivingIncomingDamageEvent event);
    void onLivingDamageEvent(LivingDamageEvent.Pre event);
    void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event);
    void onLivingJumpEvent(LivingEvent.LivingJumpEvent event);
    void onLivingFallEvent(LivingFallEvent event);
    void onKeyInputEvent(String pressedKeyDescription);
    void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event);
    void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event);
    void onBreakSpeed(PlayerEvent.BreakSpeed event);
    void onBeingInLava();
    void onBeingInWater();
    void onBeingUnderRain();
    void onCreeperCheck();
    void onEffectAddedEvent(MobEffectEvent.Added event);
}
