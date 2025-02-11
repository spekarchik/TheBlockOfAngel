package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.SuperAxeProperties;
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

public class SuperAxe extends EnhancedAxe
{
    public SuperAxe(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new SuperAxeProperties());
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
        for (int i = 0; i <= 8; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1, false, i == 6));
        }
    }

    @Override
    protected void mineAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (!isEnhancedTool() || !entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT))
            return;

        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (!isToolEffective(entityLiving, pos) /*&& !isCompatiblePlant(block)*/)
            return;

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

        super.mineAdditionalBlocks(level, pos, entityLiving);
    }

    private boolean canProceed(LivingEntity entityLiving, BlockPos pos)
    {
        return !entityLiving.level().isEmptyBlock(pos) && isToolEffective(entityLiving, pos);
    }
}
