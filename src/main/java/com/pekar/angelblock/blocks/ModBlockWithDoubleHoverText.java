package com.pekar.angelblock.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ModBlockWithDoubleHoverText extends Block
{
    public ModBlockWithDoubleHoverText(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        for (int i = 1; i <= 2; i++)
        {
            var component = getDisplayName(i).withStyle(ChatFormatting.GRAY);
            if (i == 1)
                component.withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.WHITE);
            components.add(component);
        }
    }

    protected MutableComponent getDisplayName(int lineNumber)
    {
        return Component.translatable(this.getDescriptionId() + ".desc" + lineNumber);
    }
}
