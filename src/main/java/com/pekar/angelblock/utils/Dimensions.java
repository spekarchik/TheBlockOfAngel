package com.pekar.angelblock.utils;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class Dimensions
{
    Dimensions()
    {

    }

    public boolean isOverworld(ResourceKey<Level> dimension)
    {
        return dimension.location().equals(Level.OVERWORLD.location());
    }

    public boolean isNether(ResourceKey<Level> dimension)
    {
        return dimension.location().equals(Level.NETHER.location());
    }

    public boolean isEnd(ResourceKey<Level> dimension)
    {
        return dimension.location().equals(Level.END.location());
    }
}
