package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.tools.properties.LapisHoeProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class LapisHoe extends ModHoe
{
    public LapisHoe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LapisHoeProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;

        var result = super.useOn(context);
        if (result == InteractionResult.FAIL) return result;

        if (level.isClientSide) return result;
        if (!canUseToolEffect(player)) return result;

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        BlockPos upPos = pos.above();

        if (level.isWaterAt(upPos) || ((level.isEmptyBlock(upPos))
            && ((isFarmTypeBlock(level, upPos.north()) && isFarmTypeBlock(level, upPos.south()))
            || (isFarmTypeBlock(level, upPos.east()) && isFarmTypeBlock(level, upPos.west())))))
        {
            level.setBlock(upPos, Blocks.WATER.defaultBlockState(), 11);
            new PlaySoundPacket(SoundType.WATER_PLACED).sendToPlayer((ServerPlayer) player);

            damageItemIfSurvival(player, level, pos, blockState); // pos, not upPos

            if (!updateNeighbors(level, upPos))
            {
                return InteractionResult.FAIL;
            }

            return InteractionResult.CONSUME;
        }
        else
        {
            processAdditionalBlocks(player, level, pos, context.getClickedFace());
            return InteractionResult.CONSUME;
        }
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
    protected void onBlockProcessing(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (level.isEmptyBlock(pos.above()))
        {
            if (canBeFarmland(block))
            {
                level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 11);
                new PlaySoundPacket(SoundType.PLANT).sendToPlayer((ServerPlayer) player);
                damageItemIfSurvival(player, level, pos, blockState);
            }
            else if (block == Blocks.COARSE_DIRT)
            {
                setBlock(player, pos, Blocks.DIRT);
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
