package com.pekar.angelblock.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SoaringSporeEssence extends ModItemWithMultipleHoverText
{
    public SoaringSporeEssence()
    {
        super(new Properties().stacksTo(4));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 1; i <= 4; i++)
        {
            var component = getDescription(i, false);
            tooltipComponents.add(component);
        }
    }
}
