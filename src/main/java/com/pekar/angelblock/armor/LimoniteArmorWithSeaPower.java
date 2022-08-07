package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public class LimoniteArmorWithSeaPower extends ModArmor
{
    protected LimoniteArmorWithSeaPower(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
