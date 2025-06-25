package com.pekar.angelblock.blocks;

import com.pekar.angelblock.blocks.tile_entities.AngelBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AngelBlock extends ModBlock implements EntityBlock
{
    public static final int MaxMonstersFilterValue = 4;
    public static final IntegerProperty MONSTERS_IN_FILTER = IntegerProperty.create("monsters_in_filter", 0, MaxMonstersFilterValue);

    public AngelBlock(Properties properties)
    {
        super(properties);

        registerDefaultState(this.stateDefinition.any().setValue(MONSTERS_IN_FILTER, 0));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        var blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof AngelBlockEntity angelBlockEntity)
        {
            if (stack.isEmpty()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

            var isClientSide = level.isClientSide();

            var interactionItem = stack.getItem();
            if (interactionItem == Items.ECHO_SHARD)
            {
                var isReset = angelBlockEntity.resetFilter(player);
                if (!isReset)
                    return ItemInteractionResult.FAIL;

                if (!isClientSide)
                {
                    setBlockStateValue(level, pos, 0);
                }

                return ItemInteractionResult.sidedSuccess(isClientSide);
            }
            else
            {
                var isAdded = angelBlockEntity.addMonsterToFilter(interactionItem, player);
                if (isAdded)
                {
                    setBlockStateValue(level, pos, angelBlockEntity.monstersInFilter());
                }
                return isAdded ? ItemInteractionResult.sidedSuccess(isClientSide) : ItemInteractionResult.FAIL;
            }
        }

        return ItemInteractionResult.FAIL;
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
        builder.add(MONSTERS_IN_FILTER);
    }

    public void setBlockStateValue(Level level, BlockPos pos, int value)
    {
        if (value > MaxMonstersFilterValue)
        {
            level.setBlock(pos, BlockRegistry.INACTIVE_ANGEL_BLOCK.get().defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            return;
        }

        var state = level.getBlockState(pos);
        level.setBlock(pos, state.setValue(MONSTERS_IN_FILTER, value), Block.UPDATE_ALL_IMMEDIATE);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston)
    {
        if (!level.isClientSide)
        {
            level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }
}
