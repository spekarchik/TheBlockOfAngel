package com.pekar.angelblock.blocks.tile_entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class DespawnMonsterBlockEntity<T extends BlockEntity> extends BlockEntity implements BlockEntityTicker<T>
{
    private int counter;

    public DespawnMonsterBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState)
    {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState blockState, T entity)
    {
        if (++counter > 37)
        {
            onUpdate(level, entity);
            counter = 0;
        }
    }

    protected abstract double getEffectiveRadius();

    protected abstract boolean needToDespawnEntity(Entity entity);

    private void onUpdate(Level level, T blockEntity)
    {
        if (level.isClientSide()) return;

        var monsters = level.getEntities((LivingEntity)null,
                blockEntity.getRenderBoundingBox().inflate(getEffectiveRadius()),
                this::needToDespawnEntity);

        for (Entity entity : monsters)
        {
            entity.discard();
        }
    }
}
