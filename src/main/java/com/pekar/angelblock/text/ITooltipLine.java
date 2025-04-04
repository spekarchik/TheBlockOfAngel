package com.pekar.angelblock.text;

import net.minecraft.ChatFormatting;

public interface ITooltipLine
{
    ITooltipLine styledAs(TextStyle style, boolean applyStyle);
    ITooltipLine asHeader();
    ITooltipLine asSubHeader();
    ITooltipLine asNotice();
    ITooltipLine asImportantNotice();
    ITooltipLine asDarkGrey();
    ITooltipLine withFormatting(ChatFormatting formatting);
    void apply();
}
