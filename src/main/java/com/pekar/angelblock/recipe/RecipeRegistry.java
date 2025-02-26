package com.pekar.angelblock.recipe;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.brewing.BrewingRecipe;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class RecipeRegistry
{
    public static void initStatic()
    {

    }

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event)
    {
        registerBlockBreakerBrewing(event.getBuilder(), event.getRegistryAccess());
    }

    private static void registerBlockBreakerBrewing(PotionBrewing.Builder builder, RegistryAccess registryAccess)
    {
        var input = new ItemStack(Items.SPLASH_POTION);
        var output = new ItemStack(ItemRegistry.BLOCK_BREAKER_POTION_ITEM.get());
        input.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.AWKWARD)); // see PotionItem.getDefaultInstance()
//        output.set(DataComponents.POTION_CONTENTS, new PotionContents(PotionRegistry.BLOCK_BREAKER_POTION));
        var recipe = new BrewingRecipe(Ingredient.of(input), Ingredient.of(Items.PRISMARINE_SHARD), output);

        builder.addRecipe(recipe);
    }
}
