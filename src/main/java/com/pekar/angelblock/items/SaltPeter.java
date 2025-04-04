package com.pekar.angelblock.items;

import com.pekar.angelblock.text.TextStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SaltPeter extends ModItemWithHoverText
{
    public SaltPeter(Properties properties)
    {
        super(TextStyle.Notice, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(components)) return;

        super.appendHoverText(stack, context, components, tooltipFlag);
    }
}
