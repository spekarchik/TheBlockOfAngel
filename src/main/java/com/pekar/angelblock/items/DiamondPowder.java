package com.pekar.angelblock.items;

import com.pekar.angelblock.TextStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DiamondPowder extends ModItemWithHoverText
{
    public DiamondPowder()
    {
        super(TextStyle.Notice);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(components)) return;

        super.appendHoverText(stack, context, components, tooltipFlag);
    }
}
