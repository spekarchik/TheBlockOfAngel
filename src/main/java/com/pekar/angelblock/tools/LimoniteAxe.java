package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.LimoniteAxeProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class LimoniteAxe extends EnhancedAxe
{
    public LimoniteAxe(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LimoniteAxeProperties());
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
    {
        if (blockState.getBlock() == Blocks.CACTUS) return 12.0F;
        return super.getDestroySpeed(itemStack, blockState);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 0; i <= 5; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1, false, i == 3));
        }
    }

    @Override
    protected void mineAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (!isEnhanced() || !entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT))
            return;

        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (!isToolEffective(entityLiving, pos)) return;

        float initialHardness = block.defaultDestroyTime();

        if (initialHardness != 0.0F && !isCompatiblePlant(block))
        {
            int increment = 1;
            while (canProceed(entityLiving, pos.above(increment)))
            {
                onBlockMining(level, blockState, initialHardness, pos.above(increment++), entityLiving);
            }

            increment = 1;
            while (canProceed(entityLiving, pos.below(increment)))
            {
                onBlockMining(level, blockState, initialHardness, pos.below(increment++), entityLiving);
            }
        }

        if (isCompatiblePlant(block))
            super.mineAdditionalBlocks(level, pos, entityLiving);
    }

    private boolean canProceed(LivingEntity entityLiving, BlockPos pos)
    {
        return !entityLiving.level().isEmptyBlock(pos) && isToolEffective(entityLiving, pos);
    }
}
