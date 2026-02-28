package com.pekar.angelblock.armor;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SuperArmorFlying extends SuperArmor
{
    protected SuperArmorFlying(ModArmorMaterial material, Type equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, properties);
        withElytra();
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity)
    {
        if (type != Type.CHESTPLATE) return false;
        if (entity.hasEffect(MobEffects.SLOW_FALLING)) return false;

//        boolean isFlyingHelmet = getModelName(entity, EquipmentSlot.HEAD)
//                .equals(ArmorRegistry.SUPER_HELMET.get().getArmorFamilyName());
//        boolean isFlyingLeggings = getModelName(entity, EquipmentSlot.LEGS)
//                .equals(ArmorRegistry.SUPER_LEGGINGS.get().getArmorFamilyName());
//        boolean isFlyingBoots = getModelName(entity, EquipmentSlot.FEET)
//                .equals(ArmorRegistry.SUPER_BOOTS.get().getArmorFamilyName());
        boolean isFlyingChestplate = entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ModHumanoidArmor modArmor
                && modArmor.getArmorType() == PlayerArmorType.SUPERYTE;
//        boolean isFullArmorSet = isFlyingBoots && isFlyingChestplate && isFlyingHelmet && isFlyingLeggings;

        int maxDamageToFly = stack.getMaxDamage() / 2;
        int chestDamage = stack.getDamageValue();

        return isFlyingChestplate && chestDamage < maxDamageToFly;
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        return canElytraFly(stack, entity);
    }
}
