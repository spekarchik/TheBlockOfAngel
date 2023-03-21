package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.ArrayList;

public class EndRod extends AmethystRod
{
    public EndRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();

        if (isEnhancedRod() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT.get()))
            return super.useOn(context);

        var itemStack = player.getItemInHand(context.getHand());

        if (!isBroken(itemStack))
        {
            var pos = context.getClickedPos();
            var level = player.level;
            var blockState = level.getBlockState(pos);
            var block = blockState.getBlock();

            var hand = context.getHand();
            var facing = context.getClickedFace();

            boolean isClientSide = level.isClientSide();

            if (facing == Direction.UP)
            {
                if (block == Blocks.END_STONE)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.CHORUS_FLOWER);
                }
            }

            BlockPos startPos = switch (facing)
                    {
                        case UP -> pos.above();
                        case NORTH -> pos.north();
                        case SOUTH -> pos.south();
                        case EAST -> pos.east();
                        case WEST -> pos.west();
                        case DOWN -> pos.above();
                    };

            if (level.getBlockState(startPos).getBlock() == Blocks.LAVA
                && level.getBlockState(startPos.below()).getBlock() != Blocks.OBSIDIAN)
            {
                if (isClientSide) return InteractionResult.SUCCESS;

                int X = pos.getX(), Y = startPos.getY(), Z = pos.getZ();
                var blocksToTransform = new ArrayList<BlockPos>();

                for (int x = X - 2; x <= X + 2; x++)
                    for (int z = Z - 2; z <= Z + 2; z++)
                    {
                        var currentPos = new BlockPos(x, Y, z);
                        var upBlock = level.getBlockState(currentPos).getBlock();

                        if (upBlock == Blocks.LAVA && level.getFluidState(currentPos).getAmount() < FluidState.AMOUNT_FULL)
                        {
                            blocksToTransform.add(currentPos);
                        }
                    }

                for (var ps : blocksToTransform)
                {
                    level.setBlock(ps, Blocks.END_STONE.defaultBlockState(), 11);
                    updateNeighbors(level, ps);
                }

                if (!blocksToTransform.isEmpty())
                    new PlaySoundPacket(SoundType.STEAM).sendToPlayer((ServerPlayer) player);

                damageItemIfSurvival(player, level, pos, blockState); // pos, not upPos

                return InteractionResult.CONSUME;
            }

            if (block == Blocks.TUFF)
            {
                if (!isClientSide)
                {
                    level.setBlock(pos, BlockRegistry.DESTROYING_GUNPOWDER.get().defaultBlockState(), 0);
                    level.destroyBlock(pos, true, player, 1);
                    damageItemIfSurvival(player, level, pos, blockState);
                }

                return InteractionResult.sidedSuccess(isClientSide);
            }
        }

        return super.useOn(context);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        var block = state.getBlock();

        return super.isCorrectToolForDrops(stack, state)
                || (!isBroken(stack) && (block == Blocks.TUFF));
    }

    @Override
    protected int getShiftDepth()
    {
        return 15;
    }

    @Override
    protected int getShiftingRadius()
    {
        return 3;
    }

    @Override
    protected int getSculkDetectionDepth()
    {
        return 192;
    }
}
