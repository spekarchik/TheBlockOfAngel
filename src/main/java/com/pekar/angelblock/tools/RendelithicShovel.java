package com.pekar.angelblock.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.material.Material;

public class RendelithicShovel extends ModShovel
{

    public RendelithicShovel(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        ItemStack tool = context.getItemInHand();
        var player = context.getPlayer();
        var level = player.level;


//        if (!player.canPlayerEdit(pos.offset(facing), facing, tool)) return EnumActionResult.FAIL;
        if (level.isClientSide) return InteractionResult.PASS;

        if (!canUseToolEffect(player)) return InteractionResult.PASS;

        var pos = context.getClickedPos();

        if (isToolEffective(level, pos) && Utils.isNearLava(level, pos) && !player.hasEffect(MobEffects.DIG_SLOWDOWN))
        {
            level.destroyBlock(pos, true);

            BlockState blockState = level.getBlockState(pos);

            if (blockState.getDestroySpeed(level, pos) != 0.0F)
            {
                damageItem(player, 1);
            }

            return InteractionResult.SUCCESS;
        }

        InteractionResult result = super.useOn(context);

        if (result == InteractionResult.SUCCESS)
        {
            transformAdditionalBlocks(player, level, pos, context.getClickedFace());
        }

        return result;
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

        if (hardness <= initialHardness && isToolEffective(level, pos) && !Utils.isNearLava(level, pos))
        {
            level.destroyBlock(pos, true);
            damageItem(entityLiving, 1);
        }
    }

    @Override
    protected void onBlockTransforming(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        Block block = level.getBlockState(pos).getBlock();

        if (facing != Direction.DOWN && level.getBlockState(pos.above()).getMaterial() == Material.AIR && block == Blocks.GRASS)
        {
            BlockState newBlockState = Blocks.DIRT_PATH.defaultBlockState();
            level.setBlock(pos, newBlockState, 11);
        }
    }
}
