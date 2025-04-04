package com.pekar.angelblock.items;

import com.pekar.angelblock.text.ITooltip;
import com.pekar.angelblock.text.TextStyle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class SaltPeter extends ModItemWithHoverText
{
    public SaltPeter(Properties properties)
    {
        super(TextStyle.Notice, properties);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;
        super.addTooltip(stack, context, tooltip, flag);
    }
}
