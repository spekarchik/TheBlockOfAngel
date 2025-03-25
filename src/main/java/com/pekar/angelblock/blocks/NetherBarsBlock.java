package com.pekar.angelblock.blocks;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetherBarsBlock extends ModBlockWithDoubleHoverText
{
    private static final VoxelShape HITBOX_WEST_EAST = Shapes.box(
            0.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0,
            16.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0
    );

    private static final VoxelShape HITBOX_NORTH_SOUTH = Shapes.box(
            7.0 / 16.0, 0.0 / 16.0, 0.0 / 16.0,
            9.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0
    );

    private static final VoxelShape HITBOX_COLUMN = Shapes.box(
            7.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0,
            9.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0
    );

    private static final VoxelShape HITBOX_CORNER_EAST_NORTH = Shapes.or(
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0),
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 0.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0)
    );

    private static final VoxelShape HITBOX_CORNER_EAST_SOUTH = Shapes.or(
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0),
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0)
    );

    private static final VoxelShape HITBOX_CORNER_WEST_NORTH = Shapes.or(
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 0.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0),
            Shapes.box(0.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0)
    );

    private static final VoxelShape HITBOX_CORNER_WEST_SOUTH = Shapes.or(
            Shapes.box(0.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0),
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0)
    );

    private static final VoxelShape HITBOX_CROSS = Shapes.or(
            Shapes.box(0.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0),
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 0.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0)
    );

    private static final VoxelShape HITBOX_T_EAST = Shapes.or(
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0),
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 0.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0)
    );

    private static final VoxelShape HITBOX_T_WEST = Shapes.or(
            Shapes.box(0.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0),
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 0.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0)
    );

    private static final VoxelShape HITBOX_T_SOUTH = Shapes.or(
            Shapes.box(0.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0),
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0)
    );

    private static final VoxelShape HITBOX_T_NORTH = Shapes.or(
            Shapes.box(0.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0),
            Shapes.box(7.0 / 16.0, 0.0 / 16.0, 0.0 / 16.0, 9.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0)
    );

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;

    public NetherBarsBlock()
    {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(10F, 1200F).requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(EAST, false)
        );
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        boolean north = state.getValue(NORTH);
        boolean south = state.getValue(SOUTH);
        boolean west = state.getValue(WEST);
        boolean east = state.getValue(EAST);

        if (north && south && west && east) return HITBOX_CROSS;
        if (north && south && west) return HITBOX_T_WEST;
        if (north && south && east) return HITBOX_T_EAST;
        if (west && east && north) return HITBOX_T_NORTH;
        if (west && east && south) return HITBOX_T_SOUTH;
        if (west && north) return HITBOX_CORNER_WEST_NORTH;
        if (west && south) return HITBOX_CORNER_WEST_SOUTH;
        if (east && north) return HITBOX_CORNER_EAST_NORTH;
        if (east && south) return HITBOX_CORNER_EAST_SOUTH;
        if (north || south) return HITBOX_NORTH_SOUTH;
        if (west || east) return HITBOX_WEST_EAST;

        return HITBOX_COLUMN;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, SOUTH, WEST, EAST);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var level = context.getLevel();
        var pos = context.getClickedPos();

        boolean north = connectsTo(level.getBlockState(pos.north()), level, pos);
        boolean south = connectsTo(level.getBlockState(pos.south()), level, pos);
        boolean west = connectsTo(level.getBlockState(pos.west()), level, pos);
        boolean east = connectsTo(level.getBlockState(pos.east()), level, pos);

        return this.defaultBlockState()
                .setValue(NORTH, north)
                .setValue(SOUTH, south)
                .setValue(WEST, west)
                .setValue(EAST, east);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random)
    {
        boolean north = connectsTo(level.getBlockState(pos.north()), level, pos);
        boolean south = connectsTo(level.getBlockState(pos.south()), level, pos);
        boolean west = connectsTo(level.getBlockState(pos.west()), level, pos);
        boolean east = connectsTo(level.getBlockState(pos.east()), level, pos);

        return state.setValue(NORTH, north)
                .setValue(SOUTH, south)
                .setValue(WEST, west)
                .setValue(EAST, east);
    }

    private boolean connectsTo(BlockState state, LevelReader level, BlockPos pos)
    {
        return (state.getBlock() instanceof NetherBarsBlock) || (state.isSolidRender()); // TODO: state.isSolidRender()
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!Utils.instance.text.showExtendedDescription(tooltipComponents)) return;

        tooltipComponents.add(getDisplayName(1).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.add(getDisplayName(2).withStyle(ChatFormatting.DARK_GRAY));
    }
}
