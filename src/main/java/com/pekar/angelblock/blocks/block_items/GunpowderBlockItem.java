package com.pekar.angelblock.blocks.block_items;

import com.pekar.angelblock.text.ITooltip;
import com.pekar.angelblock.text.ITooltipProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class GunpowderBlockItem extends ModBlockItem implements ITooltipProvider
{
    public GunpowderBlockItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, display, component, flag);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        tooltip.addLine(getDescriptionId()).asDarkGrey().apply();
    }
}
