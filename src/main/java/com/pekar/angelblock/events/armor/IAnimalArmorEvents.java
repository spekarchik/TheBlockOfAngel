package com.pekar.angelblock.events.armor;

import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;

public interface IAnimalArmorEvents
{
    void onArmorHurtEvent(ArmorHurtEvent event);
    void onLivingHurtEvent(LivingIncomingDamageEvent event);
    void onLivingDamageEvent(LivingDamageEvent.Pre event);
    void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event);
    void onLivingJumpEvent(LivingEvent.LivingJumpEvent event);
    void onLivingFallEvent(LivingFallEvent event);
    void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event);
    void onBeingInLava();
    void onBeingInWater();
    void onBeingUnderRain();
    void onBeingInNormalEnvironment();
    void onEffectAddedEvent(MobEffectEvent.Added event);
}
