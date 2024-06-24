package com.pekar.angelblock.armor;

public class LimoniteArmorWithDetector extends LimoniteArmor
{
    protected LimoniteArmorWithDetector(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithDetector()
    {
        return true;
    }
}
