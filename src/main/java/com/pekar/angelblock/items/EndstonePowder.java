package com.pekar.angelblock.items;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;

import java.util.ArrayList;

public class EndstonePowder extends ModItemWithDoubleHoverText
{
    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var facing = context.getClickedFace();
        boolean isClientSide = level.isClientSide();

        BlockPos startPos = switch (facing)
                {
                    case UP -> pos.above();
                    case NORTH -> pos.north();
                    case SOUTH -> pos.south();
                    case EAST -> pos.east();
                    case WEST -> pos.west();
                    case DOWN -> pos.above();
                };

        if (level.getBlockState(startPos).getBlock() == Blocks.LAVA)
        {
            if (isClientSide) return InteractionResult.SUCCESS;

            int X = pos.getX(), Y = startPos.getY(), Z = pos.getZ();
            var blocksToTransform = new ArrayList<BlockPos>();

            for (int x = X - 2; x <= X + 2; x++)
                for (int z = Z - 2; z <= Z + 2; z++)
                {
                    var currentPos = new BlockPos(x, Y, z);
                    var upBlock = level.getBlockState(currentPos).getBlock();

                    if (upBlock == Blocks.LAVA && level.getFluidState(currentPos).getAmount() == FluidState.AMOUNT_FULL)
                    {
                        blocksToTransform.add(currentPos);
                    }
                }

            for (var ps : blocksToTransform)
            {
                level.setBlock(ps, Blocks.END_STONE.defaultBlockState(), Block.UPDATE_ALL);
            }

            if (player instanceof ServerPlayer serverPlayer)
            {
                if (!blocksToTransform.isEmpty())
                    new PlaySoundPacket(SoundType.STEAM).sendToPlayer(serverPlayer);

                var itemStack = player.getItemInHand(context.getHand());
                itemStack.shrink(1);
            }

            return InteractionResult.CONSUME;
        }

        return super.useOn(context);
    }
}
