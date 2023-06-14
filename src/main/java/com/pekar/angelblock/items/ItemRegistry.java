package com.pekar.angelblock.items;

import com.pekar.angelblock.Main;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry
{
    public static final RegistryObject<Item> OBSIDIAN_POWDER = Main.ITEMS.register("obsidian_powder", ModItemWithHoverText::new);
    public static final RegistryObject<Item> ENDSTONE_POWDER = Main.ITEMS.register("endstone_powder", EndstonePowder::new);
    public static final RegistryObject<Item> DIAMOND_POWDER = Main.ITEMS.register("diamond_powder", ModItemWithHoverText::new);
    public static final RegistryObject<Item> RENDELITHIC_POWDER = Main.ITEMS.register("rendelithic_powder", ModItem::new);
    public static final RegistryObject<Item> DIAMITHIC_POWDER = Main.ITEMS.register("diamithic_powder", ModItem::new);
    public static final RegistryObject<Item> LAPIS_POWDER = Main.ITEMS.register("lapis_powder", ModItem::new);
    public static final RegistryObject<Item> LIMONITE_POWDER = Main.ITEMS.register("limonite_powder", ModItem::new);
    public static final RegistryObject<Item> SUPER_POWDER = Main.ITEMS.register("super_powder", ModItem::new);
    public static final RegistryObject<Item> FLYING_POWDER = Main.ITEMS.register("flying_powder", ModItem::new);

    public static final RegistryObject<Item> RENDELITHIC_INGOT = Main.ITEMS.register("rendelithic_ingot", ModItem::new);
    public static final RegistryObject<Item> DIAMITHIC_INGOT = Main.ITEMS.register("diamithic_ingot", ModItem::new);
    public static final RegistryObject<Item> LAPIS_INGOT = Main.ITEMS.register("lapis_ingot", ModItem::new);
    public static final RegistryObject<Item> LIMONITE_INGOT = Main.ITEMS.register("limonite_ingot", ModItem::new);
    public static final RegistryObject<Item> SUPER_INGOT = Main.ITEMS.register("super_ingot", ModItem::new);
    public static final RegistryObject<Item> FLYING_INGOT = Main.ITEMS.register("flying_ingot", ModItem::new);

    public static final RegistryObject<Item> RENDELITHIC_PLATE = Main.ITEMS.register("rendelithic_plate", ModItem::new);
    public static final RegistryObject<Item> DIAMITHIC_PLATE = Main.ITEMS.register("diamithic_plate", ModItem::new);
    public static final RegistryObject<Item> LAPIS_PLATE = Main.ITEMS.register("lapis_plate", ModItem::new);
    public static final RegistryObject<Item> LIMONITE_PLATE = Main.ITEMS.register("limonite_plate", ModItem::new);
    public static final RegistryObject<Item> SUPER_PLATE = Main.ITEMS.register("super_plate", ModItem::new);
    public static final RegistryObject<Item> FLYING_PLATE = Main.ITEMS.register("flying_plate", ModItem::new);

    public static final RegistryObject<Item> RENDELITHIC_UPGRADE_SMITHING_TEMPLATE = Main.ITEMS.register("rendelithic_upgrade_smithing_template", ModItemWithHoverText::new);
    public static final RegistryObject<Item> DIAMITHIC_UPGRADE_SMITHING_TEMPLATE = Main.ITEMS.register("diamithic_upgrade_smithing_template", ModItemWithHoverText::new);
    public static final RegistryObject<Item> LAPIS_UPGRADE_SMITHING_TEMPLATE = Main.ITEMS.register("lapis_upgrade_smithing_template", ModItemWithHoverText::new);
    public static final RegistryObject<Item> LIMONITE_UPGRADE_SMITHING_TEMPLATE = Main.ITEMS.register("limonite_upgrade_smithing_template", ModItemWithHoverText::new);
    public static final RegistryObject<Item> SUPER_UPGRADE_SMITHING_TEMPLATE = Main.ITEMS.register("super_upgrade_smithing_template", ModItemWithHoverText::new);

    public static final RegistryObject<Item> VESICULAR_TERRACOTTA = Main.ITEMS.register("vesicular_terracotta", ModItemWithHoverText::new);
    public static final RegistryObject<Item> FLAX_FIBRE = Main.ITEMS.register("flax_fibre", ModItemWithHoverText::new);
    public static final RegistryObject<Item> ENERGY_CRYSTAL = Main.ITEMS.register("energy_crystal", ModItem::new);
    public static final RegistryObject<Item> ARMOR_FIBER = Main.ITEMS.register("armor_fiber", ModItem::new);
    public static final RegistryObject<Item> ENERGY_FIBER = Main.ITEMS.register("energy_fiber", ModItem::new);
    public static final RegistryObject<Item> BIOS_DIAMOND = Main.ITEMS.register("bios_diamond", BiosDiamond::new);
    public static final RegistryObject<Item> END_SAPPHIRE = Main.ITEMS.register("end_sapphire", EndSapphire::new);
    public static final RegistryObject<Item> FLAME_STONE = Main.ITEMS.register("flame_stone", ModItemWithDoubleHoverText::new);
    public static final RegistryObject<Item> MARINE_CRYSTAL = Main.ITEMS.register("marine_crystal", ModItemWithDoubleHoverText::new);
    public static final RegistryObject<Item> STRENGTH_PEARL = Main.ITEMS.register("strength_pearl", ModItemWithDoubleHoverText::new);
    public static final RegistryObject<Item> SUPER_CRYSTAL = Main.ITEMS.register("super_crystal", ModItem::new);
    public static final RegistryObject<Item> GUARDIAN_EYE = Main.ITEMS.register("guardian_eye", GuardianEye::new);
    public static final RegistryObject<Item> MINER_FIGURE = Main.ITEMS.register("miner_figure", ModItemWithHoverText::new);
    public static final RegistryObject<Item> ANCIENT_CANINE = Main.ITEMS.register("ancient_canine", ModItemWithHoverText::new);
    public static final RegistryObject<Item> EVOKER_AMULET = Main.ITEMS.register("evoker_amulet", EvokerAmulet::new);
    public static final RegistryObject<Item> NETHER_BARS = Main.ITEMS.register("nether_bars", ModItemWithHoverText::new);
    public static final RegistryObject<Item> ANCIENT_SCROLL = Main.ITEMS.register("ancient_scroll", ModItemWithHoverText::new);

    public static void initStatic()
    {
        // just to initialize static members
    }
}

