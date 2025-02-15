package com.pekar.angelblock.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
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
        if (level.isClientSide()) return false;

        //                Registry<Enchantment> enchantmentRegistry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
//                Holder<Enchantment> fortuneHolder = enchantmentRegistry.getHolderOrThrow(Enchantments.FORTUNE);
//                int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(fortuneHolder, mainHandItemStack);
//                int exp = blockState.getExpDrop(level, pos, null, entityLiving, mainHandItemStack);
//                exp = applyFortuneBonus(exp, fortuneLevel, 1.7);
//                block.popExperience((ServerLevel) level, pos, exp);
        level.destroyBlock(pos, true, entityLiving);
        return true;
    }

}
