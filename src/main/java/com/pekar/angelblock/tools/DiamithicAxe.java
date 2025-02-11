package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.DiamithicMaterialProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class DiamithicAxe extends EnhancedAxe
{
    public DiamithicAxe(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new DiamithicMaterialProperties());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        for (int i = 0; i <= 5; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1, false, i == 4));
        }
    }
}
