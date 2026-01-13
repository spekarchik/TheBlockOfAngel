package com.pekar.angelblock.items;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ModArmorUpgradeKit extends ModItem implements ITooltipProvider
{
    public ModArmorUpgradeKit(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, component, flag);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!flag.hasShiftDown() && !flag.hasAltDown())
        {
            tooltip.addLineById("description.common.press_shift_or_alt").apply();
            return;
        }

        tooltip.ignoreEmptyLines();

        if (flag.hasShiftDown())
        {
            for (int i = 1; i <= 3; i++)
            {
                tooltip.addLine(getDescriptionId(), i)
                        .withFormatting(ChatFormatting.GOLD, i == 1)
                        .styledAs(TextStyle.Notice, i == 2 || i == 3)
                        .apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.upgrade_kit.press_alt").apply();
        }
        else if (flag.hasAltDown())
        {
            tooltip.addLine(getDescriptionId(), 11)
                    .withFormatting(ChatFormatting.DARK_AQUA, true)
                    .apply();

            tooltip.addEmptyLine();

            for (int i = 12; i <= 25; i++)
            {
                tooltip.addLine(getDescriptionId(), i)
                        .apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.common.press_shift").apply();
        }
    }
}
