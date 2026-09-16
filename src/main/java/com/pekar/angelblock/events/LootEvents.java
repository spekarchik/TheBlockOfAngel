package com.pekar.angelblock.events;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.tools.ToolRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.IntLimit;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import static com.pekar.angelblock.loot.LootRegistry.*;
import static com.pekar.angelblock.utils.Resources.createResourceLocation;

public class LootEvents implements IEventHandler
{
    @SubscribeEvent
    public void onChestLootTableLoad(LootTableLoadEvent event)
    {
        chestLootTableLoad(event);
        villageChestLootTableLoad(event);
        entitiesLootTableLoad(event);
    }

    private void chestLootTableLoad(LootTableLoadEvent event)
    {
        if (event.getKey().equals(BuiltInLootTables.ABANDONED_MINESHAFT))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.STRENGTH_PEARL).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 4))))
                    .add(LootItem.lootTableItem(ItemRegistry.MINER_FIGURE).setWeight(4))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ContextIntProviders.exactly(2))
                    .build();

            event.getTable().addPool(pool1);
            // there is one more pool with `on_random_loot` replaced with `on_good_loot` (book 1/7)
            // * let's do nothing with that. curse is appropriate for mineshafts
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.ANCIENT_CITY))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.SUPER_POWDER).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                    .setRolls(ContextIntProviders.between(1, 2))
                    .build();

            event.getTable().addPool(pool);
            // there is one more pool with `on_random_loot` replaced with `on_good_loot`
            // * no sense to add more enchanted books. let's keep `on_random_loot` and get rid of json
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.BASTION_BRIDGE))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(BlockRegistry.NETHER_BARS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 10))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.BASTION_HOGLIN_STABLE))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(BlockRegistry.NETHER_BARS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 10))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.BASTION_OTHER))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(BlockRegistry.NETHER_BARS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.BASTION_TREASURE))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(BlockRegistry.NETHER_BARS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 20))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.BURIED_TREASURE))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.SUPER_ARMOR_UPGRADE_KIT))
                    .add(LootItem.lootTableItem(ItemRegistry.SUPER_TOOL_UPGRADE_KIT))
                    .setRolls(ContextIntProviders.between(0, 1))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_POWDER).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(5))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            // removed diamond tools which replaced iron sword and leather chestplate
            // * let's do nothing with that, already have a vanilla chance of 1-2 diamonds
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.DESERT_PYRAMID))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.VESICULAR_TERRACOTTA).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

//            var pool2 = LootPool.lootPool()
//                    .add(LootItem.lootTableItem(Items.BOOK).setWeight(1)
//                            .apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ON_GOOD_LOOT_ENCHANTMENTS))))
//                    .add(EmptyLootItem.emptyItem().setWeight(12))
//                    .setRolls(ContextIntProviders.between(2, 4))
//                    .build();

            event.getTable().addPool(pool1);
