package com.pekar.angelblock.items;

import net.minecraft.network.chat.MutableComponent;

public class ModItemWithMultipleHoverText extends ModItemWithDoubleHoverText
{
    public ModItemWithMultipleHoverText(Properties properties)
    {
        super(properties);
    }

    protected MutableComponent getDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice)
    {
        var component = getDisplayName(lineNumber);
        return utils.text.getFormattedTextComponent(component, isHeader, isSubHeader, isNotice, isImportantNotice);
    }

    protected MutableComponent getDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice)
    {
        return getDescription(lineNumber, isHeader, isSubHeader, isNotice, false);
    }

    protected MutableComponent getDescription(int lineNumber, boolean isHeader, boolean isSubHeader)
    {
        return getDescription(lineNumber, isHeader, isSubHeader, false);
    }

    protected MutableComponent getDescription(int lineNumber, boolean isHeader)
    {
        return getDescription(lineNumber, isHeader, false);
    }
}
