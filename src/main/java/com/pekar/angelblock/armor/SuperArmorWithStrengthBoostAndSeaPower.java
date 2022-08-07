package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public class SuperArmorWithStrengthBoostAndSeaPower extends SuperArmorWithStrengthBoost
{
    protected SuperArmorWithStrengthBoostAndSeaPower(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean isModifiedWithSeaPower()
    {
        return true;
    }
}
