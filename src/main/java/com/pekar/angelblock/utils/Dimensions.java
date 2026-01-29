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
        return dimension.identifier().equals(Level.OVERWORLD.identifier());
    }

    public boolean isNether(ResourceKey<Level> dimension)
    {
        return dimension.identifier().equals(Level.NETHER.identifier());
    }

    public boolean isEnd(ResourceKey<Level> dimension)
    {
        return dimension.identifier().equals(Level.END.identifier());
    }
}
