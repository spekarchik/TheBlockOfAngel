package com.pekar.angelblock.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ModItemWithDoubleHoverText extends ModItem
{
    public ModItemWithDoubleHoverText(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 1; i <= 2; i++)
        {
            var component = getDisplayName(i).withStyle(ChatFormatting.GRAY);
            if (i > 1)
                component.withStyle(ChatFormatting.ITALIC);
            tooltipComponents.add(component);
        }
    }

    protected MutableComponent getDisplayName(int lineNumber)
    {
        return Component.translatable(this.getDescriptionId() + ".desc" + lineNumber);
    }
}
