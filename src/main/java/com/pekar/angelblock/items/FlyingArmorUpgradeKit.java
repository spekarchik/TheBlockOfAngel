package com.pekar.angelblock.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class FlyingArmorUpgradeKit extends ModItemWithMultipleHoverText
{
    public FlyingArmorUpgradeKit(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 1; i <= 3; i++)
        {
            tooltipComponents.add(getDescription(i, false, false, i == 2 || i == 3, false));
        }
    }
}
