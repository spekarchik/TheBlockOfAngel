package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class SuperArmorFlying extends SuperArmor
{
    protected SuperArmorFlying(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity)
    {
        if (slot != EquipmentSlot.CHEST) return false;

        boolean isFlyingHelmet = entity.getItemBySlot(EquipmentSlot.HEAD).getItem().getDescriptionId()
                .equals(ArmorRegistry.SUPER_HELMET.get().getDescriptionId());
        boolean isFlyingLeggings = entity.getItemBySlot(EquipmentSlot.LEGS).getItem().getDescriptionId()
                .equals(ArmorRegistry.SUPER_LEGGINGS.get().getDescriptionId());
        boolean isFlyingBoots = entity.getItemBySlot(EquipmentSlot.FEET).getItem().getDescriptionId()
                .equals(ArmorRegistry.SUPER_BOOTS.get().getDescriptionId());
        boolean isFlyingChestplate = entity.getItemBySlot(EquipmentSlot.CHEST).getItem().getDescriptionId()
                .equals(ArmorRegistry.SUPER_CHESTPLATE_FLYING.get().getDescriptionId());
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
