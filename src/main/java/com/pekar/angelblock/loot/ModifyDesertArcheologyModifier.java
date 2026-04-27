package com.pekar.angelblock.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pekar.angelblock.items.ItemRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class ModifyDesertArcheologyModifier extends LootModifier
{
    public static final MapCodec<ModifyDesertArcheologyModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst ->
                    codecStart(inst).apply(inst, ModifyDesertArcheologyModifier::new)
            );

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected ModifyDesertArcheologyModifier(LootItemCondition[] conditionsIn, int priority)
    {
        super(conditionsIn, priority);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        int totalChances = context.getQueriedLootTableId().getPath().equals("archaeology/desert_pyramid") ? 9 : 5;
        boolean needReplace = context.getRandom().nextIntBetweenInclusive(1, totalChances) == 2;
        if (!needReplace) return generatedLoot;

        var newItemStack = ItemRegistry.ROD_UPGRADE_KIT.toStack();

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
