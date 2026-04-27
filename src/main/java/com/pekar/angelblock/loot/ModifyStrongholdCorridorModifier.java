package com.pekar.angelblock.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class ModifyStrongholdCorridorModifier extends LootModifier
{
    public static final MapCodec<ModifyStrongholdCorridorModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst ->
                    codecStart(inst).apply(inst, ModifyStrongholdCorridorModifier::new)
            );

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected ModifyStrongholdCorridorModifier(LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        boolean needReplace = context.getRandom().nextIntBetweenInclusive(1, 5) == 2;
        if (!needReplace) return generatedLoot;

        if (generatedLoot.removeIf(x ->
                x.is(Items.IRON_CHESTPLATE) || x.is(Items.IRON_HELMET) || x.is(Items.IRON_LEGGINGS) || x.is(Items.IRON_BOOTS)
                        || x.is(Items.IRON_SWORD) || x.is(Items.IRON_PICKAXE)))
        {
            var newItem = switch (context.getRandom().nextIntBetweenInclusive(1, 5))
            {
                case 1 -> Items.COPPER_HORSE_ARMOR;
                case 2 -> Items.IRON_HORSE_ARMOR;
                case 3 -> Items.GOLDEN_HORSE_ARMOR;
                case 4 -> Items.DIAMOND_HORSE_ARMOR;
                case 5 -> Items.GOLDEN_APPLE;
                default -> throw new IllegalStateException("Unexpected value");
            };

            var newItemStack = new ItemStack(newItem);

            generatedLoot.add(newItemStack);
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }
}
