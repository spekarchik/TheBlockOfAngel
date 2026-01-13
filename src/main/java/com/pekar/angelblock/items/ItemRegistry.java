package com.pekar.angelblock.items;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.TextStyle;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class ItemRegistry
{
    public static final DeferredItem<Item> OBSIDIAN_POWDER = Main.ITEMS.register("obsidian_powder", () -> new ModItemWithHoverText(TextStyle.Notice));
    public static final DeferredItem<Item> ENDSTONE_POWDER = Main.ITEMS.register("endstone_powder", EndstonePowder::new);
    public static final DeferredItem<Item> DIAMOND_POWDER = Main.ITEMS.register("diamond_powder", DiamondPowder::new);
    public static final DeferredItem<Item> SALTPETER = Main.ITEMS.register("saltpeter", SaltPeter::new);
    public static final DeferredItem<Item> RENDELITHIC_POWDER = Main.ITEMS.register("rendelithic_powder", () -> new ModItem());
    public static final DeferredItem<Item> DIAMITHIC_POWDER = Main.ITEMS.register("diamithic_powder", () -> new ModItem());
    public static final DeferredItem<Item> LAPIS_POWDER = Main.ITEMS.register("lapis_powder", () -> new ModItem());
    public static final DeferredItem<Item> LIMONITE_POWDER = Main.ITEMS.register("limonite_powder", () -> new ModItem());
    public static final DeferredItem<Item> SUPER_POWDER = Main.ITEMS.register("super_powder", () -> new ModItem());
    public static final DeferredItem<Item> FLYING_POWDER = Main.ITEMS.register("flying_powder", () -> new ModItem());

    public static final TagKey<Item> RODS_MODIFIED_BY_ROD_SENSOR = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Main.MODID, "rods_modified_by_rod_sensor"));

    public static final DeferredItem<Item> RENDELITHIC_INGOT = Main.ITEMS.register("rendelithic_ingot", () -> new ModItem());
    public static final DeferredItem<Item> DIAMITHIC_INGOT = Main.ITEMS.register("diamithic_ingot", () -> new ModItem());
    public static final DeferredItem<Item> LAPIS_INGOT = Main.ITEMS.register("lapis_ingot", () -> new ModItem());
    public static final DeferredItem<Item> LIMONITE_INGOT = Main.ITEMS.register("limonite_ingot", () -> new ModItem());
    public static final DeferredItem<Item> SUPER_INGOT = Main.ITEMS.register("super_ingot", () -> new ModItem());
    public static final DeferredItem<Item> FLYING_INGOT = Main.ITEMS.register("flying_ingot", () -> new ModItem());

    public static final DeferredItem<Item> RENDELITHIC_PLATE = Main.ITEMS.register("rendelithic_plate", () -> new ModItem());
    public static final DeferredItem<Item> DIAMITHIC_PLATE = Main.ITEMS.register("diamithic_plate", () -> new ModItem());
    public static final DeferredItem<Item> LAPIS_PLATE = Main.ITEMS.register("lapis_plate", () -> new ModItem());
    public static final DeferredItem<Item> LIMONITE_PLATE = Main.ITEMS.register("limonite_plate", () -> new ModItem());
    public static final DeferredItem<Item> SUPER_PLATE = Main.ITEMS.register("super_plate", () -> new ModItem());
    public static final DeferredItem<Item> FLYING_PLATE = Main.ITEMS.register("flying_plate", () -> new ModItem());

    public static final DeferredItem<Item> RENDELITHIC_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("rendelithic_armor_upgrade_kit", ModArmorUpgradeKit::new);
    public static final DeferredItem<Item> DIAMITHIC_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("diamithic_armor_upgrade_kit", ModArmorUpgradeKit::new);
    public static final DeferredItem<Item> LAPIS_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("lapis_armor_upgrade_kit", ModArmorUpgradeKit::new);
    public static final DeferredItem<Item> LIMONITE_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("limonite_armor_upgrade_kit", ModArmorUpgradeKit::new);
    public static final DeferredItem<Item> SUPER_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("super_armor_upgrade_kit", ModArmorUpgradeKit::new);
    public static final DeferredItem<Item> DIAMOND_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("diamond_armor_upgrade_kit", DiamondArmorUpgradeKit::new);
    public static final DeferredItem<Item> FLYING_ARMOR_UPGRADE_KIT = Main.ITEMS.registerItem("flying_armor_upgrade_kit", ModArmorUpgradeKit::new);

    public static final DeferredItem<Item> RENDELITHIC_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("rendelithic_tool_upgrade_kit", ModToolUpgradeKit::new);
    public static final DeferredItem<Item> DIAMITHIC_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("diamithic_tool_upgrade_kit", ModToolUpgradeKit::new);
    public static final DeferredItem<Item> LAPIS_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("lapis_tool_upgrade_kit", ModToolUpgradeKit::new);
    public static final DeferredItem<Item> LIMONITE_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("limonite_tool_upgrade_kit", ModToolUpgradeKit::new);
    public static final DeferredItem<Item> SUPER_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("super_tool_upgrade_kit", ModToolUpgradeKit::new);
    public static final DeferredItem<Item> DIAMOND_TOOL_UPGRADE_KIT = Main.ITEMS.registerItem("diamond_tool_upgrade_kit", DiamondToolUpgradeKit::new);

    public static final DeferredItem<Item> ROD_UPGRADE_KIT = Main.ITEMS.registerItem("rod_upgrade_kit", RodUpgradeKit::new);
    public static final DeferredItem<Item> DOWNGRADE_KIT = Main.ITEMS.registerItem("downgrade_kit", DowngradeKit::new);

    public static final DeferredItem<Item> VESICULAR_TERRACOTTA = Main.ITEMS.register("vesicular_terracotta", () -> new ModItemWithHoverText(TextStyle.Notice));
    public static final DeferredItem<Item> BASALT_FIBER = Main.ITEMS.register("basalt_fiber", () -> new ModItem());
    public static final DeferredItem<Item> FLAX_FIBRE = Main.ITEMS.register("flax_fibre", () -> new ModItemWithHoverText(TextStyle.Notice));
    public static final DeferredItem<Item> ENERGY_CRYSTAL = Main.ITEMS.register("energy_crystal", () -> new ModItemWithHoverText());
    public static final DeferredItem<Item> ARMOR_FIBER = Main.ITEMS.register("armor_fiber", () -> new ModItem());
    public static final DeferredItem<Item> ENERGY_FIBER = Main.ITEMS.register("energy_fiber", () -> new ModItemWithHoverText());
    public static final DeferredItem<Item> BIOS_DIAMOND = Main.ITEMS.register("bios_diamond", BiosDiamond::new);
    public static final DeferredItem<Item> END_SAPPHIRE = Main.ITEMS.register("end_sapphire", EndSapphire::new);
    public static final DeferredItem<Item> FLAME_STONE = Main.ITEMS.register("flame_stone", () -> new ModItemWithDoubleHoverText());
    public static final DeferredItem<Item> MARINE_CRYSTAL = Main.ITEMS.register("marine_crystal", MarineCrystal::new);
    public static final DeferredItem<Item> STRENGTH_PEARL = Main.ITEMS.register("strength_pearl", () -> new ModItemWithDoubleHoverText());
    public static final DeferredItem<Item> SUPER_CRYSTAL = Main.ITEMS.register("super_crystal", () -> new ModItem());
    public static final DeferredItem<Item> GUARDIAN_EYE = Main.ITEMS.register("guardian_eye", GuardianEye::new);
    public static final DeferredItem<Item> MINER_FIGURE = Main.ITEMS.register("miner_figure", MinerFigure::new);
    public static final DeferredItem<Item> ANCIENT_CANINE = Main.ITEMS.register("ancient_canine", () -> new ModItemWithHoverText(TextStyle.Notice));
    public static final DeferredItem<Item> EVOKER_AMULET = Main.ITEMS.register("evoker_amulet", EvokerAmulet::new);
//    public static final DeferredItem<Item> NETHER_BARS = Main.ITEMS.register("nether_bars", () -> new ModItemWithHoverText(TextStyle.Notice));
    public static final DeferredItem<Item> ANCIENT_SCROLL = Main.ITEMS.register("ancient_scroll", () -> new ModItemWithHoverText(TextStyle.Notice));
    public static final DeferredItem<Item> BLUE_AXOLOTL_BUCKET = Main.ITEMS.register("blue_axolotl_bucket", BlueAxolotlBucket::new);

    public static final DeferredItem<Item> BLOCK_BREAKER_POTION_ITEM = Main.ITEMS.register("block_breaker_potion_item", BlockBreakerPotionItem::new);
    public static final DeferredItem<Item> SOARING_SPORE_ESSENCE = Main.ITEMS.register("soaring_spore_essence", SoaringSporeEssence::new);

    public static void initStatic()
    {
        // just to initialize static members
    }
}

