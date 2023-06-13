package com.pekar.angelblock.tools;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.tab.ModTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

public class ToolRegistry
{
    // Shovels
    public static final RegistryObject<ModShovel> RENDELITHIC_PRIMARY_SHOVEL = Main.ITEMS.register("rendelithic_primary_shovel",
            () -> ModShovel.createPrimary(ToolMaterials.RENDELITHIC, 1.5F, -2.8F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModShovel> RENDELITHIC_SHOVEL = Main.ITEMS.register("rendelithic_shovel",
            () -> new RendelithicShovel(ToolMaterials.RENDELITHIC, 1.5F, -2.8F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModShovel> LAPIS_PRIMARY_SHOVEL = Main.ITEMS.register("lapis_primary_shovel",
            () -> ModShovel.createPrimary(ToolMaterials.LAPIS, 1.5F, -3.0F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModShovel> LAPIS_SHOVEL = Main.ITEMS.register("lapis_shovel",
            () -> new LapisShovel(ToolMaterials.LAPIS, 1.5F, -3.0F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModShovel> SUPER_PRIMARY_SHOVEL = Main.ITEMS.register("super_primary_shovel",
            () -> ModShovel.createPrimary(ToolMaterials.SUPER, 1.5F, -2.6F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModShovel> SUPER_SHOVEL = Main.ITEMS.register("super_shovel",
            () -> new SuperShovel(ToolMaterials.SUPER, 1.5F, -2.6F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    // Pickaxes
    public static final RegistryObject<ModPickaxe> RENDELITHIC_PRIMARY_PICKAXE = Main.ITEMS.register("rendelithic_primary_pickaxe",
            () -> ModPickaxe.createPrimary(ToolMaterials.RENDELITHIC, 1, -2.4F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModPickaxe> RENDELITHIC_PICKAXE = Main.ITEMS.register("rendelithic_pickaxe",
            () -> new RendelithicPickaxe(ToolMaterials.RENDELITHIC, 1, -2.4F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModPickaxe> LAPIS_PRIMARY_PICKAXE = Main.ITEMS.register("lapis_primary_pickaxe",
            () -> ModPickaxe.createPrimary(ToolMaterials.LAPIS, 1, -2.8F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModPickaxe> LAPIS_PICKAXE = Main.ITEMS.register("lapis_pickaxe",
            () -> new LapisPickaxe(ToolMaterials.LAPIS, 1, -2.8F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModPickaxe> DIAMITHIC_PRIMARY_PICKAXE = Main.ITEMS.register("diamithic_primary_pickaxe",
            () -> ModPickaxe.createPrimary(ToolMaterials.DIAMITHIC, 1, -2.8F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModPickaxe> DIAMITHIC_PICKAXE = Main.ITEMS.register("diamithic_pickaxe",
            () -> new DiamithicPickaxe(ToolMaterials.DIAMITHIC, 1, -2.8F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModPickaxe> LIMONITE_PRIMARY_PICKAXE = Main.ITEMS.register("limonite_primary_pickaxe",
            () -> ModPickaxe.createPrimary(ToolMaterials.LIMONITE, 1, -2.2F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModPickaxe> LIMONITE_PICKAXE = Main.ITEMS.register("limonite_pickaxe",
            () -> new LimonitePickaxe(ToolMaterials.LIMONITE, 1, -2.2F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModPickaxe> SUPER_PRIMARY_PICKAXE = Main.ITEMS.register("super_primary_pickaxe",
            () -> ModPickaxe.createPrimary(ToolMaterials.SUPER, 1, -2.2F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModPickaxe> SUPER_PICKAXE = Main.ITEMS.register("super_pickaxe",
            () -> new SuperPickaxe(ToolMaterials.SUPER, 1, -2.2F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    // Axes
    public static final RegistryObject<ModAxe> DIAMITHIC_PRIMARY_AXE = Main.ITEMS.register("diamithic_primary_axe",
            () -> ModAxe.createPrimary(ToolMaterials.DIAMITHIC, 5.0F, -3.0F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModAxe> DIAMITHIC_AXE = Main.ITEMS.register("diamithic_axe",
            () -> new DiamithicAxe(ToolMaterials.DIAMITHIC, 5.0F, -3.0F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModAxe> LIMONITE_PRIMARY_AXE = Main.ITEMS.register("limonite_primary_axe",
            () -> ModAxe.createPrimary(ToolMaterials.LIMONITE, 5.0F, -2.5F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModAxe> LIMONITE_AXE = Main.ITEMS.register("limonite_axe",
            () -> new LimoniteAxe(ToolMaterials.LIMONITE, 5.0F, -2.5F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModAxe> SUPER_PRIMARY_AXE = Main.ITEMS.register("super_primary_axe",
            () -> ModAxe.createPrimary(ToolMaterials.SUPER, 5.0F, -2.5F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModAxe> SUPER_AXE = Main.ITEMS.register("super_axe",
            () -> new SuperAxe(ToolMaterials.SUPER, 5.0F, -2.5F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    // Hoes
    public static final RegistryObject<ModHoe> LAPIS_PRIMARY_HOE = Main.ITEMS.register("lapis_primary_hoe",
            () -> ModHoe.createPrimary(ToolMaterials.LAPIS, -3, 0.5F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModHoe> LAPIS_HOE = Main.ITEMS.register("lapis_hoe",
            () -> new LapisHoe(ToolMaterials.LAPIS, -3, 0.5F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    // Rods
    public static final RegistryObject<ModRod> ANCIENT_ROD = Main.ITEMS.register("ancient_rod",
            () -> new AncientRod(ToolMaterials.ROD_MATERIAL1, 0, -3F, false,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModRod> MARINE_ROD = Main.ITEMS.register("marine_rod",
            () -> new MarineRod(ToolMaterials.ROD_MATERIAL1, 0, -3F, false,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModRod> FIRE_ROD = Main.ITEMS.register("fire_rod",
            () -> new FireRod(ToolMaterials.ROD_MATERIAL3, 0, -3F, false,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModRod> AMETHYST_ROD = Main.ITEMS.register("amethyst_rod",
            () -> new AmethystRod(ToolMaterials.ROD_MATERIAL4, 0, -3F, false,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModRod> END_ROD = Main.ITEMS.register("end_rod",
            () -> new EndRod(ToolMaterials.ROD_MATERIAL5, 0, -3F, false,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModRod> ANCIENT_MAGNETIC_ROD = Main.ITEMS.register("ancient_magnetic_rod",
            () -> new AncientRod(ToolMaterials.ROD_MATERIAL1, 0, -3F, true,
                    new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<ModRod> MARINE_MAGNETIC_ROD = Main.ITEMS.register("marine_magnetic_rod",
            () -> new MarineRod(ToolMaterials.ROD_MATERIAL1, 0, -3F, true,
                    new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<ModRod> FIRE_MAGNETIC_ROD = Main.ITEMS.register("fire_magnetic_rod",
            () -> new FireRod(ToolMaterials.ROD_MATERIAL3, 0, -3F, true,
                    new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<ModRod> AMETHYST_MAGNETIC_ROD = Main.ITEMS.register("amethyst_magnetic_rod",
            () -> new AmethystRod(ToolMaterials.ROD_MATERIAL4, 0, -3F, true,
                    new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<ModRod> END_MAGNETIC_ROD = Main.ITEMS.register("end_magnetic_rod",
            () -> new EndRod(ToolMaterials.ROD_MATERIAL5, 0, -3F, true,
                    new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<ModRod> PLANTER = Main.ITEMS.register("planter",
            () -> new Planter(ToolMaterials.ROD_MATERIAL6, 0, -3F, false,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModRod> TRACK_LAYER = Main.ITEMS.register("track_layer",
            () -> new TrackLayer(ToolMaterials.ROD_MATERIAL5, 0, -3F, false,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModRod> BUILDER = Main.ITEMS.register("builder",
            () -> new Builder(ToolMaterials.ROD_MATERIAL6, 0, -3F, false,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    // Swords
    public static final RegistryObject<ModSword> DIAMITHIC_PRIMARY_SWORD = Main.ITEMS.register("diamithic_primary_sword",
            () -> new DiamithicPrimarySword(ToolMaterials.DIAMITHIC, 3, -2.4F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModSword> DIAMITHIC_SWORD = Main.ITEMS.register("diamithic_sword",
            () -> new DiamithicSword(ToolMaterials.DIAMITHIC, 3, -2.4F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModSword> RENDELITHIC_PRIMARY_SWORD = Main.ITEMS.register("rendelithic_primary_sword",
            () -> new RendelithicPrimarySword(ToolMaterials.RENDELITHIC, 3, -2.2F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModSword> RENDELITHIC_SWORD = Main.ITEMS.register("rendelithic_sword",
            () -> new RendelithicSword(ToolMaterials.RENDELITHIC, 3, -2.2F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModSword> LIMONITE_PRIMARY_SWORD = Main.ITEMS.register("limonite_primary_sword",
            () -> new LimonitePrimarySword(ToolMaterials.LIMONITE, 3, -1.9F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModSword> LIMONITE_SWORD = Main.ITEMS.register("limonite_sword",
            () -> new LimoniteSword(ToolMaterials.LIMONITE, 3, -1.9F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ModSword> SUPER_PRIMARY_SWORD = Main.ITEMS.register("super_primary_sword",
            () -> new SuperPrimarySword(ToolMaterials.SUPER, 3, -1.9F,
                    new Item.Properties().rarity(Rarity.COMMON)));

    public static final RegistryObject<ModSword> SUPER_SWORD = Main.ITEMS.register("super_sword",
            () -> new SuperSword(ToolMaterials.SUPER, 3, -1.9F,
                    new Item.Properties().rarity(Rarity.UNCOMMON)));

    // + морской кристалл - Marine Crystal - древний страж 50%
    // + огненный камень - Flame Stone - сундуки крепости 10%
    // + жемчужина силы - Pearl of Strength - заброшенная шахта
    // + зеленый алмаз жизни - Green Diamond of Bios - алмазная руда + древний посох = зеленая алмазная руда
    // + кристалл Края - End Crystal - сундуки города Края
    // + супер кристалл - Super Crystal - крафт
    // + древний посох - Ancient Rod - храм в джунглях 10%

    public static void initStatic()
    {
        // just to initialize static members
    }
}
