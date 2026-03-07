package com.pekar.angelblock.events;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.tools.ToolRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import static com.pekar.angelblock.loot.LootRegistry.*;

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
        if (event.getName().toString().equals("minecraft:chests/abandoned_mineshaft"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.STRENGTH_PEARL).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 4f))))
                    .add(LootItem.lootTableItem(ItemRegistry.MINER_FIGURE).setWeight(4))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ConstantValue.exactly(2f))
                    .build();

            event.getTable().addPool(pool1);
            // there is one more pool with `on_random_loot` replaced with `on_good_loot` (book 1/7)
            // * let's do nothing with that. curse is appropriate for mineshafts
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/ancient_city"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.SUPER_POWDER).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f))))
                    .setRolls(UniformGenerator.between(1f, 2f))
                    .build();

            event.getTable().addPool(pool);
            // there is one more pool with `on_random_loot` replaced with `on_good_loot`
            // * no sense to add more enchanted books. let's keep `on_random_loot` and get rid of json
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/bastion_bridge"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(BlockRegistry.NETHER_BARS).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 10f))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/bastion_hoglin_stable"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(BlockRegistry.NETHER_BARS).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 10f))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/bastion_other"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(BlockRegistry.NETHER_BARS).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 5f))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/bastion_treasure"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(BlockRegistry.NETHER_BARS).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 20f))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/buried_treasure"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.SUPER_ARMOR_UPGRADE_KIT))
                    .add(LootItem.lootTableItem(ItemRegistry.SUPER_TOOL_UPGRADE_KIT))
                    .setRolls(UniformGenerator.between(0f, 1f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_POWDER).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(5))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            // removed diamond tools which replaced iron sword and leather chestplate
            // * let's do nothing with that, already have a vanilla chance of 1-2 diamonds
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/desert_pyramid"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.VESICULAR_TERRACOTTA).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

//            var pool2 = LootPool.lootPool()
//                    .add(LootItem.lootTableItem(Items.BOOK).setWeight(1)
//                            .apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ON_GOOD_LOOT_ENCHANTMENTS))))
//                    .add(EmptyLootItem.emptyItem().setWeight(12))
//                    .setRolls(UniformGenerator.between(2f, 4f))
//                    .build();

            event.getTable().addPool(pool1);
