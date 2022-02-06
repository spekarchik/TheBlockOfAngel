package com.pekar.angelblock.armor;

import com.pekar.angelblock.tools.Utils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FlyingArmor extends ModArmor
{
    protected FlyingArmor(ArmorMaterial material, EquipmentSlot equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity)
    {
        if (!Utils.isOverworld(entity.level.dimension())) return false;

        var chestplate = entity.getItemBySlot(EquipmentSlot.CHEST).getItem();
        var mainHandItem = entity.getItemInHand(InteractionHand.MAIN_HAND).getItem();

        return chestplate.getRegistryName().equals(ArmorRegistry.FLYING_CHESTPLATE.get().getRegistryName())
                && !mainHandItem.getRegistryName().equals(Items.FIREWORK_ROCKET.getRegistryName());
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        if (!Utils.isOverworld(entity.level.dimension())) return false;

        var chestplate = entity.getItemBySlot(EquipmentSlot.CHEST).getItem();
        var mainHandItem = entity.getItemInHand(InteractionHand.MAIN_HAND).getItem();

        return chestplate.getRegistryName().equals(ArmorRegistry.FLYING_CHESTPLATE.get().getRegistryName())
                && !mainHandItem.getRegistryName().equals(Items.FIREWORK_ROCKET.getRegistryName());
    }
}
