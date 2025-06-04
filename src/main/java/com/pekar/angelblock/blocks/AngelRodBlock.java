package com.pekar.angelblock.blocks;

import com.google.common.collect.ImmutableMap;
import com.pekar.angelblock.blocks.tile_entities.AngelRodBlockEntity;
import com.pekar.angelblock.blocks.tile_entities.EntityRegistry;
import com.pekar.angelblock.tools.ToolRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class AngelRodBlock extends ModBlock implements EntityBlock
{
    private static final VoxelShape SHAPE_X = Shapes.create(0.328125, 0.0, 0.46875, 0.671875, 1.21875, 0.53125);
    private static final VoxelShape SHAPE_Z = Shapes.create(0.46875, 0.0, 0.328125, 0.53125, 1.21875, 0.671875);

    public static final BooleanProperty FACING_ALONG_X = BooleanProperty.create("facing_along_x");

    public AngelRodBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING_ALONG_X, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING_ALONG_X);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var direction = context.getHorizontalDirection();
        return switch (direction)
        {
            case EAST, WEST -> defaultBlockState().setValue(FACING_ALONG_X, false);
            default -> defaultBlockState().setValue(FACING_ALONG_X, true);
        };
    }

    @Override
    protected ImmutableMap<BlockState, VoxelShape> getShapeForEachState(Function<BlockState, VoxelShape> voxelShapeFunction)
    {
        var defaultBlockState = defaultBlockState();
        return ImmutableMap.of(
                defaultBlockState.setValue(FACING_ALONG_X, true), SHAPE_X,
                defaultBlockState.setValue(FACING_ALONG_X, false), SHAPE_Z
        );
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext)
    {
        return blockState.getValue(FACING_ALONG_X) ? SHAPE_X : SHAPE_Z;
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
    public @NotNull Item asItem()
    {
        return ToolRegistry.ANGEL_ROD.get();
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if (!level.isClientSide() && !player.isCreative())
        {
            var blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AngelRodBlockEntity angelRodBlockEntity)
            {
                var dropDirection = player.getDirection().getOpposite();

                if (player.isSteppingCarefully())
                {
                    var itemStack1 = new ItemStack(ToolRegistry.END_MAGNETIC_ROD.get());
                    itemStack1.setDamageValue(angelRodBlockEntity.getDamage());
                    var itemStack2 = new ItemStack(BlockRegistry.ANGEL_BLOCK.get());
                    var itemStack3 = new ItemStack(Items.TOTEM_OF_UNDYING);

                    popResourceFromFace(level, pos, dropDirection, itemStack1);
                    popResourceFromFace(level, pos, dropDirection, itemStack2);
                    popResourceFromFace(level, pos, dropDirection, itemStack3);
                }
                else
                {
                    var itemStack = new ItemStack(ToolRegistry.ANGEL_ROD.get());
                    itemStack.setDamageValue(angelRodBlockEntity.getDamage());
                    popResourceFromFace(level, pos, dropDirection, itemStack);
                }
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }
}
