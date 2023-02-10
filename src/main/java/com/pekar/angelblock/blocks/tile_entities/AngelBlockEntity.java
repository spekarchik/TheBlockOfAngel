package com.pekar.angelblock.blocks.tile_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class AngelBlockEntity extends BlockEntity implements BlockEntityTicker<AngelBlockEntity>
{
    private static final double EFFECTIVE_RADIUS = 70.0;
    private int counter;

    public AngelBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(EntityRegistry.ANGEL_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState blockState, AngelBlockEntity entity)
    {
        if (++counter > 40)
        {
            onUpdate(level, entity);
            counter = 0;
        }
    }

    private void onUpdate(Level level, AngelBlockEntity blockEntity)
    {
        if (level.isClientSide()) return;

        var monsters = level.getEntities((Entity)null,
                blockEntity.getRenderBoundingBox().inflate(EFFECTIVE_RADIUS),
                entity -> entity instanceof Enemy);

        for (Entity entity : monsters)
        {
            entity.discard();
        }
    }
}
