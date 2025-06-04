package com.pekar.angelblock.blocks;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class GreenDiamondBlock extends ModDropExperienceBlock
{
    public static final BooleanProperty IS_DARK = BooleanProperty.create("is_dark");

    public GreenDiamondBlock(Properties properties)
    {
        super(properties);

        registerDefaultState(getStateDefinition().any().setValue(IS_DARK, false));
    }

    @Override
    protected IntProvider getXpRange()
    {
        return UniformInt.of(5, 10);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {
        var isDark = context.getClickedPos().getY() < 0;
        return getStateDefinition().any().setValue(IS_DARK, isDark);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(IS_DARK);
    }
}
