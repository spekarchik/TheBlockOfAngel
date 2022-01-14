package com.pekar.angelblock.items;

import com.pekar.angelblock.tab.MaterialExtTab;
import net.minecraft.world.item.Item;

public class ModItem extends Item
{
    public ModItem()
    {
        super(new Properties().tab(MaterialExtTab.MATERIAL_EXT_TAB));
    }
}
