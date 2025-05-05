package com.pekar.angelblock.blocks.block_items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class AngelBlockItem extends ModBlockItem implements ITooltipProvider
{
    public AngelBlockItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, display, component, flag);
    }

    @Override
    public void addTooltip(ItemStack stack, Item.TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!Screen.hasShiftDown() && !Screen.hasAltDown())
        {
            tooltip.addLineById("description.common.press_shift_or_alt").apply();
            return;
        }

        tooltip.ignoreEmptyLines();

        if (Screen.hasShiftDown())
        {
            for (int i = 1; i <= 19; i++)
            {
                tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Header, i == 3).styledAs(TextStyle.Notice, i == 17).styledAs(TextStyle.DarkGray, i == 18).apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.common.press_alt").apply();
        }
        else if (Screen.hasAltDown())
        {
            tooltip.addEmptyLine();

            for (int i = 21; i <= 28; i++)
            {
                tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Header, i == 21).styledAs(TextStyle.DarkGray, i == 28).apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.common.press_shift").apply();
        }
    }
}
