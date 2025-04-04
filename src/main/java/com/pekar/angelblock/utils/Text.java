package com.pekar.angelblock.utils;

import com.pekar.angelblock.text.ITooltip;
import com.pekar.angelblock.text.TextStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;

public class Text
{
    Text()
    {

    }

    public MutableComponent getFormattedTextComponent(MutableComponent initialComponent, boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice, boolean isDarkGray)
    {
        TextStyle textStyle;

        if (isHeader) textStyle = TextStyle.Header;
        else if (isSubHeader) textStyle = TextStyle.Subheader;
        else if (isNotice) textStyle = TextStyle.Notice;
        else if (isImportantNotice) textStyle = TextStyle.ImportantNotice;
        else if (isDarkGray) textStyle = TextStyle.DarkGray;
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
            case DarkGray -> initialComponent.withStyle(ChatFormatting.DARK_GRAY);
            default -> initialComponent.withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY);
        };
    }

    public boolean showExtendedDescription(ITooltip tooltip)
    {
        if (!Screen.hasShiftDown())
        {
            tooltip.addLine("description.press_shift").apply();
            return false;
        }

        return true;
    }
}
