package com.pekar.angelblock.utils;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class Resources
{
    Resources()
    {

    }

    public Identifier createResourceLocation(String namespace, String name)
    {
        return Identifier.fromNamespaceAndPath(namespace, name); //was: new ResourceLocation(name),
    }

    public ResourceKey<EquipmentAsset> createEquipmentResourceKey(String namespace, String armorName)
    {
        return ResourceKey.create(
                EquipmentAssets.ROOT_ID,
                Identifier.fromNamespaceAndPath(namespace, armorName));
    }

}
