package com.pekar.angelblock.recipe;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.brewing.BrewingRecipe;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import static com.pekar.angelblock.utils.Resources.createResourceLocation;

@EventBusSubscriber(modid = Main.MODID)
public class RecipeRegistry
{
    private static final Ingredient AWKWARD_SPLASH_POTION = new Ingredient(new AwkwardSplashPotionIngredient());
    private static final Ingredient SLOW_FALLING_POTION = new Ingredient(new SlowFallingPotionIngredient());

    public static void initStatic()
    {
    }

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event)
    {
        var builder = event.getBuilder();
        registerBlockBreakerBrewing(builder);
        registerSoaringSporeEssence(builder);
    }

    private static void registerBlockBreakerBrewing(PotionBrewing.Builder builder)
    {
        //var input = new ItemStack(Items.SPLASH_POTION);
        var output = new ItemStack(ItemRegistry.BLOCK_BREAKER_POTION_ITEM.get());
        //input.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.AWKWARD)); // see PotionItem.getDefaultInstance()
//        output.set(DataComponents.POTION_CONTENTS, new PotionContents(PotionRegistry.BLOCK_BREAKER_POTION));
        var recipe = new BrewingRecipe(AWKWARD_SPLASH_POTION, Ingredient.of(Items.PRISMARINE_SHARD), output);

        builder.addRecipe(recipe);
    }

    private static void registerSoaringSporeEssence(PotionBrewing.Builder builder)
    {
        //var input = new ItemStack(Items.POTION);
        //input.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.SLOW_FALLING));
        var output = new ItemStack(ItemRegistry.SOARING_SPORE_ESSENCE.get());
        TagKey<Item> froglightsTag = TagKey.create(Registries.ITEM, createResourceLocation("minecraft", "froglights"));
        var recipe = new BrewingRecipe(SLOW_FALLING_POTION, Ingredient.of(froglightsTag), output);

        builder.addRecipe(recipe);
    }
}
