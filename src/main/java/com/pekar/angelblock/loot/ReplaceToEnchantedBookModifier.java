package com.pekar.angelblock.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import static com.pekar.angelblock.loot.LootRegistry.ON_GOOD_LOOT_ENCHANTMENTS;

public class ReplaceToEnchantedBookModifier extends LootModifier
{
    public static final MapCodec<ReplaceToEnchantedBookModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst ->
                    codecStart(inst)
                            .and(inst.group(Codec.INT.fieldOf("enchantmentCost").forGetter(e -> e.enchantmentCost),
                                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("itemToReplace").forGetter(e -> e.itemToReplace)))
                            .apply(inst, ReplaceToEnchantedBookModifier::new)
            );

    private final int enchantmentCost;
    private final Item itemToReplace;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected ReplaceToEnchantedBookModifier(LootItemCondition[] conditionsIn, int priority, int enchantmentCost, Item itemToReplace)
    {
        super(conditionsIn, priority);
        this.enchantmentCost = enchantmentCost;
        this.itemToReplace = itemToReplace;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        boolean removed = generatedLoot.removeIf(x -> x.is(itemToReplace));
        if (removed)
        {
            var enchantedBook = createEnchantedBookWithGoodLootEnchantment(context);
            generatedLoot.add(enchantedBook);
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }

    private ItemStack createEnchantedBookWithGoodLootEnchantment(LootContext context)
    {
        var registryAccess = context.getLevel().registryAccess();
        var book = new ItemStack(Items.BOOK);
        var enchantments = registryAccess.lookupOrThrow(Registries.ENCHANTMENT);
        var holderSet = enchantments.get(ON_GOOD_LOOT_ENCHANTMENTS);

        return EnchantmentHelper.enchantItem(
                context.getRandom(),
                book,
                enchantmentCost,
                registryAccess,
                holderSet
        );
    }
}
