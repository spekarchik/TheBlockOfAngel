package com.pekar.angelblock.armor;

public class DiamithicArmorWithDetector extends ModArmor
{
    protected DiamithicArmorWithDetector(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
