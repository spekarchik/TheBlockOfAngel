package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
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
import org.apache.commons.lang3.function.TriFunction;

import java.util.Objects;
import java.util.function.Function;

public class MagneticRod extends ModRod
{
    public MagneticRod(Tier material, boolean isMagnetic, Properties properties)
    {
        super(material, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = useOnInternal(context);
        if (result == InteractionResult.PASS) return InteractionResult.FAIL;
        return result;
    }

    protected InteractionResult useOnInternal(UseOnContext context)
    {
        var player = context.getPlayer();
        var itemStack = player.getItemInHand(context.getHand());

        if (isBroken(itemStack)) return InteractionResult.FAIL;

        if (!isEnhanced() || !player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT))
        {
            return super.useOn(context);
        }

        var level = player.level();
        boolean isClientSide = level.isClientSide();

        var pos = context.getClickedPos();
        var blockState = level.getBlockState(pos);
        if (blockState.isAir() || utils.blocks.types.isLiquid(blockState.getBlock()))
        {
            return getToolInteractionResult(true, isClientSide);
        }

        if (isClientSide) return getToolInteractionResult(true, isClientSide);

        shiftOres(player, level, pos, context.getClickedFace());
        return getToolInteractionResult(true, isClientSide);
    }

    private InteractionResult shiftOres(Player player, Level level, BlockPos pos, Direction clickedFace)
    {
        int depth = Math.max(getSculkDetectionDepth(), Math.max(getAmethystDetectionDepth(), getOreDepth()));

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

        return getToolInteractionResult(true, level.isClientSide());
    }

