package com.pekar.angelblock.armor;

import com.pekar.angelblock.Utils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FlyingArmor extends ModArmor
{
    protected FlyingArmor(ArmorMaterial material, Type equipmentSlot, String armorItemName)
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
        if (type != Type.CHESTPLATE) return false;
        if (Utils.isNether(entity.level().dimension()) || entity.isInWaterRainOrBubble()) return false;

        var mainHandItemStack = entity.getItemInHand(InteractionHand.MAIN_HAND);
        var mainHandItem = mainHandItemStack.getItem();

        boolean isFlyingHelmet = getModelName(entity, EquipmentSlot.HEAD)
                .equals(ArmorRegistry.FLYING_HELMET.get().getArmorModelName());
        boolean isFlyingLeggings = getModelName(entity, EquipmentSlot.LEGS)
                .equals(ArmorRegistry.FLYING_LEGGINGS.get().getArmorModelName());
        boolean isFlyingBoots = getModelName(entity, EquipmentSlot.FEET)
                .equals(ArmorRegistry.FLYING_BOOTS.get().getArmorModelName());
        boolean isFlyingChestplate = getModelName(entity, EquipmentSlot.CHEST)
                .equals(ArmorRegistry.FLYING_CHESTPLATE.get().getArmorModelName());
        boolean isFullArmorSet = isFlyingBoots && isFlyingChestplate && isFlyingHelmet && isFlyingLeggings;

        int maxDamageToFly = getMaxDamage(stack) / 2;
        int chestDamage = getDamage(stack);

        return isFullArmorSet && chestDamage < maxDamageToFly
                && !mainHandItem.getName(mainHandItemStack).equals(Items.FIREWORK_ROCKET.getName(mainHandItemStack));
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
