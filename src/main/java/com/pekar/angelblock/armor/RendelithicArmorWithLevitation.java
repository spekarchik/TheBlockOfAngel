package com.pekar.angelblock.armor;

public class RendelithicArmorWithLevitation extends RendelithicArmor
{
    protected RendelithicArmorWithLevitation(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithLevitation()
    {
        return true;
    }
}
