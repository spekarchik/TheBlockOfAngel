package com.pekar.angelblock.armor;

public class LimoniteArmorWithRegenerator extends LimoniteArmor
{
    protected LimoniteArmorWithRegenerator(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean isModifiedWithHealthRegenerator()
    {
        return true;
    }
}
