package com.pekar.angelblock.blocks;

import com.pekar.angelblock.tab.ModTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class ModBlockItem extends BlockItem
{
    public ModBlockItem(Block block, boolean addToTab)
    {
        super(block, addToTab ? new Properties().tab(ModTab.MOD_TAB) : new Properties());
    }
}
