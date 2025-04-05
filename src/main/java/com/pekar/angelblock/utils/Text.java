package com.pekar.angelblock.utils;

import com.pekar.angelblock.text.ITooltip;
import net.minecraft.client.gui.screens.Screen;

public class Text
{
    Text()
    {

    }

    public boolean showExtendedDescription(ITooltip tooltip)
    {
        if (!Screen.hasShiftDown())
        {
            tooltip.addLineById("description.press_shift").apply();
            return false;
        }

        return true;
    }
}
