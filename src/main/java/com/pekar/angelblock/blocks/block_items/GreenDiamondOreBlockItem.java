package com.pekar.angelblock.blocks.block_items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class GreenDiamondOreBlockItem extends ModBlockItem implements ITooltipProvider
{
    public GreenDiamondOreBlockItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!Utils.instance.text.showExtendedDescription(tooltip)) return;
        tooltip.addLine(getDescriptionId(), 1).asNotice().apply();
        tooltip.addEmptyLine();

        for (int i = 2; i <= 7; i++)
        {
            tooltip.addLine(getDescriptionId(), i).asDarkGrey().apply();
            if (i == 2) tooltip.addEmptyLine();
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, components, flag);
    }
}