//            event.getTable().addPool(pool2);
            // there is one more pool with `on_random_loot` replaced with `on_good_loot`
            // * added one more pool with enchanted books with the same chance
            // * cancelled. keep vanilla enchantments
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.END_CITY_TREASURE))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.END_SAPPHIRE).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                    .setRolls(ContextIntProviders.between(0, 2))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.IGLOO_CHEST))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.ANCIENT_CANINE).setWeight(9).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 5))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(BlockRegistry.FLYING_MATERIAL_BLOCK).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.JUNGLE_TEMPLE))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_ARMOR_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_TOOL_UPGRADE_KIT).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(5))
                    .setRolls(ContextIntProviders.between(0, 2))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ToolRegistry.ANCIENT_ROD).setWeight(2))
                    .add(LootItem.lootTableItem(ItemRegistry.BIOS_DIAMOND).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(5))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.FLAX_FIBRE).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool4 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_POWDER).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                    .add(LootItem.lootTableItem(ArmorRegistry.HORSE_LYMONITE_ARMOR).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(4))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            event.getTable().addPool(pool4);
            // on_good_loot
            // * let's do nothing with that. enchanted book is not important there. better to keep the loot minimalistic
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.NETHER_BRIDGE))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.FLAME_STONE).apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_POWDER).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .add(LootItem.lootTableItem(ToolRegistry.RENDELITHIC_PRIMARY_SHOVEL).setWeight(1)
                            .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.15f, 0.8f), false)))
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT)))
                    .add(LootItem.lootTableItem(ToolRegistry.RENDELITHIC_SHOVEL).setWeight(1)
                            .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.15f, 0.8f), false)))
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT)))
                    .setRolls(ContextIntProviders.between(0, 1))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_TOOL_UPGRADE_KIT).setWeight(1))
                    .setRolls(ContextIntProviders.between(0, 1))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.PILLAGER_OUTPOST))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.STRENGTH_PEARL).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                    .setRolls(ContextIntProviders.between(0, 2))
                    .build();

            event.getTable().addPool(pool);
            // on_good_loot
            // * let's do nothing with that. enchanted book is not important there
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.RUINED_PORTAL))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.SLIME_BALL).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_TOOL_UPGRADE_KIT).setWeight(3))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_ARMOR_UPGRADE_KIT).setWeight(3))
                    .add(LootItem.lootTableItem(ItemRegistry.IRON_TOOL_UPGRADE_KIT).setWeight(5))
                    .add(LootItem.lootTableItem(ItemRegistry.IRON_ARMOR_UPGRADE_KIT).setWeight(5))
                    .add(EmptyLootItem.emptyItem().setWeight(6))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.SHIPWRECK_TREASURE))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.SHULKER_SHELL).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                    .add(LootItem.lootTableItem(Items.SHULKER_BOX).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ContextIntProviders.exactly(2))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.SIMPLE_DUNGEON))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.MINER_FIGURE))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.STRENGTH_PEARL).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            // on_good_loot
            // * let's do nothing with that. enchanted book is not important there
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.STRONGHOLD_CORRIDOR))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_ARMOR_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_TOOL_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(1))
                    .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.between(0, 2))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_PICKAXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_AXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                    .setRolls(ContextIntProviders.between(0, 1))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.NETHERITE_HELMET).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_BOOTS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_HELMET).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(3))
                    .setRolls(ContextIntProviders.between(0, 1))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            // on_good_loot, not removed iron gear which reduces the chance of horse armor and golden apple
            // * better to keep the adjusted json table
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.STRONGHOLD_CROSSING))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_ARMOR_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_TOOL_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(1))
                    .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.between(0, 2))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_PICKAXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_AXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(3).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5))))
                    .setRolls(ContextIntProviders.between(0, 1))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.NETHERITE_HELMET).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_BOOTS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_HELMET).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39))))
                    .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(3))
                    .setRolls(ContextIntProviders.between(0, 1))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            // on_good_loot, iron pickaxe was unenchanted in Vanilla
            // * do nothing with that. we added a lot of enchanted gear in that chest already
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.STRONGHOLD_LIBRARY))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.ANCIENT_SCROLL).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.HORSE_ARMOR_HANDBOOK).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.WOLF_ARMOR_HANDBOOK).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool4 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.NAUTILUS_ARMOR_HANDBOOK).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

