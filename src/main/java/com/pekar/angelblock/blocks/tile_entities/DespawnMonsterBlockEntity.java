package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class DespawnMonsterBlockEntity<T extends BlockEntity> extends BlockEntity implements BlockEntityTicker<T>
{
    private final Utils utils = new Utils();

    public DespawnMonsterBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState)
    {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState blockState, T entity)
    {
        if (level.getGameTime() % 15 == 0)
        {
            onUpdate(level, entity);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        loadModTag(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        saveModTag(tag);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        var tag = new CompoundTag();
        saveModTag(tag);
        return tag;
    }

    protected abstract void loadModTag(CompoundTag tag);

    protected abstract void saveModTag(CompoundTag tag);

    protected abstract double getEffectiveRadius();

    protected abstract boolean needToDespawnEntity(Entity entity);

    private void onUpdate(Level level, T blockEntity)
    {
        if (level.isClientSide()) return;


        var monsters = level.getEntitiesOfClass(LivingEntity.class,
                utils.getRenderBoundingBox(blockEntity.getBlockPos()).inflate(getEffectiveRadius()),
                this::needToDespawnEntity);

        for (Entity entity : monsters)
        {
            entity.discard();
        }
    }
}
