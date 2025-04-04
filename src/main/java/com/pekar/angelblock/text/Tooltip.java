package com.pekar.angelblock.text;

import net.minecraft.network.chat.Component;

import javax.annotation.CheckReturnValue;
import java.util.function.Consumer;

public class Tooltip implements ITooltip
{
    private final Consumer<Component> tooltipComponent;
    private boolean ignoreEmptyLines;

    private Tooltip(Consumer<Component> tooltipComponent)
    {
        this.tooltipComponent = tooltipComponent;
    }

    public static ITooltip create(Consumer<Component> tooltipComponent)
    {
        return new Tooltip(tooltipComponent);
    }

    @Override
    public ITooltip ignoreEmptyLines()
    {
        ignoreEmptyLines = true;
        return this;
    }

    @Override
    public ITooltip includeEmptyLines()
    {
        ignoreEmptyLines = false;
        return this;
    }

    @CheckReturnValue
    @Override
    public void addEmptyLine()
    {
        tooltipComponent.accept(new TooltipLine(this).getComponent());
    }

    @CheckReturnValue
    @Override
    public ITooltipLine addLine(String descriptionRoot)
    {
        return new TooltipLine(this, descriptionRoot, ignoreEmptyLines);
    }

    @CheckReturnValue
    @Override
    public ITooltipLine addLine(String descriptionRoot, int descNumber)
    {
        return new TooltipLine(this, descriptionRoot, descNumber, ignoreEmptyLines);
    }

    void apply(TooltipLine tooltipLine)
    {
        tooltipComponent.accept(tooltipLine.getComponent());
    }
}
