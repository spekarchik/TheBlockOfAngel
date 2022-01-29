package com.pekar.angelblock.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LimonitePickaxe extends ModPickaxe
{
    public LimonitePickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties)
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
        Block block = blockState.getBlock();

        // stones
        if (block == Blocks.COBBLESTONE || block == Blocks.STONE || block == Blocks.COBBLED_DEEPSLATE
            || block == Blocks.DEEPSLATE)
        {
            level.setBlock(pos, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (block == Blocks.MOSSY_COBBLESTONE)
        {
            var resultBlock = pos.getY() > 4 ? Blocks.COBBLESTONE : Blocks.COBBLED_DEEPSLATE;
            level.setBlock(pos, resultBlock.defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (block instanceof InfestedBlock infestedBlock)
        {
            level.setBlock(pos, infestedBlock.getHostBlock().defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (block == Blocks.COBBLESTONE_SLAB)
        {
            level.setBlock(pos, Blocks.MOSSY_COBBLESTONE_SLAB.defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (block == Blocks.MOSSY_COBBLESTONE_SLAB)
        {
            level.setBlock(pos, Blocks.COBBLESTONE_SLAB.defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        // bricks
        if (block == Blocks.STONE_BRICKS)
        {
            level.setBlock(pos, Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (block == Blocks.MOSSY_STONE_BRICKS)
        {
            level.setBlock(pos, Blocks.STONE_BRICKS.defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (block == Blocks.STONE_BRICK_SLAB)
        {
            level.setBlock(pos, Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (block == Blocks.MOSSY_STONE_BRICK_SLAB)
        {
            level.setBlock(pos, Blocks.STONE_BRICK_SLAB.defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (isToolEffective(level, pos) && !Utils.isFallSafeExact(player, pos) && !player.hasEffect(MobEffects.DIG_SLOWDOWN))
        {
            level.destroyBlock(pos, true);

            if (blockState.getDestroySpeed(level, pos) != 0.0F)
            {
                damageItem(1, player);
            }

            return InteractionResult.CONSUME;
        }

        return super.useOn(context);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player)
    {
        if (isToolEffective(player.level, pos) && !Utils.isFallSafeExact(player, pos)) return true;
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity)
    {
        processAdditionalBlocks(level, pos, livingEntity);
        return super.mineBlock(itemStack, level, blockState, pos, livingEntity);
    }

    @Override
    public boolean isEnhancedTool()
    {
        return true;
    }

    @Override
    protected void onBlockProcessing(Level level, BlockState initialBlockState, float initialHardness, BlockPos pos, LivingEntity entityLiving)
    {
        BlockState blockState = level.getBlockState(pos);
        float hardness = blockState.getBlock().defaultDestroyTime();

        if (hardness <= initialHardness && isToolEffective(level, pos) && Utils.isFallSafeExact(entityLiving, pos))
        {
            level.destroyBlock(pos, true);
            damageItem(1, entityLiving);
        }
    }
}
