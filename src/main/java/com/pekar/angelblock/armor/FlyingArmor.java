package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.ArmorType;

public class FlyingArmor extends ModHumanoidArmor
{
    protected FlyingArmor(ModArmorMaterial material, ArmorType equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, properties);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        return getModelName(wearer, EquipmentSlot.FEET)
            .equals(ArmorRegistry.FLYING_BOOTS.get().getArmorFamilyName());
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment)
    {
        return false;
    }

    private String getModelName(LivingEntity entity, EquipmentSlot slot)
    {
        var item = entity.getItemBySlot(slot).getItem();
        if (!(item instanceof ModHumanoidArmor armorItem)) return "";
        return armorItem.getArmorFamilyName();
    }
}
