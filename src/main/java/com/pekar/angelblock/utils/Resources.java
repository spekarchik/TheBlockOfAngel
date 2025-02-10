package com.pekar.angelblock.utils;

import net.minecraft.resources.ResourceLocation;

public class Resources
{
    Resources()
    {

    }

    public ResourceLocation createResourceLocation(String namespace, String name)
    {
        return ResourceLocation.fromNamespaceAndPath(namespace, name); //was: new ResourceLocation(name),
    }
}
