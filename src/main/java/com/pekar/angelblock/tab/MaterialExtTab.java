package com.pekar.angelblock.tab;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MaterialExtTab extends CreativeModeTab
{
    public static CreativeModeTab MATERIAL_EXT_TAB = new MaterialExtTab(Main.MODID);

    public MaterialExtTab(String tabName)
    {
        super(tabName);
    }

    @Override
    public ItemStack makeIcon()
    {
        return new ItemStack(ItemRegistry.RENDELITHIC_POWDER.get());
    }
}
