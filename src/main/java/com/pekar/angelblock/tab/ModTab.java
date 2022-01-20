package com.pekar.angelblock.tab;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTab extends CreativeModeTab
{
    public static CreativeModeTab MOD_TAB = new ModTab(Main.MODID);

    public ModTab(String tabName)
    {
        super(tabName);
    }

    @Override
    public ItemStack makeIcon()
    {
        return new ItemStack(ItemRegistry.RENDELITHIC_POWDER.get());
    }
}
