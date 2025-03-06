package com.pekar.angelblock.recipe;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

public class SlowFallingPotionIngredient extends FilteringPotionIngredient
{
    protected SlowFallingPotionIngredient()
    {
        super(Items.POTION, Potions.SLOW_FALLING);
    }

    @Override
    public IngredientType<?> getType()
    {
        return new IngredientType<>(new SlowFallingPotionIngredientCodec());
    }

    @Override
    public Ingredient toVanilla()
    {
        return super.toVanilla();
    }
}
