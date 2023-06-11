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
        BlockRegistry.initStatic();
        ItemRegistry.initStatic();
        EntityRegistry.initStatic();
        ArmorRegistry.initStatic();
        ToolRegistry.initStatic();
        PotionRegistry.initStatic();

        return Main.ITEMS.getEntries(); // block items are also included
    }

    @Override
    protected ResourceKey<CreativeModeTab>[] getTabsBefore()
    {
        return new ResourceKey[] { CreativeModeTabs.COMBAT };
    }
}
