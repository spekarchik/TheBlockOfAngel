package com.pekar.angelblock.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModBlockWithTripleHoverText extends ModBlockWithDoubleHoverText
{
    public ModBlockWithTripleHoverText(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> components, TooltipFlag tooltipFlag)
    {
        for (int i = 1; i <= 3; i++)
        {
            var component = getDisplayName(i).withStyle(ChatFormatting.GRAY);
            if (i == 1)
                component.withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.WHITE);
            components.add(component);
        }
    }
}
