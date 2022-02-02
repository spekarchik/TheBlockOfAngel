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
        var chestplate = entity.getItemBySlot(EquipmentSlot.CHEST).getItem();
        return chestplate.getRegistryName().equals(ArmorRegistry.SUPER_CHESTPLATE_FLYING.get().getRegistryName());
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        var chestplate = entity.getItemBySlot(EquipmentSlot.CHEST).getItem();
        return chestplate.getRegistryName().equals(ArmorRegistry.SUPER_CHESTPLATE_FLYING.get().getRegistryName());
    }
}
