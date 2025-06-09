package com.pekar.angelblock.items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class RodUpgradeKit extends ModItem implements ITooltipProvider
{
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, component, flag);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        for (int i = 1; i <= 3; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.Notice, i > 1).apply();
        }
    }
}
