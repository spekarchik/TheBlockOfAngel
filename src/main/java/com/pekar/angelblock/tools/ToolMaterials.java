package com.pekar.angelblock.tools;


import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class ToolMaterials
{
    // stone: 1	131	4F	1	5
    // iron: 2	250	6F	2	14
    // diamond: 3, 1561, 8F, 3, 10
    // netherite : 4, 2031, 9F, 4, 15
    // gold: 0, 32, 12F, 0.0F, 22

    // pickaxe - efficiency only
    // sword - damage only

    static final Tier RENDELITHIC = new ModToolMaterial(1300, 7.4F, 4.4F, 3, 17,
            Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get()));

    static final Tier DIAMITHIC = new ModToolMaterial(2600, 10.5F, 5.5F, 4, 14,
            Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get()));

    static final Tier LAPIS = new ModToolMaterial(2000, 8.7F, 3.5F, 3, 12,
            Ingredient.of(ItemRegistry.LAPIS_INGOT.get()));

    static final Tier LIMONITE = new ModToolMaterial(900, 6.8F, 2.8F, 2, 30,
            Ingredient.of(ItemRegistry.LIMONITE_INGOT.get()));

    static final Tier SUPER = new ModToolMaterial(3000, 11.0F, 5.8F, 4, 25,
            Ingredient.of(ItemRegistry.SUPER_INGOT.get()));

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
