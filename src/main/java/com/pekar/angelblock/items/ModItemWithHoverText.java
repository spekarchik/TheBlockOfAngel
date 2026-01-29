package com.pekar.angelblock.items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class ModItemWithHoverText extends ModItem implements ITooltipProvider
{
    private final TextStyle descriptionStyle;

    public ModItemWithHoverText(Properties properties)
    {
        this(TextStyle.Regular, properties);
    }

    public ModItemWithHoverText(TextStyle descriptionStyle, Properties properties)
    {
        super(properties);
        this.descriptionStyle = descriptionStyle;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, display, component, flag);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        tooltip.addLine(getDescriptionId()).styledAs(descriptionStyle, true).apply();
    }
}
