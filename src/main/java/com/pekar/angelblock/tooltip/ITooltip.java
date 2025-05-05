package com.pekar.angelblock.tooltip;

public interface ITooltip
{
    ITooltip ignoreEmptyLines();
    ITooltip includeEmptyLines();
    void addEmptyLine();
    ITooltipLine addLineById(String descriptionId);
    ITooltipLine addLine(String descriptionRoot);
    ITooltipLine addLine(String descriptionRoot, int descNumber);
}
