package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public class LimoniteArmorWithDetector extends ModArmor
{
    protected LimoniteArmorWithDetector(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, armorModelName);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
