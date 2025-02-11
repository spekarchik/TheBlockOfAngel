package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.network.chat.MutableComponent;

public interface IModDescriptionItem
{

    default MutableComponent getDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice)
    {
        var component = getDisplayName(lineNumber);
        return Utils.instance.text.getFormattedTextComponent(component, isHeader, isSubHeader, isNotice, isImportantNotice);
    }

    default MutableComponent getDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice)
    {
        return getDescription(lineNumber, isHeader, isSubHeader, isNotice, false);
    }

    default MutableComponent getDescription(int lineNumber, boolean isHeader, boolean isSubHeader)
    {
        return getDescription(lineNumber, isHeader, isSubHeader, false);
    }

    default MutableComponent getDescription(int lineNumber, boolean isHeader)
    {
        return getDescription(lineNumber, isHeader, false);
    }

    MutableComponent getDisplayName(int lineNumber);
}