//            var pool5 = LootPool.lootPool()
//                    .add(LootItem.lootTableItem(Items.BOOK).setWeight(1)
//                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.exactly(30)).withOptions(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ON_GOOD_LOOT_ENCHANTMENTS))))
//                    .add(EmptyLootItem.emptyItem().setWeight(12))
//                    .setRolls(ContextIntProviders.between(2, 4))
//                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            event.getTable().addPool(pool4);
            // on_good_loot
            // * let's keep json? I don't want any 'garbage' books in libraries
            // * corrected via LootModifier
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.UNDERWATER_RUIN_BIG))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.MARINE_CRYSTAL).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 6))))
                    .add(LootItem.lootTableItem(Items.TURTLE_HELMET).setWeight(1).apply(EnchantRandomlyFunction.randomApplicableEnchantment(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT))))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            // on_good_loot
            // stone spear not removed
            // * let's do nothing with that. garbage is not unexpected in underwater ruins and enchanted book is not important there
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.UNDERWATER_RUIN_SMALL))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.FIRE_CORAL).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))))
                    .add(LootItem.lootTableItem(Items.DEAD_FIRE_CORAL).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))))
                    .add(LootItem.lootTableItem(Items.HORN_CORAL).setWeight(1).apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))))
                    .add(LootItem.lootTableItem(Items.DEAD_HORN_CORAL).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.MARINE_CRYSTAL).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(5))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.WOODLAND_MANSION))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.EVOKER_AMULET).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_ARMOR_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_TOOL_UPGRADE_KIT).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_POWDER).setWeight(2).apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 6))))
                    .add(LootItem.lootTableItem(ArmorRegistry.LIMONITE_BOOTS).setWeight(1)
                            .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.15f, 0.8f), false)))
                    .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39)))
                    .add(LootItem.lootTableItem(ArmorRegistry.LIMONITE_HELMET).setWeight(2)
                            .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(0.15f, 0.8f), false)))
                    .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(20, 39)))
                    .add(LootItem.lootTableItem(ItemRegistry.FLAX_FIBRE).setWeight(2))
                    .add(LootItem.lootTableItem(ArmorRegistry.HORSE_LYMONITE_ARMOR).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            var pool4 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.FLAX_FIBRE).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(9))
                    .setRolls(ContextIntProviders.between(1, 4))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            event.getTable().addPool(pool4);
            // on_good_loot
            // chainmail chestplate, diamond chestplate not removed
            // * let's keep json. I don't want too much 'garbage' loot in mansions
            return;
        }
    }

    private void villageChestLootTableLoad(LootTableLoadEvent event)
    {
        if (event.getKey().equals(BuiltInLootTables.VILLAGE_ARMORER))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.between(0, 3))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ARMORER_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(ContextIntProviders.between(1, 2))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(1))
                    .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(1))
                    .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(5))
                    .setRolls(ContextIntProviders.between(1, 5))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.VILLAGE_FISHER))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.FISHING_ROD).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ON_GOOD_LOOT_ENCHANTMENTS))))
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(FISHER_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(ContextIntProviders.between(0, 1))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.VILLAGE_FLETCHER))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(FLETCHER_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(ContextIntProviders.between(1, 2))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.VILLAGE_TANNERY))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.between(0, 3))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.FLYING_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ContextIntProviders.between(0, 3))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ARMORER_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(ContextIntProviders.between(1, 2))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.VILLAGE_TEMPLE))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE))
                    .setRolls(ContextIntProviders.between(1, 3))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), ContextIntProviders.between(25, 30)).withOptions(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ON_GOOD_LOOT_ENCHANTMENTS))))
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.VILLAGE_TOOLSMITH))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.between(0, 3))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(TOOLSMITH_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(ContextIntProviders.between(1, 2))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            return;
        }

        if (event.getKey().equals(BuiltInLootTables.VILLAGE_WEAPONSMITH))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ContextIntProviders.between(0, 3))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(WEAPONSMITH_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(ContextIntProviders.between(1, 2))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            return;
        }
    }

    private void entitiesLootTableLoad(LootTableLoadEvent event)
    {
        var elderGuardianLootTableId = createResourceLocation(Main.VANILLAID, "entities/elder_guardian");
        if (event.getName().equals(elderGuardianLootTableId))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.GUARDIAN_EYE).apply(LimitCount.limitCount(IntLimit.range(1, 1))))
                    .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT), 0.35f, 0.15f))
                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                    .setRolls(ContextIntProviders.exactly(1))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

    }
}
