package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class RendelithicArmor extends ModHumanoidArmor
{
    protected RendelithicArmor(ModArmorMaterial material, Type equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, PlayerArmorType.RENDELITE, properties);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.LEGS);
        var leggingsItem = itemStack.getItem();
        if (!(leggingsItem instanceof ModHumanoidArmor leggings)) return false;
        return leggings.getArmorType() == PlayerArmorType.RENDELITE;
    }
}
