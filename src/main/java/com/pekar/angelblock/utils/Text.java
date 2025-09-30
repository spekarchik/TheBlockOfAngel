package com.pekar.angelblock.utils;

import com.pekar.angelblock.tooltip.ITooltip;
import net.minecraft.world.item.TooltipFlag;

public class Text
{
    Text()
    {

    }

    public boolean showExtendedDescription(ITooltip tooltip, TooltipFlag flag)
    {
        if (!flag.hasShiftDown())
        {
            tooltip.addLineById("description.press_shift").apply();
            return false;
        }

        return true;
    }
}
