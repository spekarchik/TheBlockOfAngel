package com.pekar.angelblock.recipe;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.brewing.BrewingRecipe;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class RecipeRegistry
{
    public static void init()
    {
        registerBrewing();
    }

    private static void registerBrewing()
    {
        var builder = new PotionBrewing.Builder(FeatureFlags.DEFAULT_FLAGS);

        var input = new ItemStack(Items.SPLASH_POTION);
        var output = new ItemStack(Items.SPLASH_POTION);
        input.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.AWKWARD)); // see PotionItem.getDefaultInstance()
        output.set(DataComponents.POTION_CONTENTS, new PotionContents(PotionRegistry.BLOCK_BREAKER_POTION));
        var recipe = new BrewingRecipe(Ingredient.of(input), Ingredient.of(Items.PRISMARINE_SHARD), output);

        builder.addRecipe(recipe);
        EVENT_BUS.post(new RegisterBrewingRecipesEvent(builder, RegistryAccess.EMPTY)); // TODO: Check the recipe
        var potionBrewing = builder.build();

//        ItemStack inputPotion = PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD);
//        ItemStack outputPotion = PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), PotionRegistry.BLOCK_BREAKER_POTION);
//        BrewingRecipeRegistry.addRecipe(Ingredient.of(inputPotion), Ingredient.of(new ItemStack(Items.PRISMARINE_SHARD)), outputPotion);
    }
}
