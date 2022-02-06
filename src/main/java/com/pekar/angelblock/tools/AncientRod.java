package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.network.packets.OnPlantPacket;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

public class AncientRod extends MagneticRod
{
    public AncientRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = super.useOn(context);
        if (result != InteractionResult.PASS) return result;

        var player = context.getPlayer();
        var level = player.level;

        if (level.isClientSide) return InteractionResult.PASS;
        if (!canUseToolEffect(player)) return InteractionResult.PASS;
        if (isEnhancedRod() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT.get()))
            return super.useOn(context);

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE)
        {
            setBlock(player, pos, BlockRegistry.GREEN_DIAMOND_ORE.get());
            return InteractionResult.CONSUME;
        }

        if (block instanceof InfestedBlock infestedBlock)
        {
            setBlock(player, pos, infestedBlock.getHostBlock());
            return InteractionResult.CONSUME;
        }

        if (block != Blocks.STONE || context.getClickedFace() == Direction.UP)
        {
            if (Utils.mossyTransforming(player, pos, block))
            {
                return InteractionResult.CONSUME;
            }
        }

        var itemStack = player.getItemInHand(context.getHand());
        boolean isBroken = itemStack.getMaxDamage() - itemStack.getDamageValue() <= 1;

        if (!isBroken)
        {
            if (block instanceof LeavesBlock)
            {
                damageItemIfSurvival(player, level, pos, blockState);
                return setOnBlockSide(context, this::setVine);
            }

            if (block == Blocks.COBWEB)
            {
                destroyWebBlocks(level, pos);
                damageItemIfSurvival(player, level, pos, blockState);
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }

    private void destroyWebBlocks(Level level, BlockPos pos)
    {
        int X = pos.getX(), Y = pos.getY(), Z = pos.getZ();
        for (int a = X - 1; a <= X + 1; a++)
            for (int b = Y - 1; b <= Y + 1; b++)
                for (int c = Z - 1; c <= Z + 1; c++)
                {
                    BlockPos localPos = new BlockPos(a, b, c);
                    Block block = level.getBlockState(localPos).getBlock();

                    if (block == Blocks.COBWEB)
                    {
                        level.destroyBlock(localPos, false);
                    }
                }
    }

    private InteractionResult setVine(BlockPlaceContext context, BlockPos pos)
    {
        var level = context.getLevel();
        BlockState state = Blocks.VINE.getStateForPlacement(context);

        if (!level.isEmptyBlock(pos)) return InteractionResult.FAIL;

        level.setBlock(pos, state, 11);
        new OnPlantPacket().sendToPlayer((ServerPlayer) context.getPlayer());
        return InteractionResult.CONSUME;
    }

    protected InteractionResult setOnBlockSide(UseOnContext useOnContext, BiFunction<BlockPlaceContext, BlockPos, InteractionResult> setBlock)
    {
        var context = new BlockPlaceContext(useOnContext);

        var pos = useOnContext.getClickedPos();
        var facing = useOnContext.getClickedFace();

        switch (facing)
        {
            case UP:
                return InteractionResult.FAIL;

            case NORTH:
                return setBlock.apply(context, pos.north());

            case SOUTH:
                return setBlock.apply(context, pos.south());

            case EAST:
                return setBlock.apply(context, pos.east());

            case WEST:
                return setBlock.apply(context, pos.west());

            case DOWN:
                return setBlock.apply(context, pos.below());
        }

        return InteractionResult.PASS;
    }
}
