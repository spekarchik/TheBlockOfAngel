package com.pekar.angelblock.armor;

public class DiamithicArmorWithHealthRegenerator extends ModArmor
{
    protected DiamithicArmorWithHealthRegenerator(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
