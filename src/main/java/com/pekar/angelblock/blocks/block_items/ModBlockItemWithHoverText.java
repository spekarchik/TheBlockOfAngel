package com.pekar.angelblock.blocks.block_items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ModBlockItemWithHoverText extends ModBlockItem implements ITooltipProvider
{
    public ModBlockItemWithHoverText(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        tooltip.addLine(getDescriptionId()).asNotice().apply();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, components, flag);
    }
}
