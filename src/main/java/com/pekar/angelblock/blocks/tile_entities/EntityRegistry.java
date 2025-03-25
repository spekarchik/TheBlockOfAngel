package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.blocks.BlockRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

public class EntityRegistry
{
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AngelBlockEntity>> ANGEL_BLOCK_ENTITY =
            Main.BLOCK_ENTITIES.register("angel_block_entity", () ->
                    new BlockEntityType<>(AngelBlockEntity::new,BlockRegistry.ANGEL_BLOCK.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DevilBlockEntity>> DEVIL_BLOCK_ENTITY =
            Main.BLOCK_ENTITIES.register("devil_block_entity", () ->
                    new BlockEntityType<>(DevilBlockEntity::new, BlockRegistry.DEVIL_BLOCK.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AngelRodBlockEntity>> ANGEL_ROD_BLOCK_ENTITY =
            Main.BLOCK_ENTITIES.register("angel_rod_block_entity", () ->
                    new BlockEntityType<>(AngelRodBlockEntity::new, BlockRegistry.ANGEL_ROD_BLOCK.get()));

    public static void initStatic()
    {
        // just to initialize static members
    }
}
