package com.pekar.angelblock.tools;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.utils.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Objects;
import java.util.function.Function;

public abstract class MagneticRod extends ModRod
{
    private static final int MAGNETIC_USE_EXHAUSTION_MULTIPLIER = 1;
    private static final int NORMAL_USE_EXHAUSTION_MULTIPLIER = 16;
    private static final int MINE_EXHAUSTION_MULTIPLIER = 2;

    public MagneticRod(ModToolMaterial material, boolean isMagnetic, Properties properties)
    {
        super(material, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = useOnInternal(context);
        if (result == InteractionResult.PASS) return InteractionResult.FAIL;
        if (result == InteractionResult.SUCCESS || result == InteractionResult.SUCCESS_SERVER)
        {
            if (!context.getLevel().isClientSide())
            {
                var player = context.getPlayer();
                var exhaustionMultiplier = isEnhanced() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT)
                        ? MAGNETIC_USE_EXHAUSTION_MULTIPLIER
                        : NORMAL_USE_EXHAUSTION_MULTIPLIER;

                utils.player.causePlayerExhaustion(player, exhaustionMultiplier);
            }
        }
        return result;
    }

    protected InteractionResult useOnInternal(UseOnContext context)
    {
        var player = context.getPlayer();

        if (player == null) return InteractionResult.FAIL;

        var itemStack = player.getItemInHand(context.getHand());

        if (hasCriticalDamage(itemStack) || player.getFoodData().getFoodLevel() <= 0) return InteractionResult.FAIL;

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
            return InteractionResult.FAIL;
        }

        boolean shifted = shiftOres(player, level, pos, context.getClickedFace());
        return shifted ? getToolInteractionResult(shifted, isClientSide) : InteractionResult.FAIL;
    }

    private boolean shiftOres(Player player, Level level, BlockPos pos, Direction clickedFace)
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

        return shiftOreBlocks(player, level, pos, clickedFace, initialDepth, getDepthCoord, getRadiusCoord1, getRadiusCoord2, createPos);
    }

    private boolean shiftOreBlocks(Player player, Level level, BlockPos pos, Direction clickedFace, int initialDepth, Function<BlockPos, Integer> getDepthCoord, Function<BlockPos, Integer> getRadiusCoord1, Function<BlockPos, Integer> getRadiusCoord2, TriFunction<Integer, Integer, Integer, BlockPos> createPos)
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

        boolean shifted = false;

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
                    boolean isShiftingOre = isShiftingOre(level, currentPos);
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

                    boolean result = tryExchange(level, currentPos, replacedPos);
                    if (result) shifted = true;
                }
            }

        if (shifted || isDiamondOreFound || isAmethystFound || areRailsFound || isSculkVeinFound)
        {
            if (player instanceof ServerPlayer serverPlayer)
                oreFoundEvent(serverPlayer, pos, new DetectorFlags(shifted, isDiamondOreFound, isAmethystFound, areRailsFound, isSculkVeinFound));

            return shifted;
        }

        return false;
    }

    protected void oreFoundEvent(ServerPlayer player, BlockPos pos, DetectorFlags detectorFlags)
    {
        if (detectorFlags.isShiftingOreFound())
            utils.sound.playSoundOnBothSides(player, pos, SoundType.ORE_FOUND, SoundSource.BLOCKS, 5F);
    }

    private boolean tryExchange(Level level, BlockPos currentPos, BlockPos closerPos)
    {
        if (!isShiftingOre(level, currentPos)) return false;
        if (!canBeReplaced(level, closerPos)) return false;

        if (!level.isClientSide())
            exchange(level, currentPos, closerPos);

        return true;
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
        level.setBlock(currentPos, upperBlockState, Block.UPDATE_ALL);
        level.setBlock(closerPos, currentBlockState, Block.UPDATE_ALL);
    }

    protected boolean canBeReplaced(Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var replaceables = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Main.MODID, "overworld_replaceables"));
        return blockState.is(replaceables);
    }

    protected boolean isShiftingOre(Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var shiftingOres = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Main.MODID, "overworld_shifting_ores"));
        return blockState.is(shiftingOres);
    }

    private boolean isAmethystGeode(Block block)
    {
        return block == Blocks.SMOOTH_BASALT || block == Blocks.CALCITE;
    }

    protected void causeMinePlayerExhaustion(Player player)
    {
        utils.player.causePlayerExhaustion(player, MINE_EXHAUSTION_MULTIPLIER);
    }
}
