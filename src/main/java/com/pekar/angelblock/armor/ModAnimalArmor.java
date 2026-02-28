package com.pekar.angelblock.armor;

import net.minecraft.world.item.equipment.ArmorType;

public abstract class ModAnimalArmor extends ModArmor
{
    protected final AnimalArmorType armorType;

    public ModAnimalArmor(ModArmorMaterial material, ArmorType armorSlotType, AnimalArmorType armorType, Properties properties)
    {
        super(material, armorSlotType, properties);
        this.armorType = armorType;
    }

    public AnimalArmorType getArmorType()
    {
        return armorType;
    }
}
