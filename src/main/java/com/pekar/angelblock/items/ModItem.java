package com.pekar.angelblock.items;

import com.pekar.angelblock.tab.ModTab;
import net.minecraft.world.item.Item;

public class ModItem extends Item
{
    public ModItem()
    {
        super(new Properties().tab(ModTab.MOD_TAB));
    }
}
