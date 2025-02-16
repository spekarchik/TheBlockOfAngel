package com.pekar.angelblock.tools;


import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
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

    static final Tier RENDELITHIC = new ModToolMaterial(800, 8F, 4.8F, 3, 25,
            Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get())); // damagePerSecond = 15, singleDamage = 8.8; Axe: damagePerSecond = 12, singleDamage = 10.8

    static final Tier DIAMITHIC = new ModToolMaterial(2600, 7F, 8.3F, 4, 14,
            Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get())); // damagePerSecond = 16, singleDamage = 12.3;  Axe: damagePerSecond = 11.4, singleDamage = 14.3

    static final Tier LAPIS = new ModToolMaterial(1500, 8.7F, 3F, 3, 10,
            Ingredient.of(ItemRegistry.LAPIS_INGOT.get())); // the same as Diamond (no weapon uses this material)

    static final Tier LIMONITE = new ModToolMaterial(1200, 6.8F, 3.1F, 3, 30,
            Ingredient.of(ItemRegistry.LIMONITE_INGOT.get())); // damagePerSecond = 14.9, singleDamage = 7.1;  Axe: damagePerSecond = 10, singleDamage = 9.1

    static final Tier SUPER = new ModToolMaterial(2600, 11.0F, 8.5F, 4, 1,
            Ingredient.of(ItemRegistry.SUPER_INGOT.get())); // damagePerSecond = 25, singleDamage = 12.5;  Axe: damagePerSecond = 20.3, singleDamage = 14.5

    static final Tier ROD_MATERIAL1 = new ModToolMaterial(60, 2.0F, 0F, 0, 0,
            Ingredient.of(Items.BAMBOO));

    static final Tier ROD_MATERIAL3 = new ModToolMaterial(200, 2.0F, 0F, 0, 0,
            Ingredient.of(Items.BAMBOO));

    static final Tier ROD_MATERIAL4 = new ModToolMaterial(500, 2.0F, 0F, 0, 0,
            Ingredient.of(Items.BAMBOO));

    static final Tier ROD_MATERIAL5 = new ModToolMaterial(2000, 2.0F, 0F, 0, 0,
            Ingredient.of(Items.BAMBOO));

    static final Tier ROD_MATERIAL6 = new ModToolMaterial(5000, 2.0F, 0F, 0, 0,
            Ingredient.of(Items.BAMBOO));

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
