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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;

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

        if (block == Blocks.DIAMOND_ORE)
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
                if (block == Blocks.DIRT_PATH || block == Blocks.DIRT)
                {
                    level.setBlock(pos.above(), Blocks.GRASS.defaultBlockState(), 11);
                    damageItemIfSurvival(player, level, pos, blockState);
                    return InteractionResult.CONSUME;
                }

                if (block == Blocks.SAND)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plantBlock(player, level, pos, hand, facing, Blocks.CACTUS, (IPlantable) Blocks.CACTUS);
                }

                if (block == Blocks.SOUL_SAND)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plantSeed(player, level, pos, hand, facing, Blocks.NETHER_WART);
                }

                if (block == Blocks.END_STONE)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plantSeed(player, level, pos, hand, facing, Blocks.CHORUS_FLOWER);
                }

                if (block == Blocks.GRASS)
                {
                    int lightLevel = level.getLightEmission(pos.above());

                    if (lightLevel > 12)
                    {
                        damageItemIfSurvival(player, level, pos, blockState);

                        int randomValue = itemRand.nextInt() % 16;
                        return plantSeed(player, level, pos, hand, facing, chooseFlowerByValue(randomValue));
                    }
                    else if (!Utils.isNearMushroomOrMycelium(level, pos))
                    {
                        damageItemIfSurvival(player, level, pos, blockState);

                        int randomValue = itemRand.nextInt() & 1;
                        Block plantBlock = randomValue == 0 ? Blocks.BROWN_MUSHROOM : Blocks.RED_MUSHROOM;
                        InteractionResult result = plantSeed(player, level, pos, hand, facing, plantBlock);
                        if (result.shouldAwardStats())
                        {
                            Utils.setMyceliumInRadius(level, pos, 2);
                        }

                        return result;
                    }
                }

                // TODO: бамбук, азалия, тростник
                if (block == Blocks.FARMLAND)
                {
                    damageItemIfSurvival(player, level, pos, blockState);

                    int randomValue = itemRand.nextInt() % 4;
                    switch (randomValue)
                    {
                        case 0:
                            return plantSeed(player, level, pos, hand, facing, Blocks.WHEAT);
                        case 1:
                            return plantSeed(player, level, pos, hand, facing, Blocks.POTATOES);
                        case 2:
                            return plantSeed(player, level, pos, hand, facing, Blocks.CARROTS);
                        default:
                            return plantSeed(player, level, pos, hand, facing, Blocks.BEETROOTS);
                    }
                }

                if (block == Blocks.MOSS_BLOCK)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    int randomValue = itemRand.nextInt() & 1;
                    Block plantBlock = randomValue == 0 ? Blocks.AZALEA : Blocks.FLOWERING_AZALEA;
                    return plantSeed(player, level, pos, hand, facing, plantBlock);
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

    private InteractionResult plantSeed(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing, Block plantBlock)
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

    private InteractionResult plantBlock(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing, Block plantBlock, IPlantable plantable)
    {
        var itemStack = player.getItemInHand(hand);
        BlockState state = level.getBlockState(pos);

        if (facing == Direction.UP && state.getBlock().canSustainPlant(state, level, pos, Direction.UP, plantable) && level.isEmptyBlock(pos.above()))
        {
            level.setBlock(pos.above(), plantBlock.defaultBlockState(), 11);

            if (player instanceof ServerPlayer serverPlayer)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos.above(), itemStack);
            }

            return InteractionResult.CONSUME;
        }
        else
        {
            return InteractionResult.FAIL;
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
