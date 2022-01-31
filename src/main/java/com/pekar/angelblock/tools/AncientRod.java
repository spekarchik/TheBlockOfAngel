package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class AncientRod extends ModRod
{
    public AncientRod(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
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

        if (Utils.mossyTransforming(level, pos, block))
        {
            return InteractionResult.CONSUME;
        }

        var itemStack = player.getItemInHand(context.getHand());
        boolean isBroken = itemStack.getMaxDamage() - itemStack.getDamageValue() <= 1;

        if (!isBroken)
        {
            var hand = context.getHand();
            var facing = context.getClickedFace();
            var itemRand = new Random();

            if (facing == Direction.UP)
            {
                BlockPos upPos = pos.above();

                if (level.isWaterAt(upPos) || ((level.isEmptyBlock(upPos))
                        && ((isFarmTypeBlock(level, upPos.north()) && isFarmTypeBlock(level, upPos.south()))
                        || (isFarmTypeBlock(level, upPos.east()) && isFarmTypeBlock(level, upPos.west())))))
                {
                    level.setBlock(upPos, Blocks.WATER.defaultBlockState(), 11);
                    damageItemIfSurvival(player, level, pos, blockState); // pos, not upPos

                    if (!updateNeighbors(level, upPos))
                    {
                        return InteractionResult.FAIL;
                    }

                    return InteractionResult.CONSUME;
                }

                if (Utils.isNearWaterHorizoltal(level, pos) && (block == Blocks.DIRT || block == Blocks.COARSE_DIRT
                    || block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL || block instanceof SandBlock
                        || block == Blocks.MOSS_BLOCK || block == Blocks.MYCELIUM))
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.SUGAR_CANE);
                }

                if (block == Blocks.FARMLAND)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    int randomValue = itemRand.nextInt() % 4;

                    switch (randomValue)
                    {
                        case 0:
                            return plant(player, level, pos, hand, facing, Blocks.WHEAT);
                        case 1:
                            return plant(player, level, pos, hand, facing, Blocks.POTATOES);
                        case 2:
                            return plant(player, level, pos, hand, facing, Blocks.CARROTS);
                        default:
                            return plant(player, level, pos, hand, facing, Blocks.BEETROOTS);
                    }
                }

                if (block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.MOSS_BLOCK)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    int randomValue = itemRand.nextInt() % 3;

                    switch (randomValue)
                    {
                        case 0:
                            return plant(player, level, pos, hand, facing, Blocks.SWEET_BERRY_BUSH);
                        case 1:
                            return plant(player, level, pos, hand, facing, Blocks.FERN);
                        default:
                            {
                                int random = itemRand.nextInt() & 2;
                                Block plantBlock = random > 0 ? Blocks.AZALEA : Blocks.FLOWERING_AZALEA;
                                return plant(player, level, pos, hand, facing, plantBlock);
                            }
                    }
                }

                if (block == Blocks.GRASS_BLOCK)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    int randomValue = itemRand.nextInt() % 16;
                    return plant(player, level, pos, hand, facing, chooseFlowerByValue(randomValue));
                }

                if (block == Blocks.SAND)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.CACTUS);
                }

                if (block == Blocks.PODZOL || block == Blocks.MYCELIUM)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    int randomValue = itemRand.nextInt() & 1;
                    var plantBlock = randomValue == 0 ? Blocks.BROWN_MUSHROOM : Blocks.RED_MUSHROOM;
                    return plant(player, level, pos, hand, facing, plantBlock);
                }

                if (block == Blocks.RED_SAND || block == Blocks.GRAVEL)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.BAMBOO);
                }

                if (block == Blocks.SOUL_SAND)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.NETHER_WART);
                }

                if (block == Blocks.END_STONE)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.CHORUS_FLOWER);
                }
            }

            if (block == Blocks.COBWEB)
            {
                destroyWebBlocks(level, pos);
                damageItemIfSurvival(player, level, pos, blockState);
                return InteractionResult.CONSUME;
            }

            if (block instanceof LeavesBlock)
            {
                damageItemIfSurvival(player, level, pos, blockState);
                return setVine(context);
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

    private InteractionResult plant(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing, Block plantBlock)
    {
        var itemstack = player.getItemInHand(hand);

        if (facing == Direction.UP && level.isEmptyBlock(pos.above()))
        {
            level.setBlock(pos.above(), plantBlock.defaultBlockState(), 11);

            if (player instanceof ServerPlayer serverPlayer)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos.above(), itemstack);
            }

            return InteractionResult.CONSUME;
        }
        else
        {
            return InteractionResult.FAIL;
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

    private Block chooseFlowerByValue(int value)
    {
        switch (value)
        {
            case 1: return Blocks.POPPY;
            case 2: return Blocks.BLUE_ORCHID;
            case 3: return Blocks.ALLIUM;
            case 4: return Blocks.AZURE_BLUET;
            case 5: return Blocks.RED_TULIP;
            case 6: return Blocks.ORANGE_TULIP;
            case 7: return Blocks.WHITE_TULIP;
            case 8: return Blocks.PINK_TULIP;
            case 9: return Blocks.OXEYE_DAISY;
            case 10: return Blocks.CORNFLOWER;
            case 11: return Blocks.LILY_OF_THE_VALLEY;
            case 12: return Blocks.SUNFLOWER;
            case 13: return Blocks.LILAC;
            case 14: return Blocks.ROSE_BUSH;
            case 15: return Blocks.PEONY;
            default: return Blocks.DANDELION;
        }
    }

    private InteractionResult setVine(Level level, BlockPos pos, BlockState state)
    {
        if (!level.isEmptyBlock(pos)) return InteractionResult.FAIL;

        level.setBlock(pos, state, 11);
        return InteractionResult.CONSUME;
    }

    private InteractionResult setVine(UseOnContext useOnContext)
    {
        var context = new BlockPlaceContext(useOnContext);
        BlockState state = Blocks.VINE.getStateForPlacement(context);

        var level = useOnContext.getLevel();
        var pos = useOnContext.getClickedPos();
        var facing = useOnContext.getClickedFace();

        switch (facing)
        {
            case UP:
                return InteractionResult.FAIL;

            case NORTH:
                return setVine(level, pos.north(), state);

            case SOUTH:
                return setVine(level, pos.south(), state);

            case EAST:
                return setVine(level, pos.east(), state);

            case WEST:
                return setVine(level, pos.west(), state);

            case DOWN:
                return setVine(level, pos.below(), state);
        }

        return InteractionResult.PASS;
    }
}
