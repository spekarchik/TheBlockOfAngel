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
import net.minecraft.world.level.block.state.BlockState;

public class LapisShovel extends ModShovel
{
    public LapisShovel(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;
        var pos = context.getClickedPos();

        if (!level.isClientSide && canUseToolEffect(player))
        {
            BlockState blockState = level.getBlockState(pos);
            var block = blockState.getBlock();

            if (isToolEffective(level, pos) && Utils.isNearWater(level, pos) && !player.hasEffect(MobEffects.DIG_SLOWDOWN))
            {
                level.destroyBlock(pos, true);

                if (blockState.getDestroySpeed(level, pos) != 0.0F)
                {
                    damageItem(1, player);
                }

                return InteractionResult.CONSUME;
            }

            if (block == BlockRegistry.CRACKED_ENDSTONE.get())
            {
                level.destroyBlock(pos, true, player);

                if (blockState.getDestroySpeed(level, pos) != 0.0F)
                {
                    damageItem(1, player);
                }

                return InteractionResult.CONSUME;
            }
        }

        InteractionResult result = super.useOn(context);

        if (result.shouldAwardStats())
        {
            transformAdditionalBlocks(player, level, pos, context.getClickedFace());
        }

        return result;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player)
    {
        if (isToolEffective(player.level, pos) && Utils.isNearWater(player.level, pos)) return true;
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity)
    {
        dropAdditionalBlocks(level, pos, livingEntity);
        return super.mineBlock(itemStack, level, blockState, pos, livingEntity);
    }

    @Override
    public boolean isEnhancedTool()
    {
        return true;
    }

    @Override
    protected void onBlockDropping(Level level, BlockState initialBlockState, float initialHardness, BlockPos pos, LivingEntity entityLiving)
    {
        BlockState blockState = level.getBlockState(pos);
        float hardness = blockState.getBlock().defaultDestroyTime();

        if (hardness <= initialHardness && isToolEffective(level, pos) && !Utils.isNearWater(level, pos))
        {
            level.destroyBlock(pos, true);
            damageItem(1, entityLiving);
        }
    }
}
