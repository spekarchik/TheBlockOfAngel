package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class AncientRod extends ModRod
{
    public AncientRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
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

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE)
        {
            level.setBlock(pos, BlockRegistry.GREEN_DIAMOND_ORE.get().defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (block instanceof InfestedBlock infestedBlock)
        {
            level.setBlock(pos, infestedBlock.getHostBlock().defaultBlockState(), 11);
            return InteractionResult.CONSUME;
        }

        if (block != Blocks.STONE || context.getClickedFace() == Direction.UP)
        {
            if (Utils.mossyTransforming(level, pos, block))
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

//    private InteractionResult plantBlock(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing, IPlantable block)
//    {
//        var itemStack = player.getItemInHand(hand);
//        BlockState state = level.getBlockState(pos);
//
//        if (facing == Direction.UP && state.getBlock().canSustainPlant(state, level, pos, Direction.UP, block) && level.isEmptyBlock(pos.above()))
//        {
//            level.setBlock(pos.above(), ((Block)block).defaultBlockState(), 11);
//
//            if (player instanceof ServerPlayer serverPlayer)
//            {
//                CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos.above(), itemStack);
//            }
//
//            return InteractionResult.CONSUME;
//        }
//        else
//        {
//            return InteractionResult.FAIL;
//        }
//    }

    private InteractionResult setVine(BlockPlaceContext context, BlockPos pos)
    {
        var level = context.getLevel();
        BlockState state = Blocks.VINE.getStateForPlacement(context);

        if (!level.isEmptyBlock(pos)) return InteractionResult.FAIL;

        level.setBlock(pos, state, 11);
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
