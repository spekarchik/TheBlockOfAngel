package com.pekar.angelblock.tab;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.ToolRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.stream.Collectors;

public class MainTab extends ModTab
{
    @Override
    protected String getTabName()
    {
        return "angelblock_tab";
    }

    @Override
    protected ItemStack getIconItem()
    {
        return BlockRegistry.ANGEL_BLOCK.asItem().getDefaultInstance();
    }

    @Override
    protected Collection<Item> getTabItems()
    {
        BlockRegistry.initStatic();
        ItemRegistry.initStatic();
        EntityRegistry.initStatic();
        ArmorRegistry.initStatic();
        ToolRegistry.initStatic();
        PotionRegistry.initStatic();

        return Main.ITEMS.getEntries().stream().map(x -> x.get()).collect(Collectors.toList()); // block items are also included
    }

    @Override
    protected ResourceKey<CreativeModeTab>[] getTabsBefore()
    {
        return new ResourceKey[]
                {
                        CreativeModeTabs.SPAWN_EGGS
                };
    }
}
