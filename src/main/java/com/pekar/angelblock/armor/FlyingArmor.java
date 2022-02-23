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
        if (slot != EquipmentSlot.CHEST) return false;
        if (!Utils.isOverworld(entity.level.dimension()) || entity.isInWaterRainOrBubble()) return false;

        var chestplate = stack.getItem();
        var mainHandItem = entity.getItemInHand(InteractionHand.MAIN_HAND).getItem();

        boolean isFlyingHelmet = entity.getItemBySlot(EquipmentSlot.HEAD).getItem().getRegistryName()
                .equals(ArmorRegistry.FLYING_HELMET.get().getRegistryName());
        boolean isFlyingLeggings = entity.getItemBySlot(EquipmentSlot.LEGS).getItem().getRegistryName()
                .equals(ArmorRegistry.FLYING_LEGGINGS.get().getRegistryName());
        boolean isFlyingBoots = entity.getItemBySlot(EquipmentSlot.FEET).getItem().getRegistryName()
                .equals(ArmorRegistry.FLYING_BOOTS.get().getRegistryName());
        boolean isFlyingChestplate = chestplate.getRegistryName()
                .equals(ArmorRegistry.FLYING_CHESTPLATE.get().getRegistryName());
        boolean isFullArmorSet = isFlyingBoots && isFlyingChestplate && isFlyingHelmet && isFlyingLeggings;

        int maxDamageToFly = getMaxDamage(stack) / 2;
        int chestDamage = getDamage(stack);

        return isFullArmorSet && chestDamage < maxDamageToFly
                && !mainHandItem.getRegistryName().equals(Items.FIREWORK_ROCKET.getRegistryName());
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        return canElytraFly(stack, entity);
    }
}
