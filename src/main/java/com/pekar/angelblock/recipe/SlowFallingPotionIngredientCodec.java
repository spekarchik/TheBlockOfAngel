package com.pekar.angelblock.recipe;

import com.mojang.serialization.*;

import java.util.stream.Stream;

public class SlowFallingPotionIngredientCodec extends MapCodec<SlowFallingPotionIngredient>
{
    @Override
    public String toString()
    {
        return "SlowFallingPotionIngredientCodec";
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> ops)
    {
        return Stream.empty();
    }

    @Override
    public <T> DataResult<SlowFallingPotionIngredient> decode(DynamicOps<T> ops, MapLike<T> input)
    {
        return DataResult.success(new SlowFallingPotionIngredient());
    }

    @Override
    public <T> RecordBuilder<T> encode(SlowFallingPotionIngredient input, DynamicOps<T> ops, RecordBuilder<T> prefix)
    {
        return prefix;
    }
}
