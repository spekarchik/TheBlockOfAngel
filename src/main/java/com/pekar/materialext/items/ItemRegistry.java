package com.pekar.materialext.items;

import com.pekar.materialext.MaterialExt;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry
{
    public static final RegistryObject<Item> OBSIDIAN_POWDER = MaterialExt.ITEMS.register("obsidian_powder", ModItem::new);
    public static final RegistryObject<Item> ENDSTONE_POWDER = MaterialExt.ITEMS.register("endstone_powder", ModItem::new);
    public static final RegistryObject<Item> DIAMOND_POWDER = MaterialExt.ITEMS.register("diamond_powder", ModItem::new);
    public static final RegistryObject<Item> RENDELITHIC_POWDER = MaterialExt.ITEMS.register("rendelithic_powder", ModItem::new);
    public static final RegistryObject<Item> DIAMITHIC_POWDER = MaterialExt.ITEMS.register("diamithic_powder", ModItem::new);
    public static final RegistryObject<Item> LAPIS_POWDER = MaterialExt.ITEMS.register("lapis_powder", ModItem::new);
    public static final RegistryObject<Item> LIMONITE_POWDER = MaterialExt.ITEMS.register("limonite_powder", ModItem::new);
    public static final RegistryObject<Item> SUPER_POWDER = MaterialExt.ITEMS.register("super_powder", ModItem::new);

    public static final RegistryObject<Item> RENDELITHIC_INGOT = MaterialExt.ITEMS.register("rendelithic_ingot", ModItem::new);
    public static final RegistryObject<Item> DIAMITHIC_INGOT = MaterialExt.ITEMS.register("diamithic_ingot", ModItem::new);
    public static final RegistryObject<Item> LAPIS_INGOT = MaterialExt.ITEMS.register("lapis_ingot", ModItem::new);
    public static final RegistryObject<Item> LIMONITE_INGOT = MaterialExt.ITEMS.register("limonite_ingot", ModItem::new);
    public static final RegistryObject<Item> SUPER_INGOT = MaterialExt.ITEMS.register("super_ingot", ModItem::new);

    public static final RegistryObject<Item> RENDELITHIC_PLATE = MaterialExt.ITEMS.register("rendelithic_plate", ModItem::new);
    public static final RegistryObject<Item> DIAMITHIC_PLATE = MaterialExt.ITEMS.register("diamithic_plate", ModItem::new);
    public static final RegistryObject<Item> LAPIS_PLATE = MaterialExt.ITEMS.register("lapis_plate", ModItem::new);
    public static final RegistryObject<Item> LIMONITE_PLATE = MaterialExt.ITEMS.register("limonite_plate", ModItem::new);
    public static final RegistryObject<Item> SUPER_PLATE = MaterialExt.ITEMS.register("super_plate", ModItem::new);

    public static final RegistryObject<Item> VESICULAR_TERRACOTTA = MaterialExt.ITEMS.register("vesicular_terracotta", ModItem::new);
    public static final RegistryObject<Item> FLAX_FIBRE = MaterialExt.ITEMS.register("flax_fibre", ModItem::new);
    public static final RegistryObject<Item> ENERGY_CRYSTAL = MaterialExt.ITEMS.register("energy_crystal", ModItem::new);
    public static final RegistryObject<Item> ARMOR_FIBER = MaterialExt.ITEMS.register("armor_fiber", ModItem::new);
    public static final RegistryObject<Item> ENERGY_FIBER = MaterialExt.ITEMS.register("", ModItem::new);
    public static final RegistryObject<Item> BIOS_DIAMOND = MaterialExt.ITEMS.register("bios_diamond", ModItem::new);
    public static final RegistryObject<Item> END_SAPPHIRE = MaterialExt.ITEMS.register("end_sapphire", ModItem::new);
    public static final RegistryObject<Item> FLAME_STONE = MaterialExt.ITEMS.register("flame_stone", ModItem::new);
    public static final RegistryObject<Item> MARINE_CRYSTAL = MaterialExt.ITEMS.register("marine_crystal", ModItem::new);
    public static final RegistryObject<Item> STRENGTH_PEARL = MaterialExt.ITEMS.register("strength_pearl", ModItem::new);
    public static final RegistryObject<Item> SUPER_CRYSTAL = MaterialExt.ITEMS.register("super_crystal", ModItem::new);
    public static final RegistryObject<Item> GUARDIAN_EYE = MaterialExt.ITEMS.register("guardian_eye", ModItem::new);
    public static final RegistryObject<Item> MINER_FIGURE = MaterialExt.ITEMS.register("miner_figure", ModItem::new);
    public static final RegistryObject<Item> ANCIENT_CANINE = MaterialExt.ITEMS.register("ancient_canine", ModItem::new);
    public static final RegistryObject<Item> EVOKER_AMULET = MaterialExt.ITEMS.register("evoker_amulet", ModItem::new);
    public static final RegistryObject<Item> NETHER_BARS = MaterialExt.ITEMS.register("nether_bars", ModItem::new);
    public static final RegistryObject<Item> ANCIENT_SCROLL = MaterialExt.ITEMS.register("ancient_scroll", ModItem::new);

    public static void initStatic()
    {
        // just to initialize static members
    }
}

