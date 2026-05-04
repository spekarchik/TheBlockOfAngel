package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.LimoniteAxeProperties;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LimoniteAxe extends EnhancedAxe
{
    public LimoniteAxe(ModToolMaterial material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LimoniteAxeProperties());
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
    {
        if (hasCriticalDamage(itemStack)) return 1F;

        if (blockState.getBlock() == Blocks.CACTUS) return 12.0F;
        return super.getDestroySpeed(itemStack, blockState);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

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
    protected void mineAdditionalBlocks(ItemStack itemStack, Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (!isEnhanced() || !entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT))
            return;

        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (!isToolEffective(entityLiving, pos)) return;

        if (isCorrectToolForDrops(itemStack, blockState) && !isCompatiblePlant(itemStack, blockState))
        {
            int increment = 1;
            while (canProceed(entityLiving, pos.above(increment)))
            {
                onBlockMining(itemStack, level, pos.above(increment++), block, entityLiving);
            }

            increment = 1;
            while (canProceed(entityLiving, pos.below(increment)))
            {
                onBlockMining(itemStack, level, pos.below(increment++), block, entityLiving);
            }
        }

        if (isCompatiblePlant(itemStack, blockState))
            super.mineAdditionalBlocks(itemStack, level, pos, entityLiving);
    }

    private boolean canProceed(LivingEntity entityLiving, BlockPos pos)
    {
        return !entityLiving.level().isEmptyBlock(pos) && isToolEffective(entityLiving, pos);
    }
}
