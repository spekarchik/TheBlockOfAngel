package com.pekar.angelblock.armor;

public class DiamithicArmorWithStrengthBoost extends ModArmor
{
    protected DiamithicArmorWithStrengthBoost(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithStrengthBooster()
    {
        return true;
    }
}
