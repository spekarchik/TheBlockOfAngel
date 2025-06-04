package com.pekar.angelblock.blocks.block_items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class DevilBlockItem extends ModBlockItem implements ITooltipProvider
{
    public DevilBlockItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!Screen.hasShiftDown() && !Screen.hasAltDown())
        {
            tooltip.addLineById("description.common.press_shift_or_alt").apply();
            return;
        }

        tooltip.ignoreEmptyLines();

        if (Screen.hasShiftDown())
        {
            for (int i = 1; i <= 16; i++)
            {
                tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Header, i == 3).apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.common.press_alt").apply();
        }
        else if (Screen.hasAltDown())
        {
            for (int i = 17; i <= 35; i++)
            {
                tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Header, i == 17).styledAs(TextStyle.DarkGray, i >= 32).apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.common.press_shift").apply();
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, components, flag);
    }
}
