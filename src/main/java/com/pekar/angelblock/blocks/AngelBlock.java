package com.pekar.angelblock.blocks;

import com.pekar.angelblock.blocks.tile_entities.AngelBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AngelBlock extends ModBlock implements EntityBlock
{
    public static final int MaxMonstersFilterValue = 4;
    public static final IntegerProperty MONSTERS_IN_FILTER = IntegerProperty.create("monsters_in_filter", 0, MaxMonstersFilterValue);
    public static final BooleanProperty IS_WORMING_UP = BooleanProperty.create("is_worming_up");

    public AngelBlock(Properties properties)
    {
        super(properties);

        registerDefaultState(this.stateDefinition.any().setValue(MONSTERS_IN_FILTER, 0));
        registerDefaultState(this.stateDefinition.any().setValue(IS_WORMING_UP, false));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AngelBlockEntity angelBlockEntity)
        {
            if (stack.isEmpty()) return InteractionResult.FAIL;

            var isClientSide = level.isClientSide();

            if (level.getBlockState(pos).getValue(IS_WORMING_UP))
            {
                return InteractionResult.FAIL;
            }

            var interactionItem = stack.getItem();
            if (interactionItem == Items.ECHO_SHARD)
            {
                var isReset = angelBlockEntity.resetFilter(player);
                if (!isReset)
                    return InteractionResult.FAIL;

                if (!isClientSide)
                {
                    setBlockStateForMonsterFilter(level, pos, 0);
                }

                return getInteractionSidedSuccess(isClientSide);
            }
            else
            {
                var isAdded = angelBlockEntity.addMonsterToFilter(interactionItem, player);
                if (isAdded)
                {
                    setBlockStateForMonsterFilter(level, pos, angelBlockEntity.monstersInFilter());
                }
                return isAdded ? getInteractionSidedSuccess(isClientSide) : InteractionResult.FAIL;
            }
        }

        return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
    {
        return EntityRegistry.ANGEL_BLOCK_ENTITY.get().create(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> entityType)
    {
        return level.isClientSide()
                ? null
                : (level0, pos, blockState0, blockEntity) -> ((AngelBlockEntity)blockEntity).tick(level0, pos, blockState0, (AngelBlockEntity) blockEntity);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(IS_WORMING_UP, MONSTERS_IN_FILTER);
    }

    private void setBlockStateForMonsterFilter(Level level, BlockPos pos, int value)
    {
        var state = level.getBlockState(pos);

        if (state.getValue(IS_WORMING_UP))
        {
            return;
        }

        if (value > MaxMonstersFilterValue)
        {
            level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.setBlock(pos, BlockRegistry.INACTIVE_ANGEL_BLOCK.get().defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            return;
        }

        level.setBlock(pos, state.setValue(MONSTERS_IN_FILTER, value), Block.UPDATE_ALL_IMMEDIATE);
    }

    public void setBlockStateForWarmingUp(Level level, BlockPos pos, boolean isWarmingUp)
    {
        var state = level.getBlockState(pos);
        level.setBlock(pos, state.setValue(IS_WORMING_UP, isWarmingUp), Block.UPDATE_ALL_IMMEDIATE);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(MONSTERS_IN_FILTER, 0).setValue(IS_WORMING_UP, true);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston)
    {
        if (!level.isClientSide())
        {
            if (state.getValue(IS_WORMING_UP))
            {
                level.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1.0f, 1.0f);

                var blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof AngelBlockEntity angelBlockEntity)
                {
                    angelBlockEntity.startWormingUp();
                }
            }
            else if (oldState.getValue(IS_WORMING_UP) && !state.getValue(IS_WORMING_UP) && state.getValue(MONSTERS_IN_FILTER) == 0)
            {
                level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }
}
