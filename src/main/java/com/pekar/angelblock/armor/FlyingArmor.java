package com.pekar.angelblock.armor;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FlyingArmor extends ModHumanoidArmor
{
    protected FlyingArmor(ModArmorMaterial material, Type equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, PlayerArmorType.AERYTE, properties);
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

        boolean isFlyingHelmet = entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ModHumanoidArmor modArmor
                && modArmor.getArmorType() == PlayerArmorType.AERYTE;
        boolean isFlyingLeggings = entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ModHumanoidArmor modArmor
                && modArmor.getArmorType() == PlayerArmorType.AERYTE;
        boolean isFlyingBoots = entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ModHumanoidArmor modArmor
                && modArmor.getArmorType() == PlayerArmorType.AERYTE;
        boolean isFlyingChestplate = entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ModHumanoidArmor modArmor
                && modArmor.getArmorType() == PlayerArmorType.AERYTE;

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
        if (isPlayerHeavy(wearer)) return false;

        return wearer.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ModHumanoidArmor modArmor
                && modArmor.getArmorType() == PlayerArmorType.AERYTE;
    }
}
