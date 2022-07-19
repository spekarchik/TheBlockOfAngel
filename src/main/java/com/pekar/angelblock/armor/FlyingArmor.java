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
    protected FlyingArmor(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
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

        boolean isFlyingHelmet = entity.getItemBySlot(EquipmentSlot.HEAD).getItem().getName(null)
                .equals(ArmorRegistry.FLYING_HELMET.get().getName(null));
        boolean isFlyingLeggings = entity.getItemBySlot(EquipmentSlot.LEGS).getItem().getName(null)
                .equals(ArmorRegistry.FLYING_LEGGINGS.get().getName(null));
        boolean isFlyingBoots = entity.getItemBySlot(EquipmentSlot.FEET).getItem().getName(null)
                .equals(ArmorRegistry.FLYING_BOOTS.get().getName(null));
        boolean isFlyingChestplate = chestplate.getName(null)
                .equals(ArmorRegistry.FLYING_CHESTPLATE.get().getName(null));
        boolean isFullArmorSet = isFlyingBoots && isFlyingChestplate && isFlyingHelmet && isFlyingLeggings;

        int maxDamageToFly = getMaxDamage(stack) / 2;
        int chestDamage = getDamage(stack);

        return isFullArmorSet && chestDamage < maxDamageToFly
                && !mainHandItem.getName(null).equals(Items.FIREWORK_ROCKET.getName(null));
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        return canElytraFly(stack, entity);
    }
}
