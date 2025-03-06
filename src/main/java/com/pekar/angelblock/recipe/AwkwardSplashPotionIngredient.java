package com.pekar.angelblock.recipe;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

public class AwkwardSplashPotionIngredient extends FilteringPotionIngredient
{
    public AwkwardSplashPotionIngredient()
    {
        super(Items.SPLASH_POTION, Potions.AWKWARD);
    }

    @Override
    public IngredientType<?> getType()
    {
        return new IngredientType<>(new AwkwardSplashPotionIngredientCodec());
    }

    @Override
    public Ingredient toVanilla()
    {
        return super.toVanilla();
    }
}
