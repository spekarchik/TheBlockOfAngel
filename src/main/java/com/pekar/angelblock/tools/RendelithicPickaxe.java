package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
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
import net.minecraft.world.level.block.state.BlockState;

public class RendelithicPickaxe extends ModPickaxe
{
    public RendelithicPickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties)
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

        if (isToolEffective(level, pos) && Utils.isNearLava(level, pos) && !player.hasEffect(MobEffects.DIG_SLOWDOWN))
        {
            level.destroyBlock(pos, true);

            if (blockState.getDestroySpeed(level, pos) != 0.0F)
            {
                damageItem(1, player);
            }

            return InteractionResult.CONSUME;
        }

        if (block == Blocks.OBSIDIAN)
        {
            level.setBlock(pos, BlockRegistry.CRACKED_OBSIDIAN.get().defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        return super.useOn(context);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player)
    {
        if (isToolEffective(player.level, pos) && Utils.isNearLava(player.level, pos)) return true;
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

        if (hardness <= initialHardness && isToolEffective(level, pos) && !Utils.isNearLava(level, pos))
        {
            level.destroyBlock(pos, true);
            damageItem(1, entityLiving);
        }
    }
}
