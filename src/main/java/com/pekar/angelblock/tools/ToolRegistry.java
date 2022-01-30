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
            () -> new ModShovel(ToolMaterials.RENDELITHIC, 1.5F, -3.0F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<ModShovel> RENDELITHIC_SHOVEL = Main.ITEMS.register("rendelithic_shovel",
            () -> new RendelithicShovel(ToolMaterials.RENDELITHIC, 1.5F, -3.0F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.RARE)));

    public static final RegistryObject<ModShovel> LAPIS_PRIMARY_SHOVEL = Main.ITEMS.register("lapis_primary_shovel",
            () -> new ModShovel(ToolMaterials.LAPIS, 1.5F, -3.0F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<ModShovel> LAPIS_SHOVEL = Main.ITEMS.register("lapis_shovel",
            () -> new LapisShovel(ToolMaterials.LAPIS, 1.5F, -3.0F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.RARE)));

    public static final RegistryObject<ModShovel> SUPER_PRIMARY_SHOVEL = Main.ITEMS.register("super_primary_shovel",
            () -> new ModShovel(ToolMaterials.SUPER, 1.5F, -3.0F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<ModShovel> SUPER_SHOVEL = Main.ITEMS.register("super_shovel",
            () -> new SuperShovel(ToolMaterials.SUPER, 1.5F, -3.0F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.RARE)));

    // Pickaxes
    public static final RegistryObject<ModPickaxe> RENDELITHIC_PRIMARY_PICKAXE = Main.ITEMS.register("rendelithic_primary_pickaxe",
            () -> new ModPickaxe(ToolMaterials.RENDELITHIC, 1, -2.8F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<ModPickaxe> RENDELITHIC_PICKAXE = Main.ITEMS.register("rendelithic_pickaxe",
            () -> new RendelithicPickaxe(ToolMaterials.RENDELITHIC, 1, -2.8F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.RARE)));

    public static final RegistryObject<ModPickaxe> LAPIS_PRIMARY_PICKAXE = Main.ITEMS.register("lapis_primary_pickaxe",
            () -> new ModPickaxe(ToolMaterials.LAPIS, 1, -2.8F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<ModPickaxe> LAPIS_PICKAXE = Main.ITEMS.register("lapis_pickaxe",
            () -> new LapisPickaxe(ToolMaterials.LAPIS, 1, -2.8F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.RARE)));

    public static final RegistryObject<ModPickaxe> DIAMITHIC_PRIMARY_PICKAXE = Main.ITEMS.register("diamithic_primary_pickaxe",
            () -> new ModPickaxe(ToolMaterials.DIAMITHIC, 1, -2.8F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<ModPickaxe> DIAMITHIC_PICKAXE = Main.ITEMS.register("diamithic_pickaxe",
            () -> new DiamithicPickaxe(ToolMaterials.DIAMITHIC, 1, -2.8F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.RARE)));

    public static final RegistryObject<ModPickaxe> LIMONITE_PRIMARY_PICKAXE = Main.ITEMS.register("limonite_primary_pickaxe",
            () -> new ModPickaxe(ToolMaterials.LIMONITE, 1, -2.8F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<ModPickaxe> LIMONITE_PICKAXE = Main.ITEMS.register("limonite_pickaxe",
            () -> new LimonitePickaxe(ToolMaterials.LIMONITE, 1, -2.8F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.RARE)));

    public static final RegistryObject<ModPickaxe> SUPER_PRIMARY_PICKAXE = Main.ITEMS.register("super_primary_pickaxe",
            () -> new ModPickaxe(ToolMaterials.SUPER, 1, -2.8F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<ModPickaxe> SUPER_PICKAXE = Main.ITEMS.register("super_pickaxe",
            () -> new SuperPickaxe(ToolMaterials.SUPER, 1, -2.8F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.RARE)));

    // Axes
    public static final RegistryObject<ModAxe> DIAMITHIC_PRIMARY_AXE = Main.ITEMS.register("diamithic_primary_axe",
            () -> new ModAxe(ToolMaterials.DIAMITHIC, 5.0F, -3.0F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.COMMON)));

    public static final RegistryObject<ModAxe> DIAMITHIC_AXE = Main.ITEMS.register("diamithic_axe",
            () -> new DiamithicAxe(ToolMaterials.DIAMITHIC, 5.0F, -3.0F,
                    new Item.Properties().tab(ModTab.MOD_TAB).rarity(Rarity.RARE)));

//    public static final Item DIAMITHIC_PRIMARY_SWORD = new ModSword("diamithic_primary_sword", ToolMaterials.DIAMITHIC);
//    public static final Item DIAMITHIC_SWORD = new DiamithicSword("diamithic_sword", ToolMaterials.DIAMITHIC);
//    public static final Item LIMONITE_PRIMARY_SWORD = new ModSword("limonite_primary_sword", ToolMaterials.LIMONITE);
//    public static final Item LIMONITE_SWORD = new LimoniteSword("limonite_sword", ToolMaterials.LIMONITE);
//    public static final Item RENDELITHIC_PRIMARY_SWORD = new ModSword("rendelithic_primary_sword", ToolMaterials.RENDELITHIC);
//    public static final Item RENDELITHIC_SWORD = new RendelithicSword("rendelithic_sword", ToolMaterials.RENDELITHIC);
//    public static final Item SUPER_PRIMARY_SWORD = new ModSword("super_primary_sword", ToolMaterials.SUPER);
//    public static final Item SUPER_SWORD = new SuperSword("super_sword", ToolMaterials.SUPER);
//
//    public static final Item LAPIS_PRIMARY_HOE = new ModHoe("lapis_primary_hoe", ToolMaterials.LAPIS);
//    public static final Item LAPIS_HOE = new LapisHoe("lapis_hoe", ToolMaterials.LAPIS);
//
//    public static final Item DIAMITHIC_PRIMARY_AXE = new ModAxe("diamithic_primary_axe", ToolMaterials.DIAMITHIC, 9, -3.2F);
//    public static final Item DIAMITHIC_AXE = new DiamithicAxe("diamithic_axe", ToolMaterials.DIAMITHIC);
//    public static final Item LIMONITE_PRIMARY_AXE = new ModAxe("limonite_primary_axe", ToolMaterials.LIMONITE, 7, -2.9F);
//    public static final Item LIMONITE_AXE = new LimoniteAxe("limonite_axe", ToolMaterials.LIMONITE);
//    public static final Item SUPER_PRIMARY_AXE = new ModAxe("super_primary_axe", ToolMaterials.SUPER, 9, -2.9F);
//    public static final Item SUPER_AXE = new SuperAxe("super_axe", ToolMaterials.SUPER);
//
//    public static final Item ANCIENT_ROD = new AncientRod("ancient_rod", Item.ToolMaterial.WOOD);

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
