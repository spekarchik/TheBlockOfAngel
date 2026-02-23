package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;

public class RendelithicArmor extends ModHumanoidArmor
{
    protected RendelithicArmor(ModArmorMaterial material, ArmorType equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, properties);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.LEGS);
        var leggingsItem = itemStack.getItem();
        if (!(leggingsItem instanceof ModHumanoidArmor leggings)) return false;
        return leggings.getArmorFamilyName().equals(ArmorRegistry.RENDELITHIC_LEGGINGS.get().getArmorFamilyName());
    }
}
