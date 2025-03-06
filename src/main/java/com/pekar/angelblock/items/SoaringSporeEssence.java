package com.pekar.angelblock.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SoaringSporeEssence extends ModItemWithHoverText
{
    public SoaringSporeEssence()
    {
        super(new Properties().stacksTo(4));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(components)) return;

        components.add(getDisplayName().withStyle(ChatFormatting.GRAY));
    }
}
