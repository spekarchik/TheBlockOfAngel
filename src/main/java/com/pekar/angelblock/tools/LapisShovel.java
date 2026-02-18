package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.LapisMaterialProperties;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class LapisShovel extends EnhancedShovel
{
    public LapisShovel(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LapisMaterialProperties());
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 9; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1 || i == 6)
                    .styledAs(TextStyle.Notice, i == 4)
                    .styledAs(TextStyle.DarkGray, i == 8)
                    .apply();
        }
    }
}
