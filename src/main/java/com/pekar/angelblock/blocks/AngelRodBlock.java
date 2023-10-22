package com.pekar.angelblock.blocks;

import com.google.common.collect.ImmutableMap;
import com.pekar.angelblock.blocks.tile_entities.AngelRodBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class AngelRodBlock extends ModBlockWithHoverText implements EntityBlock
{
    public AngelRodBlock()
    {
        super(BlockBehaviour.Properties.copy(Blocks.LIGHTNING_ROD) // LIGHTNING_ROD doesn't drop by hand
                .strength(0.1F, 1200F).sound(SoundType.COPPER)
                .lightLevel(state -> 15));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
    {
        return EntityRegistry.ANGEL_ROD_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> entityType)
    {
        return level.isClientSide()
                ? null
                : (level0, pos, blockState0, blockEntity) -> ((AngelRodBlockEntity)blockEntity).tick(level0, pos, blockState0, (AngelRodBlockEntity) blockEntity);
    }

    @Override
    protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> voxelShapeFunction)
    {
        var shape = Shapes.create(0.328125, 0.0, 0.46875, 0.671875, 1.21875, 0.53125);
        return ImmutableMap.of(defaultBlockState(), shape);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
    {
        return Shapes.create(0.328125, 0.0, 0.46875, 0.671875, 1.21875, 0.53125);
    }
}
