package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class RendelithicArmor extends ModArmor
{
    protected RendelithicArmor(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.LEGS);
        var leggingsItem = itemStack.getItem();
        if (!(leggingsItem instanceof ModArmor leggings)) return false;
        return leggings.getArmorFamilyName().equals(ArmorRegistry.RENDELITHIC_LEGGINGS.get().getArmorFamilyName());
    }
}
