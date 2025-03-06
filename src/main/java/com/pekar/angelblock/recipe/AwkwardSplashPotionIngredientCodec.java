package com.pekar.angelblock.recipe;

import com.mojang.serialization.*;

import java.util.stream.Stream;

public class AwkwardSplashPotionIngredientCodec extends MapCodec<AwkwardSplashPotionIngredient>
{
    @Override
    public String toString()
    {
        return "AwkwardSplashPotionIngredientCodec";
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> ops)
    {
        return Stream.empty();
    }

    @Override
    public <T> DataResult<AwkwardSplashPotionIngredient> decode(DynamicOps<T> ops, MapLike<T> input)
    {
        return DataResult.success(new AwkwardSplashPotionIngredient());
    }

    @Override
    public <T> RecordBuilder<T> encode(AwkwardSplashPotionIngredient input, DynamicOps<T> ops, RecordBuilder<T> prefix)
    {
        return prefix;
    }
}