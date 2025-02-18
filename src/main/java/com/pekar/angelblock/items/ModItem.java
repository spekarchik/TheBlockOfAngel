package com.pekar.angelblock.items;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.item.Item;

public class ModItem extends Item
{
    protected final Utils utils = new Utils();

    public ModItem()
    {
        this(new Properties());
    }

    public ModItem(Properties properties)
    {
        super(properties);
    }
}
