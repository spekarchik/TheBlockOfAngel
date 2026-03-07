package com.pekar.angelblock.loot;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.pekar.angelblock.Main.LOOT_MODIFIERS;
import static com.pekar.angelblock.Main.MODID;
import static com.pekar.angelblock.utils.Resources.createResourceLocation;

public class LootRegistry
{
    public static final TagKey<Enchantment> ARMORER_SPECIFIC_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(MODID, "armorer_specific"));
    public static final TagKey<Enchantment> FISHER_SPECIFIC_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(MODID, "fisher_specific"));
    public static final TagKey<Enchantment> FLETCHER_SPECIFIC_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(MODID, "fletcher_specific"));
    public static final TagKey<Enchantment> TOOLSMITH_SPECIFIC_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(MODID, "toolsmith_specific"));
    public static final TagKey<Enchantment> WEAPONSMITH_SPECIFIC_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(MODID, "weaponsmith_specific"));
    public static final TagKey<Enchantment> ON_GOOD_LOOT_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(MODID, "on_good_loot"));

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ReplaceEnchantedBookModifier>> REPLACE_ENCHANTED_BOOK_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("replace_enchanted_book", () -> ReplaceEnchantedBookModifier.CODEC);

    public static void initStatic()
    {
        // just to initialize static members
    }
}
