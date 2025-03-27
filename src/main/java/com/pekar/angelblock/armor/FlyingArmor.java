package com.pekar.angelblock.armor;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FlyingArmor extends ModArmor
{
    protected FlyingArmor(ModArmorMaterial material, Type equipmentSlot)
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
        if (type != Type.CHESTPLATE) return false;
        if (entity.hasEffect(MobEffects.SLOW_FALLING)) return false;
        if (Utils.instance.dimension.isNether(entity.level().dimension()) || entity.isInWaterRainOrBubble()) return false;

        var mainHandItemStack = entity.getMainHandItem();
        if (mainHandItemStack.is(Items.FIREWORK_ROCKET)) return false;
        var offHandItemStack = entity.getOffhandItem();
        if (offHandItemStack.is(Items.FIREWORK_ROCKET)) return false;

        boolean isFlyingHelmet = getModelName(entity, EquipmentSlot.HEAD)
                .equals(ArmorRegistry.FLYING_HELMET.get().getArmorFamilyName());
        boolean isFlyingLeggings = getModelName(entity, EquipmentSlot.LEGS)
                .equals(ArmorRegistry.FLYING_LEGGINGS.get().getArmorFamilyName());
        boolean isFlyingBoots = getModelName(entity, EquipmentSlot.FEET)
                .equals(ArmorRegistry.FLYING_BOOTS.get().getArmorFamilyName());
        boolean isFlyingChestplate = getModelName(entity, EquipmentSlot.CHEST)
                .equals(ArmorRegistry.FLYING_CHESTPLATE.get().getArmorFamilyName());

        int maxDamageToFly = stack.getMaxDamage() / 2;
        int chestDamage = stack.getDamageValue();
        boolean isFullArmorSetPutOn = isFlyingBoots && isFlyingLeggings && isFlyingChestplate && isFlyingHelmet;

        return isFullArmorSetPutOn && chestDamage < maxDamageToFly;
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        return canElytraFly(stack, entity);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        return getModelName(wearer, EquipmentSlot.FEET)
            .equals(ArmorRegistry.FLYING_BOOTS.get().getArmorFamilyName());

    }

    private String getModelName(LivingEntity entity, EquipmentSlot slot)
    {
        var item = entity.getItemBySlot(slot).getItem();
        if (!(item instanceof ModArmor armorItem)) return "";
        return armorItem.getArmorFamilyName();
    }
}
