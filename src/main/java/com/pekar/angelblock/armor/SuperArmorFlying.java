package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SuperArmorFlying extends SuperArmor
{
    protected SuperArmorFlying(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity)
    {
        if (type != Type.CHESTPLATE) return false;

        boolean isFlyingHelmet = getModelName(entity, EquipmentSlot.HEAD)
                .equals(ArmorRegistry.SUPER_HELMET.get().getArmorModelName());
        boolean isFlyingLeggings = getModelName(entity, EquipmentSlot.LEGS)
                .equals(ArmorRegistry.SUPER_LEGGINGS.get().getArmorModelName());
        boolean isFlyingBoots = getModelName(entity, EquipmentSlot.FEET)
                .equals(ArmorRegistry.SUPER_BOOTS.get().getArmorModelName());
        boolean isFlyingChestplate = getModelName(entity, EquipmentSlot.CHEST)
                .equals(ArmorRegistry.SUPER_CHESTPLATE_FLYING.get().getArmorModelName());
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

    private String getModelName(LivingEntity entity, EquipmentSlot slot)
    {
        var item = entity.getItemBySlot(slot).getItem();
        if (!(item instanceof ModArmor armorItem)) return "";
        return armorItem.getArmorModelName();
    }
}
