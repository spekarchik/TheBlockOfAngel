package com.pekar.angelblock.tab;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

public class MainTab extends ModTab
{
    @Override
    protected String getTabName()
    {
        return "angel_block";
    }

    @Override
    protected RegistryObject<Item> getIconItem()
    {
        var iconItem = Main.ITEMS.getEntries().stream().filter(i -> i.getId().getPath().equals(BlockRegistry.ANGEL_BLOCK.getId().getPath())).findFirst();
        return iconItem.orElse(null);
    }

    @Override
    protected Collection<RegistryObject<Item>> getTabItems()
    {
        ItemRegistry.initStatic();
        BlockRegistry.initStatic();
        return Main.ITEMS.getEntries(); // block items are also included
    }

    @Override
    protected ResourceKey<CreativeModeTab>[] getTabsBefore()
    {
        return new ResourceKey[] { CreativeModeTabs.COMBAT };
    }
}
