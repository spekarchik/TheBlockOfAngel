package com.pekar.angelblock.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jetbrains.annotations.NotNull;

public class SlowFallingPotionIngredient extends FilteringPotionIngredient
{
    public static final MapCodec<SlowFallingPotionIngredient> SLOW_FALLING_POTION_INGREDIENT_MAP_CODEC = new SlowFallingPotionIngredientCodec();
    private static final IngredientType<SlowFallingPotionIngredient> SLOW_FALLING_POTION_INGREDIENT_TYPE = new IngredientType<>(SLOW_FALLING_POTION_INGREDIENT_MAP_CODEC);

    protected SlowFallingPotionIngredient()
    {
        super(Items.POTION, Potions.SLOW_FALLING);
    }

    @Override
    public @NotNull IngredientType<?> getType()
    {
        return SLOW_FALLING_POTION_INGREDIENT_TYPE;
    }

    @Override
    public Ingredient toVanilla()
    {
        return super.toVanilla();
    }
}
