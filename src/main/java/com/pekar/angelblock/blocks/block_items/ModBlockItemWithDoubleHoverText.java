package com.pekar.angelblock.blocks.block_items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

public class ModBlockItemWithDoubleHoverText extends ModBlockItem implements ITooltipProvider
{

    public ModBlockItemWithDoubleHoverText(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!Utils.instance.text.showExtendedDescription(tooltip)) return;

        for (int i = 1; i <= 2; i++)
        {
            tooltip.addLine(asItem().getDescriptionId(), i).styledAs(TextStyle.ImportantNotice, i == 1).apply();
        }
    }
}
