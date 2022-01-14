package com.pekar.materialext.tab;

import com.pekar.materialext.Main;
import com.pekar.materialext.items.ItemRegistry;
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
