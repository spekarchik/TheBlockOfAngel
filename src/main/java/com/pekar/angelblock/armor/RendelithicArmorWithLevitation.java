package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public class RendelithicArmorWithLevitation extends RendelithicArmor
{
    protected RendelithicArmorWithLevitation(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean isModifiedWithLevitation()
    {
        return true;
    }
}
