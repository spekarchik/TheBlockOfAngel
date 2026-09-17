package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.LimoniteAxeProperties;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LimoniteAxe extends EnhancedAxe
{
    public LimoniteAxe(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LimoniteAxeProperties());
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

        for (int i = 0; i <= 8; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1)
                    .styledAs(TextStyle.Notice, i == 3)
                    .styledAs(TextStyle.DarkGray, i >= 6 && i <= 7)
                    .apply();
        }
    }

    @Override
    protected boolean shouldMineAdditionalBlocksAround(ItemStack tool, BlockState blockState)
    {
        return isCompatiblePlant(tool, blockState);
    }
}
