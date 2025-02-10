package com.pekar.angelblock.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

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
        var mainHandItemStack = entityLiving.getMainHandItem();
        if (!level.isClientSide())
            block.playerDestroy(level, (net.minecraft.world.entity.player.Player) entityLiving, pos, blockState, null, mainHandItemStack);

        if (mainHandItemStack.getItem() instanceof DiggerItem tool)
        {
            if (!level.isClientSide())
            {
                // TODO: Check if it depends on a tool FORTUNE level
                int exp = blockState.getExpDrop(level, pos, null, entityLiving, mainHandItemStack);
                block.popExperience((ServerLevel) level, pos, exp);
                level.removeBlock(pos, true);
            }
            return true;
        }

        return false;
    }
}
