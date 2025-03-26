package com.pekar.angelblock.tools;

import com.pekar.angelblock.Main;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;

public class ToolRegistry
{
    // Shovels
    public static final DeferredItem<ModShovel> RENDELITHIC_PRIMARY_SHOVEL = Main.ITEMS.registerItem("rendelithic_primary_shovel",
            p -> ModShovel.createPrimary(ToolMaterials.RENDELITHIC, 1.5F, -2.9F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModShovel> RENDELITHIC_SHOVEL = Main.ITEMS.registerItem("rendelithic_shovel",
            p -> new RendelithicShovel(ToolMaterials.RENDELITHIC, 1.5F, -2.9F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModShovel> LAPIS_PRIMARY_SHOVEL = Main.ITEMS.registerItem("lapis_primary_shovel",
            p -> ModShovel.createPrimary(ToolMaterials.LAPIS, 1.5F, -3.0F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModShovel> LAPIS_SHOVEL = Main.ITEMS.registerItem("lapis_shovel",
            p -> new LapisShovel(ToolMaterials.LAPIS, 1.5F, -3.0F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModShovel> SUPER_PRIMARY_SHOVEL = Main.ITEMS.registerItem("super_primary_shovel",
            p -> ModShovel.createPrimary(ToolMaterials.SUPER, 1.5F, -2.6F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModShovel> SUPER_SHOVEL = Main.ITEMS.registerItem("super_shovel",
            p -> new SuperShovel(ToolMaterials.SUPER, 1.5F, -2.6F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    // Pickaxes
    public static final DeferredItem<ModPickaxe> RENDELITHIC_PRIMARY_PICKAXE = Main.ITEMS.registerItem("rendelithic_primary_pickaxe",
            p -> ModPickaxe.createPrimary(ToolMaterials.RENDELITHIC, 1, -2.7F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModPickaxe> RENDELITHIC_PICKAXE = Main.ITEMS.registerItem("rendelithic_pickaxe",
            p -> new RendelithicPickaxe(ToolMaterials.RENDELITHIC, 1, -2.7F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModPickaxe> LAPIS_PRIMARY_PICKAXE = Main.ITEMS.registerItem("lapis_primary_pickaxe",
            p -> ModPickaxe.createPrimary(ToolMaterials.LAPIS, 1, -2.8F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModPickaxe> LAPIS_PICKAXE = Main.ITEMS.registerItem("lapis_pickaxe",
            p -> new LapisPickaxe(ToolMaterials.LAPIS, 1, -2.8F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModPickaxe> DIAMITHIC_PRIMARY_PICKAXE = Main.ITEMS.registerItem("diamithic_primary_pickaxe",
            p -> ModPickaxe.createPrimary(ToolMaterials.DIAMITHIC, 1, -3.0F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModPickaxe> DIAMITHIC_PICKAXE = Main.ITEMS.registerItem("diamithic_pickaxe",
            p -> new DiamithicPickaxe(ToolMaterials.DIAMITHIC, 1, -3.0F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModPickaxe> LIMONITE_PRIMARY_PICKAXE = Main.ITEMS.registerItem("limonite_primary_pickaxe",
            p -> ModPickaxe.createPrimary(ToolMaterials.LIMONITE, 1, -2.6F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModPickaxe> LIMONITE_PICKAXE = Main.ITEMS.registerItem("limonite_pickaxe",
            p -> new LimonitePickaxe(ToolMaterials.LIMONITE, 1, -2.6F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModPickaxe> SUPER_PRIMARY_PICKAXE = Main.ITEMS.registerItem("super_primary_pickaxe",
            p -> ModPickaxe.createPrimary(ToolMaterials.SUPER, 1, -2.4F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModPickaxe> SUPER_PICKAXE = Main.ITEMS.registerItem("super_pickaxe",
            p -> new SuperPickaxe(ToolMaterials.SUPER, 1, -2.4F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    // Axes
    public static final DeferredItem<ModAxe> DIAMITHIC_PRIMARY_AXE = Main.ITEMS.registerItem("diamithic_primary_axe",
            p -> ModAxe.createPrimary(ToolMaterials.DIAMITHIC, 5.0F, -3.2F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModAxe> DIAMITHIC_AXE = Main.ITEMS.registerItem("diamithic_axe",
            p -> new DiamithicAxe(ToolMaterials.DIAMITHIC, 5.0F, -3.2F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModAxe> LIMONITE_PRIMARY_AXE = Main.ITEMS.registerItem("limonite_primary_axe",
            p -> ModAxe.createPrimary(ToolMaterials.LIMONITE, 5.0F, -2.9F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModAxe> LIMONITE_AXE = Main.ITEMS.registerItem("limonite_axe",
            p -> new LimoniteAxe(ToolMaterials.LIMONITE, 5.0F, -2.9F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModAxe> SUPER_PRIMARY_AXE = Main.ITEMS.registerItem("super_primary_axe",
            p -> ModAxe.createPrimary(ToolMaterials.SUPER, 5.0F, -2.6F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModAxe> SUPER_AXE = Main.ITEMS.registerItem("super_axe",
            p -> new SuperAxe(ToolMaterials.SUPER, 5.0F, -2.6F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    // Hoes
    public static final DeferredItem<ModHoe> LAPIS_PRIMARY_HOE = Main.ITEMS.registerItem("lapis_primary_hoe",
            p -> ModHoe.createPrimary(ToolMaterials.LAPIS, -3, 0.5F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModHoe> LAPIS_HOE = Main.ITEMS.registerItem("lapis_hoe",
            p -> new LapisHoe(ToolMaterials.LAPIS, -3, 0.5F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    // Rods
    public static final DeferredItem<ModRod> ANCIENT_ROD = Main.ITEMS.registerItem("ancient_rod",
            p -> new AncientRod(ToolMaterials.ROD_MATERIAL1, false, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModRod> MARINE_ROD = Main.ITEMS.registerItem("marine_rod",
            p -> new MarineRod(ToolMaterials.ROD_MATERIAL2, false, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModRod> FIRE_ROD = Main.ITEMS.registerItem("fire_rod",
            p -> new FireRod(ToolMaterials.ROD_MATERIAL3, false, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModRod> AMETHYST_ROD = Main.ITEMS.registerItem("amethyst_rod",
            p -> new AmethystRod(ToolMaterials.ROD_MATERIAL4, false, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModRod> END_ROD = Main.ITEMS.registerItem("end_rod",
            p -> new EndRod(ToolMaterials.ROD_MATERIAL5, false, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModRod> ANCIENT_MAGNETIC_ROD = Main.ITEMS.registerItem("ancient_magnetic_rod",
            p -> new AncientRod(ToolMaterials.ROD_MATERIAL1, true, p), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<ModRod> MARINE_MAGNETIC_ROD = Main.ITEMS.registerItem("marine_magnetic_rod",
            p -> new MarineRod(ToolMaterials.ROD_MATERIAL2, true, p), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<ModRod> FIRE_MAGNETIC_ROD = Main.ITEMS.registerItem("fire_magnetic_rod",
            p -> new FireRod(ToolMaterials.ROD_MATERIAL3, true, p), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<ModRod> AMETHYST_MAGNETIC_ROD = Main.ITEMS.registerItem("amethyst_magnetic_rod",
            p -> new AmethystRod(ToolMaterials.ROD_MATERIAL4, true, p), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<ModRod> END_MAGNETIC_ROD = Main.ITEMS.registerItem("end_magnetic_rod",
            p -> new EndRod(ToolMaterials.ROD_MATERIAL5, true, p), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<ModRod> ANGEL_ROD = Main.ITEMS.registerItem("angel_rod",
            p -> new AngelRod(ToolMaterials.ROD_MATERIAL5, true, p), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<ModRod> PLANTER = Main.ITEMS.registerItem("planter",
            p -> new Planter(ToolMaterials.ROD_MATERIAL7, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModRod> TRACK_LAYER = Main.ITEMS.registerItem("track_layer",
            p -> new TrackLayer(ToolMaterials.ROD_MATERIAL6, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModRod> BUILDER = Main.ITEMS.registerItem("builder",
            p -> new Builder(ToolMaterials.ROD_MATERIAL7, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    // Swords
    public static final DeferredItem<ModSword> DIAMITHIC_PRIMARY_SWORD = Main.ITEMS.registerItem("diamithic_primary_sword",
            p -> new DiamithicPrimarySword(ToolMaterials.DIAMITHIC_SWORD, 3, -2.7F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModSword> DIAMITHIC_SWORD = Main.ITEMS.registerItem("diamithic_sword",
            p -> new DiamithicSword(ToolMaterials.DIAMITHIC_SWORD, 3, -2.7F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModSword> RENDELITHIC_PRIMARY_SWORD = Main.ITEMS.registerItem("rendelithic_primary_sword",
            p -> new RendelithicPrimarySword(ToolMaterials.RENDELITHIC_SWORD, 3, -2.3F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModSword> RENDELITHIC_SWORD = Main.ITEMS.registerItem("rendelithic_sword",
            p -> new RendelithicSword(ToolMaterials.RENDELITHIC_SWORD, 3, -2.3F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModSword> LIMONITE_PRIMARY_SWORD = Main.ITEMS.registerItem("limonite_primary_sword",
            p -> new LimonitePrimarySword(ToolMaterials.LIMONITE_SWORD, 3, -1.9F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModSword> LIMONITE_SWORD = Main.ITEMS.registerItem("limonite_sword",
            p -> new LimoniteSword(ToolMaterials.LIMONITE_SWORD, 3, -1.9F, p), new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredItem<ModSword> SUPER_PRIMARY_SWORD = Main.ITEMS.registerItem("super_primary_sword",
            p -> new SuperPrimarySword(ToolMaterials.SUPER_SWORD, 3, -2.0F, p), new Item.Properties().rarity(Rarity.COMMON));

    public static final DeferredItem<ModSword> SUPER_SWORD = Main.ITEMS.registerItem("super_sword",
            p -> new SuperSword(ToolMaterials.SUPER_SWORD, 3, -2.0F, p), new Item.Properties().rarity(Rarity.UNCOMMON));


    public static void initStatic()
    {
        // just to initialize static members
    }
}
