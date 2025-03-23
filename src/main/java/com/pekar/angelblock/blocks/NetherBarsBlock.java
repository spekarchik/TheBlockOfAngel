package com.pekar.angelblock.blocks;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetherBarsBlock extends ModBlockWithDoubleHoverText
{
    private static final VoxelShape HITBOX_Z = Shapes.box(
            0.0 / 16.0, 0.0 / 16.0, 7.0 / 16.0,
            16.0 / 16.0, 16.0 / 16.0, 9.0 / 16.0
    );

    private static final VoxelShape HITBOX_X = Shapes.box(
            7.0 / 16.0, 0.0 / 16.0, 0.0 / 16.0,
            9.0 / 16.0, 16.0 / 16.0, 16.0 / 16.0 
    );

    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    public NetherBarsBlock()
    {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).strength(10F, 1200F).requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_AXIS, Direction.Axis.X));
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return (state.getValue(HORIZONTAL_AXIS) == Direction.Axis.X) ? HITBOX_X : HITBOX_Z;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_AXIS);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var axis = context.getHorizontalDirection().getAxis();
        return defaultBlockState().setValue(HORIZONTAL_AXIS, axis);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!Utils.instance.text.showExtendedDescription(tooltipComponents)) return;

        tooltipComponents.add(getDisplayName(1).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.add(getDisplayName(2).withStyle(ChatFormatting.DARK_GRAY));
    }
}
