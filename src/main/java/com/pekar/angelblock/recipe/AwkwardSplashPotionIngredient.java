package com.pekar.angelblock.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jetbrains.annotations.NotNull;

public class AwkwardSplashPotionIngredient extends FilteringPotionIngredient
{
    public static final MapCodec<AwkwardSplashPotionIngredient> AWKWARD_SPLASH_POTION_INGREDIENT_MAP_CODEC = new AwkwardSplashPotionIngredientCodec();
    private static final IngredientType<AwkwardSplashPotionIngredient> AWKWARD_SPLASH_POTION_INGREDIENT_TYPE = new IngredientType<>(AWKWARD_SPLASH_POTION_INGREDIENT_MAP_CODEC);

    public AwkwardSplashPotionIngredient()
    {
        super(Items.SPLASH_POTION, Potions.AWKWARD);
    }

    @Override
    public @NotNull IngredientType<?> getType()
    {
        return AWKWARD_SPLASH_POTION_INGREDIENT_TYPE;
    }
}
