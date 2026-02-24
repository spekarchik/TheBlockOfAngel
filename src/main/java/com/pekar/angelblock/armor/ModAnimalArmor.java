package com.pekar.angelblock.armor;

import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.equipment.ArmorType;

public abstract class ModAnimalArmor extends ModArmor
{
    public ModAnimalArmor(ModArmorMaterial material, EntityType entityType, Properties properties)
    {
        super(material, ArmorType.BODY, material.getMaterial().animalProperties(properties, HolderSet.direct(EntityType::builtInRegistryHolder, entityType) ));
    }
}
