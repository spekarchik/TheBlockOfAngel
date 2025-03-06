package com.pekar.angelblock.recipe;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;

import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class FilteringPotionIngredient implements ICustomIngredient
{
    private final Predicate<ItemStack> condition;

    private FilteringPotionIngredient(Predicate<ItemStack> condition)
    {
        this.condition = condition;
    }

    protected FilteringPotionIngredient(Item potionGroup, Holder<Potion> potionType)
    {
        this(stack -> {
            if (stack.isEmpty() || stack.getItem() != potionGroup)
            {
                return false;
            }

            PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            return contents.potion().flatMap(holder -> holder.unwrapKey().map(ResourceKey::location))
                    .equals(potionType.unwrapKey().map(ResourceKey::location));
        });
    }

    @Override
    public boolean test(ItemStack stack)
    {
        return condition.test(stack);
    }

    @Override
    public Stream<ItemStack> getItems()
    {
        return Stream.empty();
    }

    @Override
    public boolean isSimple()
    {
        return false;
    }
}