    private void shiftOreBlocks(ServerPlayer player, Level level, BlockPos pos, Direction clickedFace, int initialDepth, Function<BlockPos, Integer> getDepthCoord, Function<BlockPos, Integer> getRadiusCoord1, Function<BlockPos, Integer> getRadiusCoord2, TriFunction<Integer, Integer, Integer, BlockPos> createPos)
    {
        int radius = getShiftingRadius();
        boolean isDiamondOreFound = false;
        boolean isShiftingOreFound = false;
        boolean isAmethystFound = false;
        boolean areRailsFound = false;
        boolean isSculkVeinFound = false;
        int maxDepthCoord = getDepthCoord.apply(pos) - initialDepth;
        int oreDepthCoord = getDepthCoord.apply(pos) - (initialDepth >= 0 ? getOreDepth() : -getOreDepth());
        int amethystDepthCoord = getDepthCoord.apply(pos) - (initialDepth >= 0 ? getAmethystDetectionDepth() : -getAmethystDetectionDepth());
        int railDepthCoord = getDepthCoord.apply(pos) - (initialDepth >= 0 ? getRailsDetectionDepth() : -getRailsDetectionDepth());
        int sculkDepthCoord = getDepthCoord.apply(pos) - (initialDepth >= 0 ? getSculkDetectionDepth() : -getSculkDetectionDepth());

        for (int x1 = getRadiusCoord1.apply(pos) - radius; x1 <= getRadiusCoord1.apply(pos) + radius; x1++)
            for (int x2 = getRadiusCoord2.apply(pos) - radius; x2 <= getRadiusCoord2.apply(pos) + radius; x2++)
            {
                BlockPos currentPos, replacedPos;
                for (currentPos = createPos.apply(x1, x2, maxDepthCoord); !Objects.equals(getDepthCoord.apply(currentPos), getDepthCoord.apply(pos)); currentPos = replacedPos)
                {
                    boolean doCurrentDepthAllowShiftOres;
                    boolean doCurrentDepthAllowDetectAmethyst;
                    boolean doCurrentDepthAllowDetectRails;
                    boolean doCurrentDepthAllowDetectSculk;
                    var currentDepth = getDepthCoord.apply(currentPos);

                    if (initialDepth >= 0)
                    {
                        doCurrentDepthAllowShiftOres = currentDepth >= oreDepthCoord;
                        doCurrentDepthAllowDetectAmethyst = currentDepth >= amethystDepthCoord;
                        doCurrentDepthAllowDetectRails = currentDepth >= railDepthCoord;
                        doCurrentDepthAllowDetectSculk = currentDepth >= sculkDepthCoord;
                    }
                    else
                    {
                        doCurrentDepthAllowShiftOres = currentDepth <= oreDepthCoord;
                        doCurrentDepthAllowDetectAmethyst = currentDepth <= amethystDepthCoord;
                        doCurrentDepthAllowDetectRails = currentDepth <= railDepthCoord;
                        doCurrentDepthAllowDetectSculk = currentDepth <= sculkDepthCoord;
                    }

                    // check current block: ore? diamond?
                    var currentBlock = level.getBlockState(currentPos).getBlock();
                    boolean isDiamondOre = utils.blocks.types.isDiamondOre(currentBlock);
                    boolean isShiftingOre = isShiftingOre(currentBlock);
                    boolean isAmethystGeode = isAmethystGeode(currentBlock);
                    boolean isRail = utils.blocks.types.isRail(currentBlock);
                    boolean isSculkVein = utils.blocks.types.isSculk(currentBlock);

                    if (doCurrentDepthAllowDetectAmethyst && isAmethystGeode) isAmethystFound = true;

                    if (doCurrentDepthAllowDetectRails && isRail) areRailsFound = true;

                    if (doCurrentDepthAllowDetectSculk && isSculkVein) isSculkVeinFound = true;

                    if (!doCurrentDepthAllowShiftOres)
                    {
                        replacedPos = currentPos.relative(clickedFace);
                        continue;
                    }

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
                    for (replacedPos = currentPos.relative(clickedFace); !Objects.equals(getDepthCoord.apply(replacedPos), getDepthCoord.apply(pos.relative(clickedFace))); replacedPos = replacedPos.relative(clickedFace))
                    {
                        var blockState = level.getBlockState(replacedPos);
                        if (!blockState.isAir() && !utils.blocks.types.isLiquid(blockState.getBlock()))
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

        if (isShiftingOreFound || isDiamondOreFound || isAmethystFound || areRailsFound || isSculkVeinFound)
            oreFoundEvent(player, new DetectorFlags(isShiftingOreFound, isDiamondOreFound, isAmethystFound, areRailsFound, isSculkVeinFound));
    }

    protected void oreFoundEvent(ServerPlayer player, DetectorFlags detectorFlags)
    {
        if (detectorFlags.isShiftingOreFound())
            new PlaySoundPacket(SoundType.ORE_FOUND).sendToPlayer(player);
    }

    private void tryExchange(Level level, BlockPos currentPos, BlockPos closerPos)
    {
        if (!isShiftingOre(level, currentPos)) return;
        if (!canBeReplaced(level, closerPos)) return;
        exchange(level, currentPos, closerPos);
    }

    protected int getShiftingRadius()
    {
        return 1;
    }

    protected int getOreDepth()
    {
        return 5;
    }

    protected int getAmethystDetectionDepth()
    {
        return 20;
    }

    protected int getRailsDetectionDepth()
    {
        return 64;
    }

    protected int getSculkDetectionDepth()
    {
        return 64;
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
        if (!utils.dimension.isOverworld(level.dimension())) return false;

        var block = level.getBlockState(pos).getBlock();

        return block == Blocks.STONE || block == Blocks.GRANITE || block == Blocks.DIORITE || block == Blocks.ANDESITE
                || block == Blocks.DEEPSLATE || block == Blocks.TUFF || block == Blocks.COBBLESTONE || block == Blocks.COBBLED_DEEPSLATE;
    }

    protected boolean isShiftingOre(Block block)
    {
        return utils.blocks.types.isOre(block) && !utils.blocks.types.isDiamondOre(block) && !utils.blocks.types.isSculk(block);
    }

    private boolean isShiftingOre(Level level, BlockPos pos)
    {
        var block = level.getBlockState(pos).getBlock();
        return isShiftingOre(block);
    }

    private boolean isAmethystGeode(Block block)
    {
        return block == Blocks.SMOOTH_BASALT || block == Blocks.CALCITE;
    }
}
