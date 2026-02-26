package com.pekar.angelblock.items;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.neoforged.neoforge.registries.DeferredItem;

public class ItemRegistry
{
    public static final TagKey<Item> RENDELITHIC_INGOT_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "rendelithic_ingot_tag"));
    public static final TagKey<Item> DIAMITHIC_INGOT_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "diamithic_ingot_tag"));
    public static final TagKey<Item> LAPIS_INGOT_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "lapis_ingot_tag"));
    public static final TagKey<Item> LIMONITE_INGOT_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "limonite_ingot_tag"));
    public static final TagKey<Item> SUPER_INGOT_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "super_ingot_tag"));
    public static final TagKey<Item> FLYING_INGOT_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "flying_ingot_tag"));
    public static final TagKey<Item> BAMBOO_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "bamboo_tag"));
    public static final TagKey<Item> DIAMOND_ARMOR_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "diamond_armor"));
    public static final TagKey<Item> NETHERITE_ARMOR_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "netherite_armor"));
    public static final TagKey<Item> DIAMOND_TOOL_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "diamond_tool"));
    public static final TagKey<Item> NETHERITE_TOOL_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "netherite_tool"));
    public static final TagKey<Item> PLANTER_COMPATIBLE_TO_PLANT = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "planter_compatible_to_plant"));
    public static final TagKey<Item> RODS_MODIFIED_BY_ROD_SENSOR = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "rods_modified_by_rod_sensor"));

    public static final ResourceKey<TrimMaterial> BIOS_DIAMOND_TRIM_MATERIAL = ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(Main.MODID, "bios_diamond"));

    public static final DeferredItem<Item> OBSIDIAN_POWDER = Main.ITEMS.registerItem("obsidian_powder", p -> new ModItemWithHoverText(TextStyle.Notice, p));
    public static final DeferredItem<Item> ENDSTONE_POWDER = Main.ITEMS.registerItem("endstone_powder", EndstonePowder::new);
    public static final DeferredItem<Item> DIAMOND_POWDER = Main.ITEMS.registerItem("diamond_powder", DiamondPowder::new);
    public static final DeferredItem<Item> SALTPETER = Main.ITEMS.registerItem("saltpeter", SaltPeter::new);
    public static final DeferredItem<Item> RENDELITHIC_POWDER = Main.ITEMS.registerItem("rendelithic_powder", ModItem::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> DIAMITHIC_POWDER = Main.ITEMS.registerItem("diamithic_powder", ModItem::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> LAPIS_POWDER = Main.ITEMS.registerItem("lapis_powder", ModItem::new);
    public static final DeferredItem<Item> LIMONITE_POWDER = Main.ITEMS.registerItem("limonite_powder", ModItem::new);
    public static final DeferredItem<Item> SUPER_POWDER = Main.ITEMS.registerItem("super_powder", ModItem::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> FLYING_POWDER = Main.ITEMS.registerItem("flying_powder", ModItem::new);

    public static final DeferredItem<Item> RENDELITHIC_INGOT = Main.ITEMS.registerItem("rendelithic_ingot", ModItem::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> DIAMITHIC_INGOT = Main.ITEMS.registerItem("diamithic_ingot", ModItem::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> LAPIS_INGOT = Main.ITEMS.registerItem("lapis_ingot", ModItem::new);
    public static final DeferredItem<Item> LIMONITE_INGOT = Main.ITEMS.registerItem("limonite_ingot", ModItem::new);
    public static final DeferredItem<Item> SUPER_INGOT = Main.ITEMS.registerItem("super_ingot", ModItem::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> FLYING_INGOT = Main.ITEMS.registerItem("flying_ingot", ModItem::new);

    public static final DeferredItem<Item> RENDELITHIC_PLATE = Main.ITEMS.registerItem("rendelithic_plate", ModItem::new, p -> p.fireResistant().stacksTo(16));
    public static final DeferredItem<Item> DIAMITHIC_PLATE = Main.ITEMS.registerItem("diamithic_plate", ModItem::new, p -> p.fireResistant().stacksTo(16));
    public static final DeferredItem<Item> LAPIS_PLATE = Main.ITEMS.registerItem("lapis_plate", ModItem::new, p -> p.stacksTo(16));
    public static final DeferredItem<Item> LIMONITE_PLATE = Main.ITEMS.registerItem("limonite_plate", ModItem::new, p -> p.stacksTo(16));
    public static final DeferredItem<Item> SUPER_PLATE = Main.ITEMS.registerItem("super_plate", ModItem::new, p -> p.fireResistant().stacksTo(16));
    public static final DeferredItem<Item> FLYING_PLATE = Main.ITEMS.registerItem("flying_plate", ModItem::new, p -> p.stacksTo(16));

    public static final DeferredItem<Item> RENDELITHIC_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("rendelithic_armor_upgrade_kit", ModArmorUpgradeKit::new);
    public static final DeferredItem<Item> DIAMITHIC_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("diamithic_armor_upgrade_kit", ModArmorUpgradeKit::new);
    public static final DeferredItem<Item> LAPIS_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("lapis_armor_upgrade_kit", ModArmorUpgradeKit::new);
    public static final DeferredItem<Item> LIMONITE_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("limonite_armor_upgrade_kit", ModArmorUpgradeKit::new);
    public static final DeferredItem<Item> SUPER_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("super_armor_upgrade_kit", ModArmorUpgradeKit::new);
    public static final DeferredItem<Item> DIAMOND_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("diamond_armor_upgrade_kit", VanillaArmorUpgradeKit::new);
    public static final DeferredItem<Item> IRON_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("iron_armor_upgrade_kit", VanillaArmorUpgradeKit::new);
    public static final DeferredItem<Item> FLYING_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("flying_armor_upgrade_kit", ModArmorUpgradeKit::new);

    public static final DeferredItem<Item> RENDELITHIC_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("rendelithic_tool_upgrade_kit", ModToolUpgradeKit::new);
    public static final DeferredItem<Item> DIAMITHIC_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("diamithic_tool_upgrade_kit", ModToolUpgradeKit::new);
    public static final DeferredItem<Item> LAPIS_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("lapis_tool_upgrade_kit", ModToolUpgradeKit::new);
    public static final DeferredItem<Item> LIMONITE_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("limonite_tool_upgrade_kit", ModToolUpgradeKit::new);
    public static final DeferredItem<Item> SUPER_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("super_tool_upgrade_kit", ModToolUpgradeKit::new);
    public static final DeferredItem<Item> DIAMOND_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("diamond_tool_upgrade_kit", VanillaToolUpgradeKit::new);
    public static final DeferredItem<Item> IRON_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("iron_tool_upgrade_kit", VanillaToolUpgradeKit::new);

    public static final DeferredItem<Item> ROD_UPGRADE_KIT = Main.ITEMS.registerItem("rod_upgrade_kit", RodUpgradeKit::new);
    public static final DeferredItem<Item> DOWNGRADE_KIT = Main.ITEMS.registerItem("downgrade_kit", DowngradeKit::new);

    public static final DeferredItem<Item> HORSE_ARMOR_HANDBOOK = Main.ITEMS.registerItem("horse_armor_handbook", AnimalArmorHandbook::new, p -> p.rarity(Rarity.UNCOMMON).stacksTo(4));
    public static final DeferredItem<Item> WOLF_ARMOR_HANDBOOK = Main.ITEMS.registerItem("wolf_armor_handbook", AnimalArmorHandbook::new, p -> p.rarity(Rarity.UNCOMMON).stacksTo(4));
    public static final DeferredItem<Item> NAUTILUS_ARMOR_HANDBOOK = Main.ITEMS.registerItem("nautilus_armor_handbook", AnimalArmorHandbook::new, p -> p.rarity(Rarity.UNCOMMON).stacksTo(4));

    public static final DeferredItem<Item> VESICULAR_TERRACOTTA = Main.ITEMS.registerItem("vesicular_terracotta", p -> new ModItemWithHoverText(TextStyle.Notice, p), Item.Properties::fireResistant);
    public static final DeferredItem<Item> BASALT_FIBER = Main.ITEMS.registerItem("basalt_fiber", ModItem::new);
    public static final DeferredItem<Item> FLAX_FIBRE = Main.ITEMS.registerItem("flax_fibre", p -> new ModItemWithHoverText(TextStyle.Notice, p));
    public static final DeferredItem<Item> ENERGY_CRYSTAL = Main.ITEMS.registerItem("energy_crystal", ModItemWithHoverText::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> ARMOR_FIBER = Main.ITEMS.registerItem("armor_fiber", ModItem::new);
    public static final DeferredItem<Item> ENERGY_FIBER = Main.ITEMS.registerItem("energy_fiber", ModItemWithHoverText::new);
    public static final DeferredItem<Item> BIOS_DIAMOND = Main.ITEMS.registerItem("bios_diamond", BiosDiamond::new, p -> p.trimMaterial(BIOS_DIAMOND_TRIM_MATERIAL).fireResistant());
    public static final DeferredItem<Item> END_SAPPHIRE = Main.ITEMS.registerItem("end_sapphire", EndSapphire::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> FLAME_STONE = Main.ITEMS.registerItem("flame_stone", ModItemWithDoubleHoverText::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> MARINE_CRYSTAL = Main.ITEMS.registerItem("marine_crystal", MarineCrystal::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> STRENGTH_PEARL = Main.ITEMS.registerItem("strength_pearl", ModItemWithDoubleHoverText::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> SUPER_CRYSTAL = Main.ITEMS.registerItem("super_crystal", ModItem::new, Item.Properties::fireResistant);
    public static final DeferredItem<Item> GUARDIAN_EYE = Main.ITEMS.registerItem("guardian_eye", GuardianEye::new);
    public static final DeferredItem<Item> MINER_FIGURE = Main.ITEMS.registerItem("miner_figure", MinerFigure::new);
    public static final DeferredItem<Item> ANCIENT_CANINE = Main.ITEMS.registerItem("ancient_canine", p -> new ModItemWithHoverText(TextStyle.Notice, p));
    public static final DeferredItem<Item> EVOKER_AMULET = Main.ITEMS.registerItem("evoker_amulet", EvokerAmulet::new);
    public static final DeferredItem<Item> ANCIENT_SCROLL = Main.ITEMS.registerItem("ancient_scroll", p -> new ModItemWithHoverText(TextStyle.Notice, p));
    public static final DeferredItem<Item> BLUE_AXOLOTL_BUCKET = Main.ITEMS.registerItem("blue_axolotl_bucket", BlueAxolotlBucket::new, p -> p.rarity(Rarity.EPIC).stacksTo(1));
    public static final DeferredItem<Item> ROD_SENSOR = Main.ITEMS.registerItem("rod_sensor", ModItem::new, Item.Properties::fireResistant);

    public static final DeferredItem<Item> BLOCK_BREAKER_POTION_ITEM = Main.ITEMS.registerItem("block_breaker_potion_item", BlockBreakerPotionItem::new,
            p -> p.stacksTo(4));
    public static final DeferredItem<Item> SOARING_SPORE_ESSENCE = Main.ITEMS.registerItem("soaring_spore_essence", SoaringSporeEssence::new,
            p -> p.stacksTo(4));

    public static void initStatic()
    {
        // just to initialize static members
    }
}

