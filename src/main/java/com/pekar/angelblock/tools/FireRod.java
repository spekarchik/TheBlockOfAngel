package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FireRod extends MarineRod
{
    public FireRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;

        if (level.isClientSide) return InteractionResult.PASS;
        if (!canUseToolEffect(player)) return InteractionResult.PASS;

        var result = super.useOn(context);
        if (result != InteractionResult.PASS) return result;

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        var itemStack = player.getItemInHand(context.getHand());
        boolean isBroken = itemStack.getMaxDamage() - itemStack.getDamageValue() <= 1;

        if (!isBroken)
        {
            var hand = context.getHand();
            var facing = context.getClickedFace();

            if (facing == Direction.UP)
            {
                if (block == Blocks.SOUL_SAND)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.NETHER_WART);
                }
            }

            if (block == Blocks.PUMPKIN)
            {
                damageItemIfSurvival(player, level, pos, blockState);
                level.setBlock(pos, Blocks.GLOWSTONE.defaultBlockState(), 11);
                return InteractionResult.CONSUME;
            }

            if (block == Blocks.WHITE_WOOL)
            {
                level.setBlock(pos, BlockRegistry.DESTROYING_BONE_MEAL.get().defaultBlockState(), 0);
                level.destroyBlock(pos, true, player, 1);
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }
}
