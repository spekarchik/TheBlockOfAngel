package com.pekar.angelblock.blocks.block_items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class GreenDiamondOreBlockItem extends ModBlockItem implements ITooltipProvider
{
    public GreenDiamondOreBlockItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void addTooltip(ItemStack stack, Item.TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!Utils.instance.text.showExtendedDescription(tooltip)) return;
        tooltip.addLine(getDescriptionId()).asNotice().apply();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, display, component, flag);
    }
}
