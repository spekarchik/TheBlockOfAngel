package com.pekar.angelblock.text;

public interface ITooltip
{
    ITooltip ignoreEmptyLines();
    ITooltip includeEmptyLines();
    void addEmptyLine();
    ITooltipLine addLine(String descriptionRoot);
    ITooltipLine addLine(String descriptionRoot, int descNumber);
}
