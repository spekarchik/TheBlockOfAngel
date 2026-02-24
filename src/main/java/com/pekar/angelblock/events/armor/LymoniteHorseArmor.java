package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.armor.ModAnimalArmor;
import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.events.effect.HealthBoostAnimalPermanentArmorEffect;
import com.pekar.angelblock.events.effect.base.IPermanentArmorEffect;
import com.pekar.angelblock.events.mob.IMob;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;

public class LymoniteHorseArmor extends AnimalArmor
{
    private final IPermanentArmorEffect healthBoostEffect;

    public LymoniteHorseArmor(IAnimal animal)
    {
        super(animal);
        healthBoostEffect = new HealthBoostAnimalPermanentArmorEffect(animal, this, 1);
        healthBoostEffect.setupBasic().setupAvailability(this::isArmorPutOn);
    }

    private boolean isArmorPutOn(IMob mob, IArmor armor)
    {
        var entity = mob.getEntity();
        var slotItem = entity.getItemBySlot(EquipmentSlot.BODY);
        return !slotItem.isEmpty() && slotItem.getItem() instanceof ModAnimalArmor modArmor && modArmor.getArmorFamilyName().equals(armor.getFamilyName());
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        var damageSource = event.getSource();
        var entity = event.getEntity();
        var armor = animal.getAnimalEntity().getBodyArmorItem();
        if (!(armor.getItem() instanceof ModAnimalArmor modArmor)) return;

        if (armor.is(ArmorRegistry.HORSE_LYMONITE_ARMOR))
        {
            if (isFreezeDamage(damageSource))
            {
                event.setCanceled(true);
            }
            else if (isPlantThornsDamage(damageSource))
            {
                event.setCanceled(true);
            }

            if (entity.hasEffect(MobEffects.POISON))
            {
                entity.removeEffect(MobEffects.POISON);
            }
        }
    }

    @Override
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
        var damageSource = event.getSource();

        if (damageSource.is(DamageTypes.EXPLOSION) || damageSource.is(DamageTypes.WITHER))
        {
            event.setNewDamage(event.getNewDamage() * 1.5F);
        }
        else if (damageSource.is(DamageTypes.WIND_CHARGE))
        {
            event.setNewDamage(event.getNewDamage() * 3.0F);
        }
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        healthBoostEffect.updateAvailability();
        healthBoostEffect.updateActivity();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {

    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {

    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {

    }

    @Override
    public void onBeingInLava()
    {

    }

    @Override
    public void onBeingInWater()
    {
    }

    @Override
    public void onBeingUnderRain()
    {
        clearFreeze();
    }

    @Override
    public void onBeingInNormalEnvironment()
    {
        clearFreeze();
    }

    private void clearFreeze()
    {
        if (isArmorPutOn(animal, this) && animal.getEntity().isFreezing())
        {
            var entity = animal.getEntity();
            var ticksFrozen = entity.getTicksFrozen();
            entity.setTicksFrozen(Math.max(0, ticksFrozen - 4));
        }
    }

    @Override
    public void onEffectAddedEvent(MobEffectEvent.Added event)
    {
        if (event.getEffectInstance().getEffect() == MobEffects.POISON)
        {
            animal.getEntity().removeEffect(MobEffects.POISON);
        }
    }

    @Override
    public String getFamilyName()
    {
        return ArmorRegistry.LIMONITE_BOOTS.get().getArmorFamilyName();
    }
}
