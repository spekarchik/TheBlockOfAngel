package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.OreDetectedPacket;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Function;

public class MagneticRod extends ModRod
{
    public MagneticRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
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
        if (!isEnhancedRod() || !player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT.get()))
        {
            return super.useOn(context);
        }

        var itemStack = player.getItemInHand(context.getHand());
        boolean isBroken = itemStack.getMaxDamage() - itemStack.getDamageValue() <= 1;
        if (isBroken) return InteractionResult.PASS;

        var pos = context.getClickedPos();
        if (!canBeReplaced(level, pos) && !isOre(level, pos) && !isDiamondOre(level.getBlockState(pos).getBlock()))
        {
            return InteractionResult.CONSUME;
        }

        return shiftOres(player, level, pos, context.getClickedFace());
    }

    private InteractionResult shiftOres(Player player, Level level, BlockPos pos, Direction clickedFace)
    {
        int depth = getShiftDepth();

        int initialDepth;
        Function<BlockPos, Integer> getDepthCoord, getRadiusCoord1, getRadiusCoord2;
        TriFunction<Integer, Integer, Integer, BlockPos> createPos;

        if (clickedFace == Direction.UP || clickedFace == Direction.DOWN)
        {
            getDepthCoord = p -> p.getY();
            getRadiusCoord1 = p -> p.getX();
            getRadiusCoord2 = p -> p.getZ();
            createPos = (x1, x2, d) -> new BlockPos(x1, d, x2);
            initialDepth = clickedFace == Direction.UP ? depth : -depth;
        }
        else if (clickedFace == Direction.SOUTH || clickedFace == Direction.NORTH)
        {
            // south: z++
            getDepthCoord = p -> p.getZ();
            getRadiusCoord1 = p -> p.getX();
            getRadiusCoord2 = p -> p.getY();
            createPos = (x1, x2, d) -> new BlockPos(x1, x2, d);
            initialDepth = clickedFace == Direction.SOUTH ? depth : -depth;
        }
        else // (clickedFace == Direction.EAST || clickedFace == Direction.WEST)
        {
            // east: x++
            getDepthCoord = p -> p.getX();
            getRadiusCoord1 = p -> p.getY();
            getRadiusCoord2 = p -> p.getZ();
            createPos = (x1, x2, d) -> new BlockPos(d, x1, x2);
            initialDepth = clickedFace == Direction.EAST ? depth : -depth;
        }

        shiftOreBlocks((ServerPlayer) player, level, pos, clickedFace, initialDepth, getDepthCoord, getRadiusCoord1, getRadiusCoord2, createPos);

        return InteractionResult.CONSUME;
    }

    private void shiftOreBlocks(ServerPlayer player, Level level, BlockPos pos, Direction clickedFace, int initialDepth, Function<BlockPos, Integer> getDepthCoord, Function<BlockPos, Integer> getRadiusCoord1, Function<BlockPos, Integer> getRadiusCoord2, TriFunction<Integer, Integer, Integer, BlockPos> createPos)
    {
        int radius = getShiftingRadius();
        boolean isDiamondOreFound = false;
        boolean isShiftingOreFound = false;

        for (int x1 = getRadiusCoord1.apply(pos) - radius; x1 <= getRadiusCoord1.apply(pos) + radius; x1++)
            for (int x2 = getRadiusCoord2.apply(pos) - radius; x2 <= getRadiusCoord2.apply(pos) + radius; x2++)
            {
                BlockPos currentPos, replacedPos;
                for (currentPos = createPos.apply(x1, x2, getDepthCoord.apply(pos) - initialDepth); getDepthCoord.apply(currentPos) != getDepthCoord.apply(pos); currentPos = replacedPos)
                {
                    // check current block: ore? diamond?
                    var currentBlock = level.getBlockState(currentPos).getBlock();
                    boolean isDiamondOre = isDiamondOre(currentBlock);
                    boolean isShiftingOre = isShiftingOre(currentBlock);

                    if (isDiamondOre) isDiamondOreFound = true;

                    if (isShiftingOre)
                    {
                        isShiftingOreFound = true;
                    }
                    else
                    {
                        replacedPos = currentPos.relative(clickedFace);
                        continue;
                    }

                    // check next block to replace
                    boolean solidBlockFound = false;
                    for (replacedPos = currentPos.relative(clickedFace); getDepthCoord.apply(replacedPos) != getDepthCoord.apply(pos.relative(clickedFace)); replacedPos = replacedPos.relative(clickedFace))
                    {
                        var blockState = level.getBlockState(replacedPos);
                        if (!blockState.isAir() && !blockState.getMaterial().isLiquid())
                        {
                            solidBlockFound = true;
                            break;
                        }
                    }

                    // replace blocks if possible, break the loop otherwise (the rest of the blocks are air ot liquid)
                    if (!solidBlockFound) break;

                    tryExchange(level, currentPos, replacedPos);
                }
            }

        if (isShiftingOreFound || isDiamondOreFound) oreFoundEvent(player, isShiftingOreFound, isDiamondOreFound);
    }

    protected void oreFoundEvent(ServerPlayer player, boolean isOreFound, boolean isDiamondOreFound)
    {
        if (isOreFound)
            new OreDetectedPacket(false).sendToPlayer(player);
    }

    private OreType tryExchange(Level level, BlockPos currentPos, BlockPos closerPos)
    {
        var block = level.getBlockState(currentPos).getBlock();
        boolean isDiamondOre = isDiamondOre(block);

        if (!isOre(level, currentPos)) return isDiamondOre ? OreType.DIAMOND_ORE : OreType.NOT_ORE;
        if (!canBeReplaced(level, closerPos)) return OreType.NOT_ORE;
        exchange(level, currentPos, closerPos);
        return OreType.ORE;
    }

    protected int getShiftingRadius()
    {
        return 1;
    }

    protected int getShiftDepth()
    {
        return 5;
    }

    private void exchange(Level level, BlockPos currentPos, BlockPos closerPos)
    {
        var currentBlockState = level.getBlockState(currentPos);
        var upperBlockState = level.getBlockState(closerPos);
        level.setBlock(currentPos, upperBlockState, 11);
        level.setBlock(closerPos, currentBlockState, 11);
    }

    protected boolean canBeReplaced(Level level, BlockPos pos)
    {
        if (!Utils.isOverworld(level.dimension())) return false;

        var block = level.getBlockState(pos).getBlock();

        return block == Blocks.STONE || block == Blocks.GRANITE || block == Blocks.DIORITE || block == Blocks.ANDESITE
                || block == Blocks.DEEPSLATE || block == Blocks.TUFF || block == Blocks.COBBLESTONE || block == Blocks.COBBLED_DEEPSLATE;
    }

    protected boolean isShiftingOre(Block block)
    {
        return block instanceof OreBlock && !isDiamondOre(block);
    }

    private boolean isOre(Level level, BlockPos pos)
    {
        var block = level.getBlockState(pos).getBlock();
        return isShiftingOre(block);
    }

    private boolean isDiamondOre(Block block)
    {
        return block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE;
    }
}