//            event.getTable().addPool(pool2);
            // there is one more pool with `on_random_loot` replaced with `on_good_loot`
            // * added one more pool with enchanted books with the same chance
            // * cancelled. keep vanilla enchantments
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/end_city_treasure"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.END_SAPPHIRE).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f))))
                    .setRolls(UniformGenerator.between(0f, 2f))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/igloo_chest"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.ANCIENT_CANINE).setWeight(9).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 5f))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(BlockRegistry.FLYING_MATERIAL_BLOCK).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f))))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/jungle_temple"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_ARMOR_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_TOOL_UPGRADE_KIT).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(5))
                    .setRolls(UniformGenerator.between(0f, 2f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ToolRegistry.ANCIENT_ROD).setWeight(2))
                    .add(LootItem.lootTableItem(ItemRegistry.BIOS_DIAMOND).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(5))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.FLAX_FIBRE).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f))))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool4 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_POWDER).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 6f))))
                    .add(LootItem.lootTableItem(ArmorRegistry.HORSE_LYMONITE_ARMOR).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(4))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            event.getTable().addPool(pool4);
            // on_good_loot
            // * let's do nothing with that. enchanted book is not important there. better to keep the loot minimalistic
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/nether_bridge"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.FLAME_STONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 2f))))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_POWDER).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .add(LootItem.lootTableItem(ToolRegistry.RENDELITHIC_PRIMARY_SHOVEL).setWeight(1)
                            .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15f, 0.8f), false)))
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment(event.getRegistries()))
                    .add(LootItem.lootTableItem(ToolRegistry.RENDELITHIC_SHOVEL).setWeight(1)
                            .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15f, 0.8f), false)))
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment(event.getRegistries()))
                    .setRolls(UniformGenerator.between(0f, 1f))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_TOOL_UPGRADE_KIT).setWeight(1))
                    .setRolls(UniformGenerator.between(0f, 1f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/pillager_outpost"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.STRENGTH_PEARL).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f))))
                    .setRolls(UniformGenerator.between(0f, 2f))
                    .build();

            event.getTable().addPool(pool);
            // on_good_loot
            // * let's do nothing with that. enchanted book is not important there
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/ruined_portal"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.SLIME_BALL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f))))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_TOOL_UPGRADE_KIT).setWeight(8))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_ARMOR_UPGRADE_KIT).setWeight(8))
                    .add(EmptyLootItem.emptyItem().setWeight(6))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/shipwreck_treasure"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.SHULKER_SHELL).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 3f))))
                    .add(LootItem.lootTableItem(Items.SHULKER_BOX).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f))))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ConstantValue.exactly(2f))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/simple_dungeon"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.MINER_FIGURE))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.STRENGTH_PEARL).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 3f))))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            // on_good_loot
            // * let's do nothing with that. enchanted book is not important there
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/stronghold_corridor"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_ARMOR_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_TOOL_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(1))
                    .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 3f))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(UniformGenerator.between(0f, 2f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_PICKAXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_AXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2f, 5f))))
                    .setRolls(UniformGenerator.between(0f, 1f))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.NETHERITE_HELMET).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_BOOTS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_HELMET).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(3))
                    .setRolls(UniformGenerator.between(0f, 1f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            // on_good_loot, not removed iron gear which reduces the chance of horse armor and golden apple
            // * better to keep the adjusted json table
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/stronghold_crossing"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_ARMOR_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_TOOL_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(1))
                    .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 3f))))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(UniformGenerator.between(0f, 2f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_PICKAXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_AXE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.ANCIENT_DEBRIS).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2f, 5f))))
                    .setRolls(UniformGenerator.between(0f, 1f))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.NETHERITE_HELMET).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.NETHERITE_BOOTS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.DIAMOND_HELMET).setWeight(2)
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f))))
                    .add(LootItem.lootTableItem(Items.SCULK_SENSOR).setWeight(3))
                    .setRolls(UniformGenerator.between(0f, 1f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            // on_good_loot, iron pickaxe was unenchanted in Vanilla
            // * do nothing with that. we added a lot of enchanted gear in that chest already
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/stronghold_library"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.ANCIENT_SCROLL).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.HORSE_ARMOR_HANDBOOK).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.WOLF_ARMOR_HANDBOOK).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool4 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.NAUTILUS_ARMOR_HANDBOOK).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

//            var pool5 = LootPool.lootPool()
//                    .add(LootItem.lootTableItem(Items.BOOK).setWeight(1)
//                            .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), ConstantValue.exactly(30f)).withOptions(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ON_GOOD_LOOT_ENCHANTMENTS))))
//                    .add(EmptyLootItem.emptyItem().setWeight(12))
//                    .setRolls(UniformGenerator.between(2f, 4f))
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

        if (event.getName().toString().equals("minecraft:chests/underwater_ruin_big"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.MARINE_CRYSTAL).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 2f))))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(2f, 6f))))
                    .add(LootItem.lootTableItem(Items.TURTLE_HELMET).setWeight(1).apply(EnchantRandomlyFunction.randomApplicableEnchantment(event.getRegistries())))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            // on_good_loot
            // stone spear not removed
            // * let's do nothing with that. garbage is not unexpected in underwater ruins and enchanted book is not important there
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/underwater_ruin_small"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.FIRE_CORAL).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 2f))))
                    .add(LootItem.lootTableItem(Items.DEAD_FIRE_CORAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 2f))))
                    .add(LootItem.lootTableItem(Items.HORN_CORAL).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 2f))))
                    .add(LootItem.lootTableItem(Items.DEAD_HORN_CORAL).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(0f, 2f))))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.MARINE_CRYSTAL).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(5))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/woodland_mansion"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.EVOKER_AMULET).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_ARMOR_UPGRADE_KIT).setWeight(2))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_TOOL_UPGRADE_KIT).setWeight(2))
                    .add(EmptyLootItem.emptyItem().setWeight(3))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_POWDER).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 6f))))
                    .add(LootItem.lootTableItem(ArmorRegistry.LIMONITE_BOOTS).setWeight(1)
                            .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15f, 0.8f), false)))
                    .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f)))
                    .add(LootItem.lootTableItem(ArmorRegistry.LIMONITE_HELMET).setWeight(2)
                            .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.15f, 0.8f), false)))
                    .apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(20f, 39f)))
                    .add(LootItem.lootTableItem(ItemRegistry.FLAX_FIBRE).setWeight(2))
                    .add(LootItem.lootTableItem(ArmorRegistry.HORSE_LYMONITE_ARMOR).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            var pool4 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.FLAX_FIBRE).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(9))
                    .setRolls(UniformGenerator.between(1f, 4f))
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
        if (event.getName().toString().equals("minecraft:chests/village/village_armorer"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(UniformGenerator.between(0f, 3f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ARMORER_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(UniformGenerator.between(1f, 2f))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(1))
                    .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(1))
                    .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(5))
                    .setRolls(UniformGenerator.between(1f, 5f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/village/village_fisher"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.FISHING_ROD).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ON_GOOD_LOOT_ENCHANTMENTS))))
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(FISHER_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(UniformGenerator.between(0f, 1f))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/village/village_fletcher"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(FLETCHER_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(UniformGenerator.between(1f, 2f))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/village/village_tannery"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(UniformGenerator.between(0f, 3f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.FLYING_ARMOR_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(2))
                    .setRolls(UniformGenerator.between(0f, 3f))
                    .build();

            var pool3 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ARMORER_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(UniformGenerator.between(1f, 2f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            event.getTable().addPool(pool3);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/village/village_temple"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE))
                    .setRolls(UniformGenerator.between(1f, 3f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantWithLevelsFunction.enchantWithLevels(event.getRegistries(), UniformGenerator.between(25f, 30f)).fromOptions(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ON_GOOD_LOOT_ENCHANTMENTS))))
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/village/village_toolsmith"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(UniformGenerator.between(0f, 3f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(TOOLSMITH_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(UniformGenerator.between(1f, 2f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            return;
        }

        if (event.getName().toString().equals("minecraft:chests/village/village_weaponsmith"))
        {
            var pool1 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.RENDELITHIC_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMITHIC_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LAPIS_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.LIMONITE_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(LootItem.lootTableItem(ItemRegistry.DIAMOND_TOOL_UPGRADE_KIT).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight(1))
                    .setRolls(UniformGenerator.between(0f, 3f))
                    .build();

            var pool2 = LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BOOK).apply(EnchantRandomlyFunction.randomEnchantment().withOneOf(event.getRegistries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(WEAPONSMITH_SPECIFIC_ENCHANTMENTS))))
                    .setRolls(UniformGenerator.between(1f, 2f))
                    .build();

            event.getTable().addPool(pool1);
            event.getTable().addPool(pool2);
            return;
        }
    }

    private void entitiesLootTableLoad(LootTableLoadEvent event)
    {
        if (event.getName().toString().equals("minecraft:entities/elder_guardian"))
        {
            var pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemRegistry.GUARDIAN_EYE).apply(LimitCount.limitCount(IntRange.exact(1))))
                    .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(event.getRegistries(), 0.35f, 0.15f))
                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                    .setRolls(ConstantValue.exactly(1f))
                    .build();

            event.getTable().addPool(pool);
            return;
        }

    }
}
