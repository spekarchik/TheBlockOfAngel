package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.tools.properties.SuperMaterialProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class SuperPickaxe extends EnhancedPickaxe
{
    public SuperPickaxe(ModToolMaterial material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new SuperMaterialProperties());
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 14; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1)
                    .styledAs(TextStyle.Notice, i == 8)
                    .styledAs(TextStyle.DarkGray, i >= 12 && i <= 13)
                    .apply();
        }
    }
}
