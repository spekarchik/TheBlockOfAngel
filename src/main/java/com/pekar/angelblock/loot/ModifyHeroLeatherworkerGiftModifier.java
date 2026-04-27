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

public class ModifyHeroLeatherworkerGiftModifier extends LootModifier
{
    public static final MapCodec<ModifyHeroLeatherworkerGiftModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst ->
                    codecStart(inst).apply(inst, ModifyHeroLeatherworkerGiftModifier::new)
            );

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected ModifyHeroLeatherworkerGiftModifier(LootItemCondition[] conditionsIn, int priority)
    {
        super(conditionsIn, priority);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        int option = context.getRandom().nextIntBetweenInclusive(1, 7);
        if (option > 5) return generatedLoot;

        generatedLoot.removeFirst();

        switch (option)
        {
            case 1 -> generatedLoot.add(ItemRegistry.RENDELITHIC_ARMOR_UPGRADE_KIT.toStack());
            case 2 -> generatedLoot.add(ItemRegistry.DIAMITHIC_ARMOR_UPGRADE_KIT.toStack());
            case 3 -> generatedLoot.add(ItemRegistry.LAPIS_ARMOR_UPGRADE_KIT.toStack());
            case 4 -> generatedLoot.add(ItemRegistry.LIMONITE_ARMOR_UPGRADE_KIT.toStack());
            case 5 -> generatedLoot.add(ItemRegistry.FLYING_ARMOR_UPGRADE_KIT.toStack());
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }
}
