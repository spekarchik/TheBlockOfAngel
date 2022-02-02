package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class FlyingArmor extends ModArmor
{
    protected FlyingArmor(ArmorMaterial material, EquipmentSlot equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_)
    {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }
}
