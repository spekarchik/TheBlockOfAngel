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
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.material.FluidState;

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

        var pos = context.getClickedPos();
        if (!canBeReplaced(level, pos) && !isOre(level, pos) && !isDiamondOre(level.getBlockState(pos).getBlock()))
        {
            return InteractionResult.PASS;
        }

        return shiftOres(player, level, pos, context.getClickedFace());
    }

    private InteractionResult shiftOres(Player player, Level level, BlockPos pos, Direction clickedFace)
    {
        int radius = getShiftingRadius();
        int depth = getShiftDepth();
        int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int initialDepth, inc;
        Function<BlockPos, BlockPos> closerPosFunc;

        boolean isDiamondOreFound = false;
        boolean isOreFound = false;

        if (clickedFace == Direction.UP || clickedFace == Direction.DOWN)
        {
            if (clickedFace == Direction.UP)
            {
                initialDepth = depth;
                inc = 1;
                closerPosFunc = BlockPos::above;
            }
            else
            {
                initialDepth = -depth;
                inc = -1;
                closerPosFunc = BlockPos::below;
            }

            for (int x = posX - radius; x <= posX + radius; x++)
                for (int z = posZ - radius; z <= posZ + radius; z++)
                    for (int y = posY - initialDepth; y != posY; y += inc)
                    {
                        var currentPos = new BlockPos(x, y, z);
                        var result = tryExchange(level, currentPos, closerPosFunc.apply(currentPos));
                        switch (result)
                        {
                            case ORE -> isOreFound = true;
                            case DIAMOND_ORE -> isDiamondOreFound = true;
                            default -> {}
                        }
                    }

            if (isOreFound || isDiamondOreFound) oreFoundEvent((ServerPlayer) player, isOreFound, isDiamondOreFound);

            return InteractionResult.CONSUME;
        }

        if (clickedFace == Direction.SOUTH || clickedFace == Direction.NORTH)
        {
            // south: z++
            if (clickedFace == Direction.SOUTH)
            {
                initialDepth = depth;
                inc = 1;
                closerPosFunc = BlockPos::south;
            }
            else
            {
                initialDepth = -depth;
                inc = -1;
                closerPosFunc = BlockPos::north;
            }

            for (int x = posX - radius; x <= posX + radius; x++)
                for (int y = posY - radius; y <= posY + radius; y++)
                    for (int z = posZ - initialDepth; z != posZ; z += inc)
                    {
                        var currentPos = new BlockPos(x, y, z);
                        var result = tryExchange(level, currentPos, closerPosFunc.apply(currentPos));
                        switch (result)
                        {
                            case ORE -> isOreFound = true;
                            case DIAMOND_ORE -> isDiamondOreFound = true;
                            default -> {}
                        }
                    }

            if (isOreFound || isDiamondOreFound) oreFoundEvent((ServerPlayer) player, isOreFound, isDiamondOreFound);

            return InteractionResult.CONSUME;
        }

        if (clickedFace == Direction.EAST || clickedFace == Direction.WEST)
        {
            // east: x++
            if (clickedFace == Direction.EAST)
            {
                initialDepth = depth;
                inc = 1;
                closerPosFunc = BlockPos::east;
            }
            else
            {
                initialDepth = -depth;
                inc = -1;
                closerPosFunc = BlockPos::west;
            }

            for (int z = posZ - radius; z <= posZ + radius; z++)
                for (int y = posY - radius; y <= posY + radius; y++)
                    for (int x = posX - initialDepth; x != posX; x += inc)
                    {
                        var currentPos = new BlockPos(x, y, z);
                        var result = tryExchange(level, currentPos, closerPosFunc.apply(currentPos));
                        switch (result)
                        {
                            case ORE -> isOreFound = true;
                            case DIAMOND_ORE -> isDiamondOreFound = true;
                            default -> {}
                        }
                    }

            if (isOreFound || isDiamondOreFound) oreFoundEvent((ServerPlayer) player, isOreFound, isDiamondOreFound);

            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    protected void oreFoundEvent(ServerPlayer player, boolean isOreFound, boolean isDiamondOreFound)
    {
        if (isOreFound)
            new OreDetectedPacket(false).sendToPlayer(player);
    }

    private OreType tryExchange(Level level, BlockPos currentPos, BlockPos closerPos)
    {
        var block = level.getBlockState(currentPos).getBlock();
        boolean isDiamondOre = block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE;

        if (!isOre(level, currentPos)) return isDiamondOre ? OreType.DIAMOND_ORE : OreType.NOT_ORE;
        if (!canBeReplaced(level, closerPos)) return OreType.ORE;
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
        var currectBlockState = level.getBlockState(currentPos);
        var upperBlockState = level.getBlockState(closerPos);
        level.setBlock(currentPos, upperBlockState, 11);
        level.setBlock(closerPos, currectBlockState, 11);
    }

    protected boolean canBeReplaced(Level level, BlockPos pos)
    {
        if (!Utils.isOverworld(level.dimension())) return false;

        var block = level.getBlockState(pos).getBlock();

        return block == Blocks.AIR || (block instanceof LiquidBlock && level.getFluidState(pos).getAmount() < FluidState.AMOUNT_FULL)
                || block == Blocks.STONE || block == Blocks.GRANITE || block == Blocks.DIORITE || block == Blocks.ANDESITE
                || block == Blocks.DEEPSLATE || block == Blocks.TUFF || block == Blocks.COBBLESTONE || block == Blocks.COBBLED_DEEPSLATE;
    }

    protected boolean isOre(Block block)
    {
        return block instanceof OreBlock && !isDiamondOre(block);
    }

    private boolean isOre(Level level, BlockPos pos)
    {
        var block = level.getBlockState(pos).getBlock();
        return isOre(block);
    }

    private boolean isDiamondOre(Block block)
    {
        return block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE;
    }
}
