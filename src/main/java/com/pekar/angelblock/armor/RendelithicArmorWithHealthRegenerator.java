package com.pekar.angelblock.armor;

public class RendelithicArmorWithHealthRegenerator extends RendelithicArmor
{
    protected RendelithicArmorWithHealthRegenerator(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
