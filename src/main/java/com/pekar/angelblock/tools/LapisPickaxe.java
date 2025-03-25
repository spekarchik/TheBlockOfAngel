package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.LapisMaterialProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class LapisPickaxe extends EnhancedPickaxe
{
    public LapisPickaxe(ModToolMaterial material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LapisMaterialProperties());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 0; i <= 6; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1, false, i == 3, false, i == 5));
        }
    }
}
