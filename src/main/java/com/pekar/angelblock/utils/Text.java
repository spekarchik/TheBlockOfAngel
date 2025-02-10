package com.pekar.angelblock.utils;

import com.pekar.angelblock.TextStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

public class Text
{
    Text()
    {

    }

    public MutableComponent getFormattedTextComponent(MutableComponent initialComponent, boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice)
    {
        TextStyle textStyle;

        if (isHeader) textStyle = TextStyle.Header;
        else if (isSubHeader) textStyle = TextStyle.Subheader;
        else if (isNotice) textStyle = TextStyle.Notice;
        else if (isImportantNotice) textStyle = TextStyle.ImportantNotice;
        else textStyle = TextStyle.Regular;

        return getDescription(initialComponent, textStyle);
    }

    public MutableComponent getDescription(MutableComponent initialComponent, TextStyle textStyle)
    {
        return switch (textStyle)
        {
            case Header -> initialComponent.withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE);
            case Subheader -> initialComponent.withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GRAY);
            case Notice -> initialComponent.withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);
            case ImportantNotice -> initialComponent.withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.WHITE);
            default -> initialComponent.withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY);
        };
    }
}
