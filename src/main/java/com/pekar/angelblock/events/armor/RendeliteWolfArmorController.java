package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.AnimalArmorType;
import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.armor.ModArmor;
import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;

public class RendeliteWolfArmorController extends AnimalArmor
{
    private static final int SLOWNESS_NEGATIVE_EFFECT_AMPLIFIER = 3;
    private static final int SLOWNESS_NEGATIVE_EFFECT_DURATION = 600;
    private static final int GLOWING_EFFECT_DURATION = 6000;

    public RendeliteWolfArmorController(IAnimal animal)
    {
        super(animal, AnimalArmorType.RENDELITE_WOLF);
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        var damageSource = event.getSource();
        var entity = event.getEntity();
        var armor = animal.getAnimalEntity().getBodyArmorItem();
        if (!(armor.getItem() instanceof ModArmor modArmor)) return;

        if (armor.is(ArmorRegistry.WOLF_RENDELITE_ARMOR))
        {
            if (modArmor.isBroken(armor))
            {
                if (!entity.hasEffect(MobEffects.GLOWING))
                    entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, GLOWING_EFFECT_DURATION, 0, false, true));
            }
            else
            {
                if (isFireOrLavaOrHotFloorDamage(damageSource))
                {
                    event.setCanceled(true);
                }
                else if (damageSource.is(DamageTypes.WITHER))
                {
                    event.setCanceled(true);
                    animal.getEntity().removeEffect(MobEffects.WITHER);
                }
                else if (!damageSource.is(DamageTypeTags.BYPASSES_WOLF_ARMOR))
                {
                    var damageAmount = Mth.ceil(event.getAmount());
                    var armorDamageValue = armor.getDamageValue();
                    var armorMaxDamage = armor.getMaxDamage();
                    var durabilityLeft = armorMaxDamage - armorDamageValue - 1;
                    var durabilityLoss = Math.min(damageAmount, durabilityLeft);
                    armor.hurtAndBreak(durabilityLoss, animal.getEntity(), EquipmentSlot.BODY);

                    event.setAmount(0F);
                }
            }
        }
    }

    @Override
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {

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
        addSlownessEffect();
    }

    @Override
    public void onBeingUnderRain()
    {
        addSlownessEffect();
    }

    @Override
    public void onBeingInNormalEnvironment()
    {
        var entity = animal.getEntity();
        if (entity.isOnFire())
            entity.clearFire();
    }

    @Override
    public void onEffectAddedEvent(MobEffectEvent.Added event)
    {
        if (event.getEffectInstance().getEffect() == MobEffects.WITHER)
        {
            animal.getEntity().removeEffect(MobEffects.WITHER);
        }
    }

    private void addSlownessEffect()
    {
        if (animal.getArmor() == null) return;
        if (animal.getEntity().hasEffect(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT)) return;

        animal.getEntity().addEffect(new MobEffectInstance(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT, SLOWNESS_NEGATIVE_EFFECT_DURATION, SLOWNESS_NEGATIVE_EFFECT_AMPLIFIER, false, true));
    }
}
