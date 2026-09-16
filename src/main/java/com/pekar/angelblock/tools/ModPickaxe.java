package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ItemAbility;

public class ModPickaxe extends ModMiningTool
{
    public static ModPickaxe createPrimary(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModPickaxe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModPickaxe(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, properties.pickaxe(material.getVanillaMaterial(), attackDamage, attackSpeed), materialProperties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility)
    {
        return !hasCriticalDamage(stack) && false;
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 3; i++)
        {
            tooltip.addLine(getDescriptionId(), i).styledAs(TextStyle.DarkGray, i >= 1 && i <= 2).apply();
        }
    }
}
