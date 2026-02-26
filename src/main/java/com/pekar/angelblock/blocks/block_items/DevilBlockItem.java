package com.pekar.angelblock.blocks.block_items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class DevilBlockItem extends ModBlockItem implements ITooltipProvider
{
    public DevilBlockItem(Block block, Properties properties)
    {
        super(block, properties.fireResistant().stacksTo(1));
    }

    @Override
    public void addTooltip(ItemStack stack, Item.TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!flag.hasShiftDown() && !flag.hasAltDown())
        {
            tooltip.addLineById("description.common.press_shift_or_alt").apply();
            return;
        }

        tooltip.ignoreEmptyLines();

        if (flag.hasShiftDown())
        {
            for (int i = 1; i <= 18; i++)
            {
                tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Header, i == 3).apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.common.press_alt").apply();
        }
        else if (flag.hasAltDown())
        {
            for (int i = 19; i <= 37; i++)
            {
                tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Header, i == 19).styledAs(TextStyle.DarkGray, i >= 34).apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.common.press_shift").apply();
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, display, component, flag);
    }
}
