package com.pekar.angelblock.recipe;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class RecipeRegistry
{
    public static void init()
    {
        registerBrewing();
    }

    private static void registerBrewing()
    {
        ItemStack inputPotion = PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD);
        ItemStack outputPotion = PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.BLOCK_BREAKER_POTION.get());
        BrewingRecipeRegistry.addRecipe(Ingredient.of(inputPotion), Ingredient.of(new ItemStack(Items.PRISMARINE_SHARD)), outputPotion);
    }
}
