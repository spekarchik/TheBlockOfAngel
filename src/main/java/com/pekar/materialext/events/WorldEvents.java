package com.pekar.materialext.events;

import com.pekar.materialext.MaterialExt;
import com.pekar.materialext.armor.ArmorRegistry;
import com.pekar.materialext.armor.ModArmor;
import com.pekar.materialext.items.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldEvents implements IEventHandler
{
    private static final int ELYTRA_REPAIR_AMOUNT = 100;
    private static final int SHIELD_REPAIR_AMOUNT = 100;
    private static final int BOW_REPAIR_AMOUNT = 70;
    private static final int FISHING_ROD_REPAIR_AMOUNT = 16;
    private static final int FLINT_AND_STEEL_REPAIR_AMOUNT = 15;
    private static final int TOOL_ENHANCEMENT_COST = 15;
    private static final int REPAIR_COST = 2;

    //@SubscribeEvent
    public void onLootTableLoadEvent(LootTableLoadEvent event)
    {
//        if (LootTableList.CHESTS_SPAWN_BONUS_CHEST.equals(event.getName()))
//        {
//            replaceTable(event, "chests/stronghold_library_override");
//        }
//        if (LootTableList.CHESTS_VILLAGE_BLACKSMITH.equals(event.getName()))
//        {
//            replaceTable(event, "chests/village_blacksmith_override");
//        }
//        else if (LootTableList.CHESTS_DESERT_PYRAMID.equals(event.getName()))
//        {
//            replaceTable(event, "chests/desert_pyramid_override");
//        }
//        else if (LootTableList.CHESTS_JUNGLE_TEMPLE.equals(event.getName()))
//        {
//            replaceTable(event, "chests/jungle_temple_override");
//        }
//        else if (LootTableList.CHESTS_WOODLAND_MANSION.equals(event.getName()))
//        {
//            replaceTable(event, "chests/woodland_mansion_override");
//        }
//        else if (LootTableList.CHESTS_NETHER_BRIDGE.equals(event.getName()))
//        {
//            replaceTable(event, "chests/nether_bridge_override");
//        }
//        else if (LootTableList.CHESTS_ABANDONED_MINESHAFT.equals(event.getName()))
//        {
//            replaceTable(event, "chests/abandoned_mineshaft_override");
//        }
//        else if (LootTableList.CHESTS_SIMPLE_DUNGEON.equals(event.getName()))
//        {
//            replaceTable(event, "chests/simple_dungeon_override");
//        }
//        else if (LootTableList.CHESTS_END_CITY_TREASURE.equals(event.getName()))
//        {
//            replaceTable(event, "chests/end_city_treasure_override");
//        }
//        else if (LootTableList.CHESTS_IGLOO_CHEST.equals(event.getName()))
//        {
//            replaceTable(event, "chests/igloo_chest_override");
//        }
//        else if (LootTableList.CHESTS_STRONGHOLD_LIBRARY.equals(event.getName()))
//        {
//            replaceTable(event, "chests/stronghold_library_override");
//        }
    }

    @SubscribeEvent
    public void onAnvilUpdateEvent(AnvilUpdateEvent event)
    {
//        Item rightSlotItem = event.getRight().getItem();
//        ItemStack left = event.getLeft();
//        Item leftSlotItem = left.getItem();
//
//        if (rightSlotItem == Items.STICK)
//        {
//            if (leftSlotItem == ToolRegistry.RENDELITHIC_PICKAXE || leftSlotItem == ToolRegistry.RENDELITHIC_SHOVEL
//                    || leftSlotItem == ToolRegistry.RENDELITHIC_SWORD)
//            {
//                event.setOutput(new ItemStack(ItemRegistry.FLAME_STONE));
//                event.setCost(1);
//                return;
//            }
//
//            if (leftSlotItem == ToolRegistry.DIAMITHIC_SWORD || leftSlotItem == ToolRegistry.DIAMITHIC_AXE
//                    || leftSlotItem == ToolRegistry.DIAMITHIC_PICKAXE)
//            {
//                event.setOutput(new ItemStack(ItemRegistry.STRENGTH_PEARL));
//                event.setCost(1);
//                return;
//            }
//
//            if (leftSlotItem == ToolRegistry.LIMONITE_AXE || leftSlotItem == ToolRegistry.LIMONITE_PICKAXE
//                    || leftSlotItem == ToolRegistry.LIMONITE_SWORD)
//            {
//                event.setOutput(new ItemStack(ItemRegistry.BIOS_DIAMOND));
//                event.setCost(1);
//                return;
//            }
//
//            if (leftSlotItem == ToolRegistry.LAPIS_HOE || leftSlotItem == ToolRegistry.LAPIS_PICKAXE
//                    || leftSlotItem == ToolRegistry.LAPIS_SHOVEL)
//            {
//                event.setOutput(new ItemStack(ItemRegistry.MARINE_CRYSTAL));
//                event.setCost(1);
//                return;
//            }
//
//            if (leftSlotItem == ToolRegistry.SUPER_SWORD || leftSlotItem == ToolRegistry.SUPER_AXE
//                    || leftSlotItem == ToolRegistry.SUPER_SHOVEL || leftSlotItem == ToolRegistry.SUPER_PICKAXE)
//            {
//                event.setOutput(new ItemStack(ItemRegistry.SUPER_CRYSTAL));
//                event.setCost(1);
//                return;
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == ItemRegistry.RENDELITHIC_INGOT)
//        {
//            if (leftSlotItem instanceof IModTool)
//            {
//                IModTool tool = (IModTool)leftSlotItem;
//                if (tool.canBeRepairedWith(ItemRegistry.RENDELITHIC_INGOT))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == ArmorRegistry.RENDEL_LEGGINGS || leftSlotItem == ArmorRegistry.RENDEL_BOOTS
//                    || leftSlotItem == ArmorRegistry.RENDEL_CHESTPLATE || leftSlotItem == ArmorRegistry.RENDEL_HELMET)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == ItemRegistry.LIMONITE_INGOT)
//        {
//            if (leftSlotItem instanceof IModTool)
//            {
//                IModTool tool = (IModTool)leftSlotItem;
//                if (tool.canBeRepairedWith(ItemRegistry.LIMONITE_INGOT))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == ArmorRegistry.LIMONITE_LEGGINGS || leftSlotItem == ArmorRegistry.LIMONITE_BOOTS
//                    || leftSlotItem == ArmorRegistry.LIMONITE_CHESTPLATE || leftSlotItem == ArmorRegistry.LIMONITE_HELMET)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == ItemRegistry.LAPIS_INGOT)
//        {
//            if (leftSlotItem instanceof IModTool)
//            {
//                IModTool tool = (IModTool)leftSlotItem;
//                if (tool.canBeRepairedWith(ItemRegistry.LAPIS_INGOT))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == ArmorRegistry.LAPIS_LEGGINGS || leftSlotItem == ArmorRegistry.LAPIS_BOOTS
//                    || leftSlotItem == ArmorRegistry.LAPIS_CHESTPLATE || leftSlotItem == ArmorRegistry.LAPIS_HELMET)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == ItemRegistry.DIAMITHIC_INGOT)
//        {
//            if (leftSlotItem instanceof IModTool)
//            {
//                IModTool tool = (IModTool)leftSlotItem;
//                if (tool.canBeRepairedWith(ItemRegistry.DIAMITHIC_INGOT))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == ArmorRegistry.DIAMITHIC_LEGGINGS || leftSlotItem == ArmorRegistry.DIAMITHIC_BOOTS
//                    || leftSlotItem == ArmorRegistry.DIAMITHIC_CHESTPLATE || leftSlotItem == ArmorRegistry.DIAMITHIC_HELMET)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == ItemRegistry.SUPER_INGOT)
//        {
//            if (leftSlotItem instanceof IModTool)
//            {
//                IModTool tool = (IModTool)leftSlotItem;
//                if (tool.canBeRepairedWith(ItemRegistry.SUPER_INGOT))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == ArmorRegistry.SUPER_LEGGINGS || leftSlotItem == ArmorRegistry.SUPER_BOOTS
//                    || leftSlotItem == ArmorRegistry.SUPER_CHESTPLATE || leftSlotItem == ArmorRegistry.SUPER_HELMET)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Items.DIAMOND)
//        {
//            if (leftSlotItem instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)leftSlotItem;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.DIAMOND.toString()))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == Items.DIAMOND_LEGGINGS || leftSlotItem == Items.DIAMOND_BOOTS
//                    || leftSlotItem == Items.DIAMOND_CHESTPLATE || leftSlotItem == Items.DIAMOND_HELMET
//                    || leftSlotItem == Items.DIAMOND_SWORD || leftSlotItem == Items.DIAMOND_PICKAXE
//                    || leftSlotItem == Items.DIAMOND_HOE || leftSlotItem == Items.DIAMOND_SHOVEL
//                    || leftSlotItem == Items.DIAMOND_AXE)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Items.IRON_INGOT)
//        {
//            if (leftSlotItem instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)leftSlotItem;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.IRON.toString()))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == Items.IRON_LEGGINGS || leftSlotItem == Items.IRON_BOOTS
//                    || leftSlotItem == Items.IRON_CHESTPLATE || leftSlotItem == Items.IRON_HELMET
//                    || leftSlotItem == Items.IRON_SWORD || leftSlotItem == Items.SHEARS
//                    || leftSlotItem == Items.IRON_AXE || leftSlotItem == Items.IRON_PICKAXE
//                    || leftSlotItem == Items.IRON_HOE || leftSlotItem == Items.IRON_SHOVEL
//                    || leftSlotItem == Items.CHAINMAIL_LEGGINGS || leftSlotItem == Items.CHAINMAIL_BOOTS
//                    || leftSlotItem == Items.CHAINMAIL_CHESTPLATE || leftSlotItem == Items.CHAINMAIL_HELMET)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Items.GOLD_INGOT)
//        {
//            if (leftSlotItem instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)leftSlotItem;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.GOLD.toString()))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == Items.GOLDEN_LEGGINGS || leftSlotItem == Items.GOLDEN_BOOTS
//                    || leftSlotItem == Items.GOLDEN_CHESTPLATE || leftSlotItem == Items.GOLDEN_HELMET
//                    || leftSlotItem == Items.GOLDEN_SWORD || leftSlotItem == Items.GOLDEN_PICKAXE
//                    || leftSlotItem == Items.GOLDEN_HOE || leftSlotItem == Items.GOLDEN_SHOVEL
//                    || leftSlotItem == Items.GOLDEN_AXE)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Items.LEATHER)
//        {
//            if (leftSlotItem == Items.LEATHER_LEGGINGS || leftSlotItem == Items.LEATHER_BOOTS
//                    || leftSlotItem == Items.LEATHER_CHESTPLATE || leftSlotItem == Items.LEATHER_HELMET
//                    || leftSlotItem == Items.ELYTRA)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Item.getItemFromBlock(Blocks.COBBLESTONE))
//        {
//            if (leftSlotItem instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)leftSlotItem;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.STONE.toString()))
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == Items.STONE_AXE || leftSlotItem == Items.STONE_PICKAXE
//                    || leftSlotItem == Items.STONE_HOE || leftSlotItem == Items.STONE_SHOVEL
//                    || leftSlotItem == Items.STONE_SWORD)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Item.getItemFromBlock(Blocks.PLANKS))
//        {
//            if (leftSlotItem instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)leftSlotItem;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.WOOD.toString())
//                    && leftSlotItem != ToolRegistry.ANCIENT_ROD)
//                {
//                    event.setOutput(left);
//                    event.setCost(REPAIR_COST);
//                }
//            }
//            else if (leftSlotItem == Items.WOODEN_AXE || leftSlotItem == Items.WOODEN_PICKAXE
//                    || leftSlotItem == Items.WOODEN_HOE || leftSlotItem == Items.WOODEN_SHOVEL
//                    || leftSlotItem == Items.WOODEN_SWORD || leftSlotItem == Items.SHIELD)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Item.getItemFromBlock(Blocks.VINE) || rightSlotItem == Item.getItemFromBlock(Blocks.WATERLILY))
//        {
//            if (leftSlotItem == ToolRegistry.ANCIENT_ROD)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Items.STRING)
//        {
//            if (leftSlotItem == Items.BOW || leftSlotItem == Items.FISHING_ROD)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Items.FLINT)
//        {
//            if (leftSlotItem == Items.FLINT_AND_STEEL)
//            {
//                event.setOutput(left);
//                event.setCost(REPAIR_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == Items.BOOK)
//        {
//            int bookCount = Math.min(event.getRight().getCount() + 1, 5);
//
//            if (leftSlotItem == Items.ENCHANTED_BOOK)
//            {
//                ItemStack output = left.copy();
//                output.setCount(bookCount);
//                event.setOutput(output);
//                event.setCost(bookCount);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == ItemRegistry.BIOS_DIAMOND)
//        {
//            if (leftSlotItem == ToolRegistry.LIMONITE_PRIMARY_AXE)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.LIMONITE_AXE));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.LIMONITE_PRIMARY_PICKAXE)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.LIMONITE_PICKAXE));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.LIMONITE_PRIMARY_SWORD)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.LIMONITE_SWORD));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == ItemRegistry.FLAME_STONE)
//        {
//            if (leftSlotItem == ToolRegistry.RENDELITHIC_PRIMARY_SHOVEL)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.RENDELITHIC_SHOVEL));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.RENDELITHIC_PRIMARY_PICKAXE)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.RENDELITHIC_PICKAXE));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.RENDELITHIC_PRIMARY_SWORD)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.RENDELITHIC_SWORD));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == ItemRegistry.MARINE_CRYSTAL)
//        {
//            if (leftSlotItem == ToolRegistry.LAPIS_PRIMARY_SHOVEL)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.LAPIS_SHOVEL));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.LAPIS_PRIMARY_PICKAXE)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.LAPIS_PICKAXE));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.LAPIS_PRIMARY_HOE)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.LAPIS_HOE));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == ItemRegistry.STRENGTH_PEARL)
//        {
//            if (leftSlotItem == ToolRegistry.DIAMITHIC_PRIMARY_AXE)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.DIAMITHIC_AXE));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.DIAMITHIC_PRIMARY_PICKAXE)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.DIAMITHIC_PICKAXE));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.DIAMITHIC_PRIMARY_SWORD)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.DIAMITHIC_SWORD));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//
//            return;
//        }
//
//        if (rightSlotItem == ItemRegistry.SUPER_CRYSTAL)
//        {
//            if (leftSlotItem == ToolRegistry.SUPER_PRIMARY_SHOVEL)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.SUPER_SHOVEL));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.SUPER_PRIMARY_PICKAXE)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.SUPER_PICKAXE));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.SUPER_PRIMARY_SWORD)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.SUPER_SWORD));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//            else if (leftSlotItem == ToolRegistry.SUPER_PRIMARY_AXE)
//            {
//                event.setOutput(new ItemStack(ToolRegistry.SUPER_AXE));
//                event.setCost(TOOL_ENHANCEMENT_COST);
//            }
//
//            return;
//        }
    }

    @SubscribeEvent
    public void onAnvilRepairEvent(AnvilRepairEvent event)
    {
//        ItemStack itemResult = event.getItemResult();
//        Item item = itemResult.getItem();
//        Item ingredient = event.getIngredientInput().getItem();
//
//        if (ingredient == ItemRegistry.RENDELITHIC_INGOT)
//        {
//            if (item == ArmorRegistry.RENDEL_LEGGINGS || item == ArmorRegistry.RENDEL_BOOTS
//                    || item == ArmorRegistry.RENDEL_CHESTPLATE || item == ArmorRegistry.RENDEL_HELMET)
//            {
//                repairArmor(itemResult, item);
//                return;
//            }
//
//            if (item instanceof IModTool)
//            {
//                IModTool tool = (IModTool)item;
//                if (tool.canBeRepairedWith(ItemRegistry.RENDELITHIC_INGOT))
//                {
//                    repairItem(itemResult, tool.getRepairAmount());
//                    return;
//                }
//            }
//
//            return;
//        }
//
//        if (ingredient == ItemRegistry.LIMONITE_INGOT)
//        {
//            if (item == ArmorRegistry.LIMONITE_LEGGINGS || item == ArmorRegistry.LIMONITE_BOOTS
//                    || item == ArmorRegistry.LIMONITE_CHESTPLATE || item == ArmorRegistry.LIMONITE_HELMET)
//            {
//                repairArmor(itemResult, item);
//                return;
//            }
//
//            if (item instanceof IModTool)
//            {
//                IModTool tool = (IModTool)item;
//                if (tool.canBeRepairedWith(ItemRegistry.LIMONITE_INGOT))
//                {
//                    repairItem(itemResult, tool.getRepairAmount());
//                    return;
//                }
//            }
//
//            return;
//        }
//
//        if (ingredient == ItemRegistry.LAPIS_INGOT)
//        {
//            if (item == ArmorRegistry.LAPIS_LEGGINGS || item == ArmorRegistry.LAPIS_BOOTS
//                    || item == ArmorRegistry.LAPIS_CHESTPLATE || item == ArmorRegistry.LAPIS_HELMET)
//            {
//                repairArmor(itemResult, item);
//                return;
//            }
//
//            if (item instanceof IModTool)
//            {
//                IModTool tool = (IModTool)item;
//                if (tool.canBeRepairedWith(ItemRegistry.LAPIS_INGOT))
//                {
//                    repairItem(itemResult, tool.getRepairAmount());
//                    return;
//                }
//            }
//
//            return;
//        }
//
//        if (ingredient == ItemRegistry.DIAMITHIC_INGOT)
//        {
//            if (item == ArmorRegistry.DIAMITHIC_LEGGINGS || item == ArmorRegistry.DIAMITHIC_BOOTS
//                    || item == ArmorRegistry.DIAMITHIC_CHESTPLATE || item == ArmorRegistry.DIAMITHIC_HELMET)
//            {
//                repairArmor(itemResult, item);
//                return;
//            }
//
//            if (item instanceof IModTool)
//            {
//                IModTool tool = (IModTool)item;
//                if (tool.canBeRepairedWith(ItemRegistry.DIAMITHIC_INGOT))
//                {
//                    repairItem(itemResult, tool.getRepairAmount());
//                    return;
//                }
//            }
//
//            return;
//        }
//
//        if (ingredient == ItemRegistry.SUPER_INGOT)
//        {
//            if (item == ArmorRegistry.SUPER_LEGGINGS || item == ArmorRegistry.SUPER_BOOTS
//                    || item == ArmorRegistry.SUPER_CHESTPLATE || item == ArmorRegistry.SUPER_HELMET)
//            {
//                repairArmor(itemResult, item);
//                return;
//            }
//
//            if (item instanceof IModTool)
//            {
//                IModTool tool = (IModTool)item;
//                if (tool.canBeRepairedWith(ItemRegistry.SUPER_INGOT))
//                {
//                    repairItem(itemResult, tool.getRepairAmount());
//                    return;
//                }
//            }
//
//            return;
//        }
//
//        if (ingredient == Items.DIAMOND)
//        {
//            if (item == Items.DIAMOND_LEGGINGS || item == Items.DIAMOND_BOOTS
//                    || item == Items.DIAMOND_CHESTPLATE || item == Items.DIAMOND_HELMET)
//            {
//                repairVanillaArmor(itemResult, item);
//                return;
//            }
//
//            if (item instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)item;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.DIAMOND.toString()))
//                {
//                    int repairAmount = Item.ToolMaterial.DIAMOND.getMaxUses() / 6;
//                    repairItem(itemResult, repairAmount);
//                    return;
//                }
//            }
//
//            if (item == Items.DIAMOND_PICKAXE || item == Items.DIAMOND_SHOVEL
//                    || item == Items.DIAMOND_AXE || item == Items.DIAMOND_SWORD
//                    || item == Items.DIAMOND_HOE)
//            {
//                int repairAmount = Item.ToolMaterial.DIAMOND.getMaxUses() / 6;
//                repairItem(itemResult, repairAmount);
//                return;
//            }
//
//            return;
//        }
//
//        if (ingredient == Items.IRON_INGOT)
//        {
//            if (item == Items.IRON_LEGGINGS || item == Items.IRON_BOOTS
//                    || item == Items.IRON_CHESTPLATE || item == Items.IRON_HELMET
//                    || item == Items.CHAINMAIL_LEGGINGS || item == Items.CHAINMAIL_BOOTS
//                    || item == Items.CHAINMAIL_CHESTPLATE || item == Items.CHAINMAIL_HELMET)
//            {
//                repairVanillaArmor(itemResult, item);
//                return;
//            }
//
//            if (item instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)item;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.IRON.toString()))
//                {
//                    int repairAmount = Item.ToolMaterial.IRON.getMaxUses() / 6;
//                    repairItem(itemResult, repairAmount);
//                    return;
//                }
//            }
//
//            if (item == Items.IRON_PICKAXE || item == Items.IRON_SHOVEL
//                    || item == Items.IRON_AXE || item == Items.IRON_SWORD
//                    || item == Items.IRON_HOE || item == Items.SHEARS)
//            {
//                int repairAmount = Item.ToolMaterial.IRON.getMaxUses() / 6;
//                repairItem(itemResult, repairAmount);
//                return;
//            }
//
//            return;
//        }
//
//        if (ingredient == Item.getItemFromBlock(Blocks.COBBLESTONE))
//        {
//            if (item instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)item;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.STONE.toString()))
//                {
//                    int repairAmount = Item.ToolMaterial.STONE.getMaxUses() / 6;
//                    repairItem(itemResult, repairAmount);
//                    return;
//                }
//            }
//
//            if (item == Items.STONE_PICKAXE || item == Items.STONE_SHOVEL
//                    || item == Items.STONE_AXE || item == Items.STONE_SWORD
//                    || item == Items.STONE_HOE)
//            {
//                int repairAmount = Item.ToolMaterial.STONE.getMaxUses() / 6;
//                repairItem(itemResult, repairAmount);
//                return;
//            }
//
//            return;
//        }
//
//        if (ingredient == Items.GOLD_INGOT)
//        {
//            if (item == Items.GOLDEN_LEGGINGS || item == Items.GOLDEN_BOOTS
//                    || item == Items.GOLDEN_CHESTPLATE || item == Items.GOLDEN_HELMET)
//            {
//                repairVanillaArmor(itemResult, item);
//                return;
//            }
//
//            if (item instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)item;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.GOLD.toString()))
//                {
//                    int repairAmount = Item.ToolMaterial.GOLD.getMaxUses() / 4;
//                    repairItem(itemResult, repairAmount);
//                    return;
//                }
//            }
//
//            if (item == Items.GOLDEN_PICKAXE || item == Items.GOLDEN_SHOVEL
//                    || item == Items.GOLDEN_AXE || item == Items.GOLDEN_SWORD
//                    || item == Items.GOLDEN_HOE)
//            {
//                int repairAmount = Item.ToolMaterial.GOLD.getMaxUses() / 4;
//                repairItem(itemResult, repairAmount);
//                return;
//            }
//
//            return;
//        }
//
//        if (ingredient == Items.LEATHER)
//        {
//            if (item == Items.LEATHER_LEGGINGS || item == Items.LEATHER_BOOTS
//                    || item == Items.LEATHER_CHESTPLATE || item == Items.LEATHER_HELMET
//                    || item == Items.ELYTRA)
//            {
//                if (item instanceof ItemArmor)
//                {
//                    repairVanillaArmor(itemResult, item);
//                }
//                else
//                {
//                    repairItem(itemResult, ELYTRA_REPAIR_AMOUNT);
//                }
//                return;
//            }
//
//            return;
//        }
//
//        if (ingredient == Item.getItemFromBlock(Blocks.PLANKS))
//        {
//            if (item instanceof ItemTool)
//            {
//                ItemTool tool = (ItemTool)item;
//                if (tool.getToolMaterialName().equals(Item.ToolMaterial.WOOD.toString()))
//                {
//                    int repairAmount = Item.ToolMaterial.WOOD.getMaxUses() / 3;
//                    repairItem(itemResult, repairAmount);
//                    return;
//                }
//            }
//
//            if (item == Items.WOODEN_AXE || item == Items.WOODEN_PICKAXE
//                    || item == Items.WOODEN_HOE || item == Items.WOODEN_SHOVEL
//                    || item == Items.WOODEN_SWORD)
//            {
//                int repairAmount = Item.ToolMaterial.WOOD.getMaxUses() / 3;
//                repairItem(itemResult, repairAmount);
//                return;
//            }
//
//            if (item == Items.SHIELD)
//            {
//                repairItem(itemResult, SHIELD_REPAIR_AMOUNT);
//                return;
//            }
//            return;
//        }
//
//        if (ingredient == Items.STRING)
//        {
//            if (item == Items.BOW)
//            {
//                repairItem(itemResult, BOW_REPAIR_AMOUNT);
//                return;
//            }
//            else if (item == Items.FISHING_ROD)
//            {
//                repairItem(itemResult, FISHING_ROD_REPAIR_AMOUNT);
//                return;
//            }
//
//            return;
//        }
//
//        if (ingredient == Items.FLINT)
//        {
//            if (item == Items.FLINT_AND_STEEL)
//            {
//                repairItem(itemResult, FLINT_AND_STEEL_REPAIR_AMOUNT);
//                return;
//            }
//        }
//
//        if (ingredient == Item.getItemFromBlock(Blocks.VINE))
//        {
//            if (item == ToolRegistry.ANCIENT_ROD)
//            {
//                int repairAmount = Item.ToolMaterial.WOOD.getMaxUses() / 5;
//                repairItem(itemResult, repairAmount);
//                return;
//            }
//        }
//
//        if (ingredient == Item.getItemFromBlock(Blocks.WATERLILY))
//        {
//            if (item == ToolRegistry.ANCIENT_ROD)
//            {
//                int repairAmount = Item.ToolMaterial.WOOD.getMaxUses();
//                repairItem(itemResult, repairAmount);
//                return;
//            }
//        }
    }

//    private void repairVanillaArmor(ItemStack itemResult, Item item)
//    {
//        ItemArmor armor = (ItemArmor) item;
//        int repairAmount = armor.getArmorMaterial().getDurability(EntityEquipmentSlot.CHEST) / 6;
//        repairItem(itemResult, repairAmount);
//    }
//
//    private void repairArmor(ItemStack itemResult, Item item)
//    {
//        ModArmor armor = (ModArmor)item;
//        int repairAmount = armor.getRepairAmount();
//        repairItem(itemResult, repairAmount);
//    }
//
//    private void repairItem(ItemStack itemStack, int damageDecrement)
//    {
//        int newDamage = itemStack.getItemDamage() - damageDecrement;
//        itemStack.setItemDamage(newDamage < 0 ? 0 : newDamage);
//    }

    private void replaceTable(LootTableLoadEvent event, String resourcePath)
    {
        ResourceLocation loc = new ResourceLocation(MaterialExt.MODID, resourcePath);
        LootTable lootTable = event.getLootTableManager().get(loc);
        event.setTable(lootTable);
    }
}
