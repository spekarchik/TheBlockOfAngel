package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class SuperArmorFlying extends SuperArmor
{
    protected SuperArmorFlying(ArmorMaterial material, EquipmentSlot equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity)
    {
        if (slot != EquipmentSlot.CHEST) return false;

        var chestplate = entity.getItemBySlot(EquipmentSlot.CHEST).getItem();

        boolean isFlyingHelmet = entity.getItemBySlot(EquipmentSlot.HEAD).getItem().getName(null)
                .equals(ArmorRegistry.SUPER_HELMET.get().getName(null));
        boolean isFlyingLeggings = entity.getItemBySlot(EquipmentSlot.LEGS).getItem().getName(null)
                .equals(ArmorRegistry.SUPER_LEGGINGS.get().getName(null));
        boolean isFlyingBoots = entity.getItemBySlot(EquipmentSlot.FEET).getItem().getName(null)
                .equals(ArmorRegistry.SUPER_BOOTS.get().getName(null));
        boolean isFlyingChestplate = chestplate.getName(null)
                .equals(ArmorRegistry.SUPER_CHESTPLATE_FLYING.get().getName(null));
        boolean isFullArmorSet = isFlyingBoots && isFlyingChestplate && isFlyingHelmet && isFlyingLeggings;

        int maxDamageToFly = getMaxDamage(stack) / 2;
        int chestDamage = getDamage(stack);

        return isFullArmorSet && chestDamage < maxDamageToFly;
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        return canElytraFly(stack, entity);
    }
}
