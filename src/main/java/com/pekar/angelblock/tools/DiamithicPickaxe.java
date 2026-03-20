package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.tools.properties.DiamithicMaterialProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class DiamithicPickaxe extends EnhancedPickaxe
{
    public DiamithicPickaxe(ModToolMaterial material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new DiamithicMaterialProperties());
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        for (int i = 0; i <= 9; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1)
                    .styledAs(TextStyle.Notice, i == 4)
                    .styledAs(TextStyle.DarkGray, i >= 6 && i <= 8)
                    .apply();
        }
    }
}
