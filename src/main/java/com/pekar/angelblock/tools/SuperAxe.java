package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.SuperAxeProperties;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SuperAxe extends EnhancedAxe
{
    public SuperAxe(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new SuperAxeProperties());
    }

    @Override
    protected boolean supportsVerticalMining()
    {
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack tool, BlockState blockState)
    {
        if (hasCriticalDamage(tool)) return 1F;
        if (blockState.getBlock() == Blocks.CACTUS) return 12F;
        return super.getDestroySpeed(tool, blockState);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 0; i <= 11; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1)
                    .styledAs(TextStyle.Notice, i == 6)
                    .styledAs(TextStyle.DarkGray, i >= 9 && i <= 10)
                    .apply();
        }
    }
}
