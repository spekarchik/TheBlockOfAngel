package com.pekar.angelblock.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GreenDiamondBlock extends ModDropExperienceBlockWithHoverText
{
    public static final BooleanProperty IS_DARK = BooleanProperty.create("is_dark");

    public GreenDiamondBlock()
    {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).strength(1F)
                .lightLevel(state -> 3).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops());

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

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        tooltipComponents.add(getDisplayName().withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
