package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.blocks.BlockRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry
{
    public static final RegistryObject<BlockEntityType<AngelBlockEntity>> ANGEL_BLOCK_ENTITY =
            Main.BLOCK_ENTITIES.register("angel_block_entity", () ->
                    BlockEntityType.Builder.of(AngelBlockEntity::new, BlockRegistry.ANGEL_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<DevilBlockEntity>> DEVIL_BLOCK_ENTITY =
            Main.BLOCK_ENTITIES.register("devil_block_entity", () ->
                    BlockEntityType.Builder.of(DevilBlockEntity::new, BlockRegistry.DEVIL_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<AngelRodBlockEntity>> ANGEL_ROD_BLOCK_ENTITY =
            Main.BLOCK_ENTITIES.register("angel_rod_block_entity", () ->
                    BlockEntityType.Builder.of(AngelRodBlockEntity::new, BlockRegistry.ANGEL_ROD_BLOCK.get()).build(null));

    public static void initStatic()
    {
        // just to initialize static members
    }
}
