package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.SuperAxeProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SuperAxe extends ModAxe
{
    public SuperAxe(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new SuperAxeProperties());
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player)
    {
        if (canPreventBlockDestroying(player, pos) && !materialProperties.isSafeToBreak(player, pos)) return true;
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity)
    {
        if (!level.isClientSide)
            mineAdditionalBlocks(level, pos, livingEntity);
        return super.mineBlock(itemStack, level, blockState, pos, livingEntity);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
    {
        if (blockState.getBlock() == Blocks.CACTUS) return 12.0F;
        return super.getDestroySpeed(itemStack, blockState);
    }

    @Override
    public boolean isEnhancedTool()
    {
        return true;
    }

    @Override
    protected void mineAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (level.isClientSide || !isEnhancedTool() || !isToolEffective(entityLiving, pos)) return;

        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        BlockState blockState = level.getBlockState(pos);
        float initialHardness = blockState.getBlock().defaultDestroyTime();

        if (initialHardness == 0.0F)
            return;

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

        super.mineAdditionalBlocks(level, pos, entityLiving);
    }

    private boolean canProceed(LivingEntity entityLiving, BlockPos pos)
    {
        return !entityLiving.level.isEmptyBlock(pos) && isToolEffective(entityLiving, pos);
    }
}
