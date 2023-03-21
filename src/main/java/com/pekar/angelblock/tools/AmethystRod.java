package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AmethystRod extends FireRod
{
    public AmethystRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;

        var result = super.useOn(context);
        if (result != InteractionResult.PASS) return result;

        var itemStack = player.getItemInHand(context.getHand());

        boolean isClientSide = level.isClientSide();

        if (!isBroken(itemStack))
        {
            var pos = context.getClickedPos();
            var blockState = level.getBlockState(pos);
            var block = blockState.getBlock();

            var upPos = pos.above();
            var upBlock = level.getBlockState(upPos).getBlock();

            if (block == Blocks.OBSIDIAN && upBlock != Blocks.LAVA)
            {
                if (!isClientSide)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    setBlock(player, pos, Blocks.CRYING_OBSIDIAN);
                }

                return InteractionResult.sidedSuccess(isClientSide);
            }

            if (block == Blocks.STONE || block == Blocks.GRANITE || block == Blocks.ANDESITE
                    || block == Blocks.DIORITE || block == Blocks.CALCITE
                    || block == Blocks.DRIPSTONE_BLOCK)
            {
                damageItemIfSurvival(player, level, pos, blockState);
                return setOnBlockSide(context, this::setGlowLichen);
            }

            if (block == Blocks.DIAMOND_BLOCK)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, Blocks.BUDDING_AMETHYST);
                    damageItemIfSurvival(player, level, pos, blockState);
                }

                return InteractionResult.sidedSuccess(isClientSide);
            }

            if (block == Blocks.BONE_BLOCK)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, Blocks.CALCITE);
                    damageItemIfSurvival(player, level, pos, blockState);
                }

                return InteractionResult.sidedSuccess(isClientSide);
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        var block = state.getBlock();

        return super.isCorrectToolForDrops(stack, state)
                || (!isBroken(stack) && (block == Blocks.OBSIDIAN || block == Blocks.GRANITE || block == Blocks.ANDESITE
                || block == Blocks.DIORITE || block == Blocks.CALCITE
                || block == Blocks.DRIPSTONE_BLOCK || block == Blocks.DIAMOND_BLOCK || block == Blocks.BONE_BLOCK));
    }

    @Override
    protected int getShiftDepth()
    {
        return 12;
    }

    @Override
    protected int getSculkDetectionDepth()
    {
        return 128;
    }

    private InteractionResult setGlowLichen(BlockPlaceContext context, BlockPos pos)
    {
        var level = context.getLevel();
        BlockState state = Blocks.GLOW_LICHEN.getStateForPlacement(context);

        if (!level.isEmptyBlock(pos)) return InteractionResult.FAIL;

        var isClientSide = context.getLevel().isClientSide();
        if (!isClientSide)
        {
            new PlaySoundPacket(SoundType.BLOCK_CHANGED).sendToPlayer((ServerPlayer) context.getPlayer());
            level.setBlock(pos, state, 11);
        }

        return InteractionResult.sidedSuccess(isClientSide);
    }
}
