package com.pekar.angelblock.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Player
{
    public final PlayerConditions conditions = new PlayerConditions();

    Player()
    {

    }

    public Direction getDirection(LivingEntity entityLiving, BlockPos pos)
    {
        BlockPos entityPos = entityLiving.blockPosition();
        int entityX = entityPos.getX(), entityY = entityPos.getY(), entityZ = entityPos.getZ();

        if (pos.getY() > entityY + 2)
        {
            return Direction.UP;
        }
        else if (entityY - pos.getY() > 1)
        {
            return Direction.DOWN;
        }
        else if (pos.getY() < entityY)
        {
            if (Math.abs(pos.getX() - entityX) < 2 && Math.abs(pos.getZ() - entityZ) < 2)
            {
                return Direction.DOWN;
            }
        }

        return entityLiving.getDirection();
    }

    public Direction getDirectionForShovel(LivingEntity entityLiving, BlockPos pos)
    {
        BlockPos entityPos = entityLiving.blockPosition();
        int entityY = entityPos.getY();

        if (pos.getY() > entityY + 2)
        {
            return Direction.UP;
        }
        else if (entityY >= pos.getY())
        {
            return Direction.DOWN;
        }

        return entityLiving.getDirection();
    }

    public boolean destroyBlockByMainHandTool(Level level, BlockPos pos, LivingEntity entityLiving, BlockState blockState, Block block)
    {
        if (level.isClientSide()) return false;

        //                Registry<Enchantment> enchantmentRegistry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
//                Holder<Enchantment> fortuneHolder = enchantmentRegistry.getHolderOrThrow(Enchantments.FORTUNE);
//                int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(fortuneHolder, mainHandItemStack);
//                int exp = blockState.getExpDrop(level, pos, null, entityLiving, mainHandItemStack);
//                exp = applyFortuneBonus(exp, fortuneLevel, 1.7);
//                block.popExperience((ServerLevel) level, pos, exp);
        final int recursionLeft = 512;
        return destroyBlock(pos, true, entityLiving, level, entityLiving.getMainHandItem(), recursionLeft);
    }

    // See Level.destroyBlock()
    private boolean destroyBlock(BlockPos pos, boolean dropBlock, @NotNull Entity entity, Level level, ItemStack tool, int recursionLeft)
    {
        BlockState blockstate = level.getBlockState(pos);
        
        if (blockstate.isAir())
        {
            return false;
        }
        else
        {
            FluidState fluidstate = level.getFluidState(pos);
            if (!(blockstate.getBlock() instanceof BaseFireBlock))
            {
                level.levelEvent(2001, pos, Block.getId(blockstate));
            }

            if (dropBlock)
            {
                BlockEntity blockentity = blockstate.hasBlockEntity() ? level.getBlockEntity(pos) : null;
                Block.dropResources(blockstate, level, pos, blockentity, entity, tool);
            }

            boolean flag = level.setBlock(pos, fluidstate.createLegacyBlock(), 3, recursionLeft);
            if (flag)
            {
                level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(entity, blockstate));
            }

            return flag;
        }
    }
}
