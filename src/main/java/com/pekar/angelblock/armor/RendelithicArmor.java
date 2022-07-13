package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class RendelithicArmor extends ModArmor
{
    protected RendelithicArmor(ArmorMaterial material, EquipmentSlot equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        var leggings = wearer.getItemBySlot(EquipmentSlot.LEGS).getItem();
        return leggings.getName(null).equals(ArmorRegistry.RENDELITHIC_LEGGINGS.get().getName(null));
    }
}
