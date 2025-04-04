package com.pekar.angelblock.text;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.CheckReturnValue;

public class TooltipLine implements ITooltipLine
{
    private final Tooltip tooltip;
    private final MutableComponent component;
    private final boolean ignoreEmptyLines;

    TooltipLine(Tooltip tooltip, String descriptionRoot, int descNumber, boolean ignoreEmptyLines)
    {
        this.tooltip = tooltip;
        this.ignoreEmptyLines = ignoreEmptyLines;
        this.component = Component.translatable(descriptionRoot + ".desc" + descNumber);
    }

    TooltipLine(Tooltip tooltip, String descriptionRoot, boolean ignoreEmptyLines)
    {
        this.tooltip = tooltip;
        this.component = Component.translatable(descriptionRoot + ".desc");
        this.ignoreEmptyLines = ignoreEmptyLines;
    }

    TooltipLine(Tooltip tooltip)
    {
        this.tooltip = tooltip;
        this.component = Component.empty();
        this.ignoreEmptyLines = false;
    }

    @CheckReturnValue
    @Override
    public ITooltipLine styledAs(TextStyle style, boolean applyStyle)
    {
        if (applyStyle)
        {
            switch (style)
            {
                case Header -> component.withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE);
                case Subheader -> component.withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GRAY);
                case Notice -> component.withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);
                case ImportantNotice -> component.withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.WHITE);
                case DarkGray -> component.withStyle(ChatFormatting.DARK_GRAY);
                default -> component.withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY);
            }
        }
        return this;
    }

    @CheckReturnValue
    @Override
    public final ITooltipLine asHeader()
    {
        return styledAs(TextStyle.Header);
    }

    @CheckReturnValue
    @Override
    public final ITooltipLine asSubHeader()
    {
        return styledAs(TextStyle.Subheader);
    }

    @CheckReturnValue
    @Override
    public final ITooltipLine asNotice()
    {
        return styledAs(TextStyle.Notice);
    }

    @CheckReturnValue
    @Override
    public final ITooltipLine asImportantNotice()
    {
        return styledAs(TextStyle.ImportantNotice);
    }

    @CheckReturnValue
    @Override
    public final ITooltipLine asDarkGrey()
    {
        return styledAs(TextStyle.DarkGray);
    }

    @CheckReturnValue
    @Override
    public final ITooltipLine withFormatting(ChatFormatting formatting)
    {
        component.withStyle(formatting);
        return this;
    }

    @Override
    public final void apply()
    {
        if (isEmpty() && ignoreEmptyLines) return;
        tooltip.apply(this);
    }

    final Component getComponent()
    {
        return component;
    }

    private boolean isEmpty()
    {
        return component.getString().isEmpty();
    }

    private ITooltipLine styledAs(TextStyle style)
    {
        return styledAs(style, true);
    }
}
