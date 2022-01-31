package com.pekar.angelblock.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LapisHoe extends ModHoe
{
    public LapisHoe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;

        if (level.isClientSide) return InteractionResult.PASS;
        if (!canUseToolEffect(player)) return InteractionResult.PASS;

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        BlockPos upPos = pos.above();

        if (level.isWaterAt(upPos) || ((level.isEmptyBlock(upPos))
            && ((isFarmTypeBlock(level, upPos.north()) && isFarmTypeBlock(level, upPos.south()))
            || (isFarmTypeBlock(level, upPos.east()) && isFarmTypeBlock(level, upPos.west())))))
        {
            level.setBlock(upPos, Blocks.WATER.defaultBlockState(), 11);

            damageItemIfSurvival(player, level, pos, blockState); // pos, not upPos

            if (!updateNeighbors(level, upPos))
            {
                return InteractionResult.FAIL;
            }

            return InteractionResult.CONSUME;
        }

        var result = super.useOn(context);

        if (result.shouldAwardStats())
        {
            processAdditionalBlocks(player, level, pos, context.getClickedFace());
        }

        return result;
    }

    @Override
    protected void onBlockProcessing(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (level.isEmptyBlock(pos.above()))
        {
            if (canBeFarmland(block))
            {
                level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 11);
                damageItemIfSurvival(player, level, pos, blockState);
            }
            else if (block == Blocks.COARSE_DIRT)
            {
                level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 11);
                damageItemIfSurvival(player, level, pos, blockState);
            }
        }
    }

    @Override
    public boolean isEnhancedTool()
    {
        return true;
    }
}
