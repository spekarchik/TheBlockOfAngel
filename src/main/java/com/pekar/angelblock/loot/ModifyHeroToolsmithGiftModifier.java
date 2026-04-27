package com.pekar.angelblock.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pekar.angelblock.items.ItemRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import static com.pekar.angelblock.loot.LootRegistry.ON_GOOD_LOOT_ENCHANTMENTS;

public class ModifyHeroToolsmithGiftModifier extends LootModifier
{
    public static final MapCodec<ModifyHeroToolsmithGiftModifier> CODEC =
            RecordCodecBuilder.mapCodec(inst ->
                    codecStart(inst).apply(inst, ModifyHeroToolsmithGiftModifier::new)
            );

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected ModifyHeroToolsmithGiftModifier(LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        int option = context.getRandom().nextIntBetweenInclusive(1, 8);
        if (option > 5)
        {
            var itemStack = generatedLoot.getFirst();
            if (itemStack.isEnchantable())
            {
                generatedLoot.removeFirst();
                generatedLoot.add(createEnchantedItem(context, itemStack.getItem()));
            }
            return generatedLoot;
        }

        generatedLoot.removeFirst();

        switch (option)
        {
            case 1 -> generatedLoot.add(ItemRegistry.RENDELITHIC_TOOL_UPGRADE_KIT.toStack());
            case 2 -> generatedLoot.add(ItemRegistry.DIAMITHIC_TOOL_UPGRADE_KIT.toStack());
            case 3 -> generatedLoot.add(ItemRegistry.LAPIS_TOOL_UPGRADE_KIT.toStack());
            case 4 -> generatedLoot.add(ItemRegistry.LIMONITE_TOOL_UPGRADE_KIT.toStack());
            case 5 -> generatedLoot.add(ItemRegistry.SUPER_TOOL_UPGRADE_KIT.toStack());
        }

        return generatedLoot;
    }

    private ItemStack createEnchantedItem(LootContext context, Item item)
    {
        var registryAccess = context.getLevel().registryAccess();
        var enchantedItem = new ItemStack(item);
        var enchantments = registryAccess.lookupOrThrow(Registries.ENCHANTMENT);
        var holderSet = enchantments.get(ON_GOOD_LOOT_ENCHANTMENTS);

        return EnchantmentHelper.enchantItem(
                context.getRandom(),
                enchantedItem,
                35,
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
