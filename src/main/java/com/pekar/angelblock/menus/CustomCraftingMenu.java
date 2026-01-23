package com.pekar.angelblock.menus;

import com.pekar.angelblock.tools.ToolRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CustomCraftingMenu extends CraftingMenu
{
    public CustomCraftingMenu(int containerId, Inventory playerInventory)
    {
        super(containerId, playerInventory);
    }

    public CustomCraftingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access)
    {
        super(containerId, playerInventory, access);
    }

    @Override
    public void slotsChanged(Container inventory)
    {
        super.slotsChanged(inventory);

        var resultSlot = getSlot(getResultSlotIndex());
        var result = resultSlot.getItem();

        if (!result.isEmpty() && result.is(ToolRegistry.BUILDER.get()))
        {
            if (hasDamagedRod(ToolRegistry.AMETHYST_ROD.get()))
            {
                resultSlot.set(ItemStack.EMPTY);
            }
        }
        else if (!result.isEmpty() && result.is(ToolRegistry.TRACK_LAYER.get()))
        {
            if (hasDamagedRod(ToolRegistry.MARINE_ROD.get()))
            {
                resultSlot.set(ItemStack.EMPTY);
            }
        }
        else if (!result.isEmpty() && result.is(ToolRegistry.PLANTER.get()))
        {
            if (hasDamagedRod(ToolRegistry.ANCIENT_ROD.get()))
            {
                resultSlot.set(ItemStack.EMPTY);
            }
        }
    }

    private boolean hasDamagedRod(Item rodItem)
    {
        for (int i = 0; i < slots.size(); i++)
        {
            var slot = slots.get(i);
            var stack = slot.getItem();
            if (stack.is(rodItem) && stack.isDamaged())
            {
                return true;
            }
        }
        return false;
    }
}
