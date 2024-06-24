package com.pekar.angelblock.armor;

public class DiamithicArmorWithLevitation extends ModArmor
{
    protected DiamithicArmorWithLevitation(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithLevitation()
    {
        return true;
    }
}
