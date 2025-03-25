package com.pekar.angelblock.blocks;

import net.minecraft.network.chat.MutableComponent;

public class ModBlockWithMultipleHoverText extends ModBlockWithDoubleHoverText
{
    public ModBlockWithMultipleHoverText(Properties properties)
    {
        super(properties);
    }

    protected MutableComponent getDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice, boolean isDarkGray)
    {
        var component = getDisplayName(lineNumber);
        return utils.text.getFormattedTextComponent(component, isHeader, isSubHeader, isNotice, isImportantNotice, isDarkGray);
    }

    protected MutableComponent getDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice)
    {
        var component = getDisplayName(lineNumber);
        return utils.text.getFormattedTextComponent(component, isHeader, isSubHeader, isNotice, isImportantNotice, false);
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
