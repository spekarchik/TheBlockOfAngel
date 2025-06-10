package com.pekar.angelblock.tools;

import net.minecraft.world.item.Tier;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public abstract class WorkRod extends ModRod implements ITooltipProvider
{
    public WorkRod(Tier material, Properties properties)
    {
        super(material, false, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, tooltipComponents, tooltipFlag);
    }
}
