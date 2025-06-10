package com.pekar.angelblock.tools;


import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ToolMaterials
{
    // stone: 1	131	4F	1	5
    // iron: 2	250	6F	2	14
    // diamond: 3, 1561, 8F, 3, 10
    // netherite : 4, 2031, 9F, 4, 15
    // gold: 0, 32, 12F, 0.0F, 22

    // pickaxe - efficiency (speed) only
    // sword - damage only
    // all swords attackSpeed = 4 - 2.4 = 1.6
    // all axes: 4 - 3.0 = 1.0
    // (damagePerSecond = 4 + attackDamageBonus) * (4 - attackSpeed)
    // iron sword: 9.6, singleDamage = 6.0
    // diamond sword: 11.2, singleDamage = 7.0
    // netherite sword: 12.8, singleDamage = 8.0
    // iron axe: 8.0, singleDamage = 8.0
    // diamond axe: 9.0, singleDamage = 9.0
    // netherite axe: 10.0, singleDamage = 10.0

    public static final String RENDELITHIC_MATERIAL_NAME = "rendelithic";
    public static final String LIMONITE_MATERIAL_NAME = "limonite";
    public static final String DIAMITHIC_MATERIAL_NAME = "diamithic";
    public static final String LAPIS_MATERIAL_NAME = "lapis";
    public static final String SUPER_MATERIAL_NAME = "super";
    public static final String ROD_MATERIAL1_NAME = "rod1";
    public static final String ROD_MATERIAL2_NAME = "rod2";
    public static final String ROD_MATERIAL3_NAME = "rod3";
    public static final String ROD_MATERIAL4_NAME = "rod4";
    public static final String ROD_MATERIAL5_NAME = "rod5";
    public static final String ROD_MATERIAL6_NAME = "rod6";
    public static final String ROD_MATERIAL7_NAME = "rod7";

    static final ModToolMaterial RENDELITHIC = new ModToolMaterial(RENDELITHIC_MATERIAL_NAME, 600, 8F, 4.8F, 3, 25,
            Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get())); // damagePerSecond = 15, singleDamage = 8.8; Axe: damagePerSecond = 12, singleDamage = 10.8
    
    static final ModToolMaterial RENDELITHIC_SWORD = RENDELITHIC.clone(RENDELITHIC_MATERIAL_NAME, 150);

    static final ModToolMaterial DIAMITHIC = new ModToolMaterial(DIAMITHIC_MATERIAL_NAME, 2100, 7F, 8.3F, 4, 14,
            Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get())); // damagePerSecond = 16, singleDamage = 12.3;  Axe: damagePerSecond = 11.4, singleDamage = 14.3

    static final ModToolMaterial DIAMITHIC_SWORD = DIAMITHIC.clone(DIAMITHIC_MATERIAL_NAME, 525);

    static final ModToolMaterial LAPIS = new ModToolMaterial(LAPIS_MATERIAL_NAME, 1500, 8.7F, 3F, 3, 10,
            Ingredient.of(ItemRegistry.LAPIS_INGOT.get())); // the same as Diamond (no weapon uses this material)

    static final ModToolMaterial LAPIS_SWORD = LAPIS.clone(LAPIS_MATERIAL_NAME, 375);

    static final ModToolMaterial LIMONITE = new ModToolMaterial(LIMONITE_MATERIAL_NAME, 1000, 6.8F, 3.1F, 3, 30,
            Ingredient.of(ItemRegistry.LIMONITE_INGOT.get())); // damagePerSecond = 14.9, singleDamage = 7.1;  Axe: damagePerSecond = 10, singleDamage = 9.1

    static final ModToolMaterial LIMONITE_SWORD = LIMONITE.clone(LIMONITE_MATERIAL_NAME, 250);

    static final ModToolMaterial SUPER = new ModToolMaterial(SUPER_MATERIAL_NAME, 2100, 11.0F, 8.5F, 4, 1,
            Ingredient.of(ItemRegistry.SUPER_INGOT.get())); // damagePerSecond = 25, singleDamage = 12.5;  Axe: damagePerSecond = 20.3, singleDamage = 14.5

    static final ModToolMaterial SUPER_SWORD = SUPER.clone(SUPER_MATERIAL_NAME, 525);

    static final ModToolMaterial ROD_MATERIAL1 = new ModToolMaterial(ROD_MATERIAL1_NAME, 60, 2.0F, 0F, 0, 1,
            Ingredient.of(Items.BAMBOO));

    static final ModToolMaterial ROD_MATERIAL2 = ROD_MATERIAL1.clone(ROD_MATERIAL2_NAME, 120);

    static final ModToolMaterial ROD_MATERIAL3 = ROD_MATERIAL1.clone(ROD_MATERIAL3_NAME, 200);

    static final ModToolMaterial ROD_MATERIAL4 = ROD_MATERIAL1.clone(ROD_MATERIAL4_NAME, 500);

    static final ModToolMaterial ROD_MATERIAL5 = ROD_MATERIAL1.clone(ROD_MATERIAL5_NAME, 1000);

    static final ModToolMaterial ROD_MATERIAL6 = ROD_MATERIAL1.clone(ROD_MATERIAL6_NAME, 2000);

    static final ModToolMaterial ROD_MATERIAL7 = ROD_MATERIAL1.clone(ROD_MATERIAL7_NAME, 5000);

//    static final Tier RENDELITHIC = EnumHelper
//            .addToolMaterial("materialext:rendelithic_tool", 3, 2200, 7, 3, 15)
//            .setRepairItem(new ItemStack(ItemRegistry.RENDELITHIC_INGOT));
//
//    static final Tier DIAMITHIC = EnumHelper
//            .addToolMaterial("materialext:diamithic_tool", 4, 2600, 11, 4.8F, 15)
//            .setRepairItem(new ItemStack(ItemRegistry.DIAMITHIC_INGOT));
//
//    static final Tier LAPIS = EnumHelper
//            .addToolMaterial("materialext:lapis_tool", 4, 2000, 9, 4, 15)
//            .setRepairItem(new ItemStack(ItemRegistry.LAPIS_INGOT));
//
//    static final Tier LIMONITE = EnumHelper
//            .addToolMaterial("materialext:limonite_tool", 4, 1800, 8, 3, 15)
//            .setRepairItem(new ItemStack(ItemRegistry.LIMONITE_INGOT));
//
//    static final Tier SUPER = EnumHelper
//            .addToolMaterial("materialext:super_tool", 4, 3000, 11, 5, 15)
//            .setRepairItem(new ItemStack(ItemRegistry.SUPER_INGOT));
}
