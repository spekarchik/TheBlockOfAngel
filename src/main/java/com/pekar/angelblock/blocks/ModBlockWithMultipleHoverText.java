package com.pekar.angelblock.blocks;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;

public class ModBlockWithMultipleHoverText extends Block
{
    protected final Utils utils = new Utils();

    public ModBlockWithMultipleHoverText(Properties properties)
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

    private MutableComponent getDisplayName(int lineNumber)
    {
        return Component.translatable(this.getDescriptionId() + ".desc" + lineNumber);
    }
}
