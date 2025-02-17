package com.pekar.angelblock.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class WorkRod extends ModRod
{
    public WorkRod(Tier material, Properties properties)
    {
        super(material, false, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        for (int i = 1; i <= 3; i++)
        {
            var component = getDisplayName(i).withStyle(ChatFormatting.GRAY);
            if (i > 2)
                component.withStyle(ChatFormatting.ITALIC);
            tooltipComponents.add(component);
        }
    }
}
