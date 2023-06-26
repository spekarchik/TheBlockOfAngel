package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class LimoniteArmor extends ModArmor
{
    protected LimoniteArmor(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.FEET);
        var bootsItem = itemStack.getItem();
        if (!(bootsItem instanceof ModArmor boots)) return false;
        return boots.getArmorModelName().equals(ArmorRegistry.LIMONITE_BOOTS.get().getArmorModelName());
    }
}
