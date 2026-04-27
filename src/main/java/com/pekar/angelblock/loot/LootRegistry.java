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

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ReplaceToEnchantedBookModifier>> REPLACE_ENCHANTED_BOOK_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("replace_enchanted_book", () -> ReplaceToEnchantedBookModifier.CODEC);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ModifyDesertArcheologyModifier>> MODIFY_DESERT_ARCHAEOLOGY_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("modify_desert_archaeology", () -> ModifyDesertArcheologyModifier.CODEC);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ModifyOceanArcheologyModifier>> MODIFY_OCEAN_ARCHAEOLOGY_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("modify_ocean_archaeology", () -> ModifyOceanArcheologyModifier.CODEC);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ReplaceSingleItemWithChanceModifier>> REPLACE_SINGLE_ITEM_WITH_CHANCE_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("replace_single_item_with_chance", () -> ReplaceSingleItemWithChanceModifier.CODEC);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ModifyHeroLeatherworkerGiftModifier>> MODIFY_HERO_LEATHERWORKER_GIFT_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("modify_hero_leatherworker_gift", () -> ModifyHeroLeatherworkerGiftModifier.CODEC);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ModifyHeroToolsmithGiftModifier>> MODIFY_HERO_TOOLSMITH_GIFT_LOOT_MODIFIER =
            LOOT_MODIFIERS.register("modify_hero_toolsmith_gift", () -> ModifyHeroToolsmithGiftModifier.CODEC);

    public static void initStatic()
    {
        // just to initialize static members
    }
}
