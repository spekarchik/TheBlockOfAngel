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

        boolean isFlyingHelmet = entity.getItemBySlot(EquipmentSlot.HEAD).getItem().getRegistryName()
                .equals(ArmorRegistry.SUPER_HELMET.get().getRegistryName());
        boolean isFlyingLeggings = entity.getItemBySlot(EquipmentSlot.LEGS).getItem().getRegistryName()
                .equals(ArmorRegistry.SUPER_LEGGINGS.get().getRegistryName());
        boolean isFlyingBoots = entity.getItemBySlot(EquipmentSlot.FEET).getItem().getRegistryName()
                .equals(ArmorRegistry.SUPER_BOOTS.get().getRegistryName());
        boolean isFlyingChestplate = chestplate.getRegistryName()
                .equals(ArmorRegistry.SUPER_CHESTPLATE_FLYING.get().getRegistryName());
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
