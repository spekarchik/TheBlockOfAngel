package com.pekar.materialext.tab;

import com.pekar.materialext.MaterialExtensionMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MaterialExtTab extends CreativeModeTab
{
    public static CreativeModeTab MATERIAL_EXT_TAB = new MaterialExtTab(MaterialExtensionMod.MODID);

    public MaterialExtTab(String tabName)
    {
        super(tabName);
    }

    @Override
    public ItemStack makeIcon()
    {
        return new ItemStack(Items.STRING);
    }
}
