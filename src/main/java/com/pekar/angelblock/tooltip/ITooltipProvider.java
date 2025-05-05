package com.pekar.angelblock.tooltip;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.function.Consumer;

public interface ITooltipProvider
{
    void addTooltip(ItemStack stack, Item.TooltipContext context, ITooltip tooltip, TooltipFlag flag);

    static void appendHoverText(ITooltipProvider tooltipProvider, ItemStack stack, Item.TooltipContext context, Consumer<Component> tooltipConsumer, TooltipFlag flag)
    {
        tooltipProvider.addTooltip(stack, context, Tooltip.create(tooltipConsumer), flag);
    }
}
