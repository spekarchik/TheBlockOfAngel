package com.pekar.angelblock.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class ReplaceSingleItemWithChanceModifier extends LootModifier
{
    public static final MapCodec<ReplaceSingleItemWithChanceModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst ->
                    codecStart(inst)
                            .and(inst.group(Codec.INT.fieldOf("numOfOptions").forGetter(e -> e.numOfOptions),
                                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item)))
                            .apply(inst, ReplaceSingleItemWithChanceModifier::new)
            );

    private final int numOfOptions;
    private final Item item;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected ReplaceSingleItemWithChanceModifier(LootItemCondition[] conditionsIn, int priority, int numOfOptions, Item item)
    {
        super(conditionsIn, priority);
        this.numOfOptions = numOfOptions;
        this.item = item;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        boolean needReplace = context.getRandom().nextIntBetweenInclusive(1, numOfOptions) == 2;
        if (!needReplace) return generatedLoot;

        var newItemStack = new ItemStack(item);

        generatedLoot.removeFirst();
        generatedLoot.add(newItemStack);

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }
}
