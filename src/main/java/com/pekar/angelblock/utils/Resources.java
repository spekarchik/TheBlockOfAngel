package com.pekar.angelblock.utils;

import com.pekar.angelblock.Main;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAsset;

public class Resources
{
    Resources()
    {

    }

    public ResourceLocation createResourceLocation(String namespace, String name)
    {
        return ResourceLocation.fromNamespaceAndPath(namespace, name); //was: new ResourceLocation(name),
    }

    public ResourceKey<EquipmentAsset> createEquipmentResourceKey(String namespace, String armorName)
    {
        return ResourceKey.create(
                ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Main.VANILLAID, "equipment_asset")),
                ResourceLocation.fromNamespaceAndPath(namespace, armorName));
    }

}
