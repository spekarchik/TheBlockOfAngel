package com.pekar.angelblock.mixins;

import com.pekar.angelblock.tools.ToolRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CraftingMenu.class)
public abstract class CraftingMenuMixin
{
    // Adjust the assembled result before vanilla stores and synchronizes it, including
    // recipe-book placement. This helper also serves the player's 2x2 crafting grid.
    @ModifyVariable(method = "slotChangedCraftingGrid", at = @At(value = "LOAD", ordinal = 0), ordinal = 0)
    private static ItemStack angelblock$adjustCraftingResult(ItemStack result, AbstractContainerMenu menu,
            Level level, Player player, CraftingContainer container, ResultContainer resultSlots,
            RecipeHolder<CraftingRecipe> recipeHint)
    {
        if (!(menu instanceof CraftingMenu) || result.isEmpty()) return result;

        Item requiredUndamagedRod = null;
        if (result.is(ToolRegistry.BUILDER.get()))
            requiredUndamagedRod = ToolRegistry.AMETHYST_ROD.get();
        else if (result.is(ToolRegistry.TRACK_LAYER.get()))
            requiredUndamagedRod = ToolRegistry.MARINE_ROD.get();
        else if (result.is(ToolRegistry.PLANTER.get()))
            requiredUndamagedRod = ToolRegistry.ANCIENT_ROD.get();

        if (requiredUndamagedRod != null && !angelblock$getDamagedRod(container, requiredUndamagedRod).isEmpty())
            return ItemStack.EMPTY;

        if (result.is(ToolRegistry.ANGEL_ROD))
        {
            ItemStack damagedRod = angelblock$getDamagedRod(container, ToolRegistry.END_MAGNETIC_ROD.get());
            if (!damagedRod.isEmpty()) result.setDamageValue(damagedRod.getDamageValue());
        }

        return result;
    }

    @Unique
    private static ItemStack angelblock$getDamagedRod(CraftingContainer container, Item rodItem)
    {
        for (int i = 0; i < container.getContainerSize(); i++)
        {
            ItemStack stack = container.getItem(i);
            if (stack.is(rodItem) && stack.isDamaged()) return stack;
        }
        return ItemStack.EMPTY;
    }
}
