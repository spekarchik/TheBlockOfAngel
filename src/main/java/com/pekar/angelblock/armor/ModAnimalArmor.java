package com.pekar.angelblock.armor;

import net.minecraft.world.item.equipment.ArmorType;

public abstract class ModAnimalArmor extends ModArmor
{
    protected final AnimalArmorType armorType;

    public ModAnimalArmor(ModArmorMaterial material, AnimalArmorType armorType, Properties properties)
    {
        super(material, ArmorType.BODY, properties.stacksTo(1));
        this.armorType = armorType;
    }

    public AnimalArmorType getArmorType()
    {
        return armorType;
    }
}
