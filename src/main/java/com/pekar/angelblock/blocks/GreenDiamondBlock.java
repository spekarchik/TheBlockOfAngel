package com.pekar.angelblock.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.List;

public class GreenDiamondBlock extends ModDropExperienceBlockWithHoverText
{
    public GreenDiamondBlock()
    {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE).strength(1F)
                .lightLevel(state -> 12).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops());
    }

    @Override
    protected IntProvider getXpRange()
    {
        return UniformInt.of(5, 10);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        tooltipComponents.add(getDisplayName().withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    }
}
