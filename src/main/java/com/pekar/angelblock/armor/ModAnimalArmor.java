package com.pekar.angelblock.armor;

import net.minecraft.world.item.equipment.ArmorType;

public abstract class ModAnimalArmor extends ModArmor
{
    public ModAnimalArmor(ModArmorMaterial material, Properties properties)
    {
        super(material, ArmorType.BODY, properties);
    }
}
