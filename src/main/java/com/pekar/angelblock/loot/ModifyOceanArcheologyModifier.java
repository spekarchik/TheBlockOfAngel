package com.pekar.angelblock.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pekar.angelblock.items.ItemRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class ModifyOceanArcheologyModifier extends LootModifier
{
    public static final MapCodec<ModifyOceanArcheologyModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst ->
                    codecStart(inst).apply(inst, ModifyOceanArcheologyModifier::new)
            );

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected ModifyOceanArcheologyModifier(LootItemCondition[] conditionsIn, int priority)
    {
        super(conditionsIn, priority);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        boolean needReplace = context.getRandom().nextIntBetweenInclusive(1, 8) == 2;
        if (!needReplace) return generatedLoot;

        boolean isWarmOcean = context.getQueriedLootTableId().getPath().equals("archaeology/ocean_ruin_warm");
        int numOfOptions = isWarmOcean ? 3 : 2;
        int rnd = context.getRandom().nextIntBetweenInclusive(1, numOfOptions);

        generatedLoot.removeFirst();

        switch (rnd)
        {
            case 1 -> generatedLoot.add(ItemRegistry.MARINE_CRYSTAL.toStack());
            case 2 -> generatedLoot.add(createTurtleHelmet(context));
            case 3 -> generatedLoot.add(ItemRegistry.FLYING_ARMOR_UPGRADE_KIT.toStack());
        }

        return generatedLoot;
    }

    private ItemStack createTurtleHelmet(LootContext context)
    {
        var registryAccess = context.getLevel().registryAccess();
        var helmet = new ItemStack(Items.TURTLE_HELMET);
        var enchantments = registryAccess.lookupOrThrow(Registries.ENCHANTMENT);
        var holderSet = enchantments.get(EnchantmentTags.ON_RANDOM_LOOT);

        return EnchantmentHelper.enchantItem(
                context.getRandom(),
                helmet,
                30,
                registryAccess,
                holderSet
        );
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }
}
