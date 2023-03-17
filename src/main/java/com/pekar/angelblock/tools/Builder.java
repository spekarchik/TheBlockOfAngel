package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class Builder extends ModRod
{
    public Builder(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;
        var offHandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        var pos = context.getClickedPos();

        var offHandItem = offHandItemStack.getItem();
        var success = false;

        if (offHandItem instanceof BlockItem)
        {
            boolean isMagneticMode = isEnhancedRod() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT.get());
            success = isMagneticMode
                    ? placeBlocksMagnetic(player, level, pos, context.getClickedFace())
                    : placeBlocks(player, level, pos, context.getClickedFace());
        }

        return success ? InteractionResult.sidedSuccess(level.isClientSide()) : InteractionResult.PASS;
    }

    protected boolean placeBlocks(Player player, Level level, BlockPos pos, Direction facing)
    {
        var offHandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!(offHandItemStack.getItem() instanceof BlockItem blockItem)) return false;

        var placingBlock = blockItem.getBlock();
        boolean isPlacingBlockCompatible = isBlockCompatible(level, placingBlock.defaultBlockState(), pos);

        var clickedBlockState = level.getBlockState(pos);
        var clickedBlock = clickedBlockState.getBlock();

        if (!isPlacingBlockCompatible) return false;

        var updatedPos = clickedBlock != placingBlock ? pos.relative(facing) : pos;
        final int posX = updatedPos.getX(), posY = updatedPos.getY(), posZ = updatedPos.getZ();

        final int MAX_PLACEMENT_LENGTH = 65; // one more - first block can be skipped
        int shiftX = 0, shiftZ = 0, shiftY = 0, increment = 0;

        switch (player.getDirection())
        {
            case NORTH ->
            {
                shiftX = -1;
                shiftZ = -MAX_PLACEMENT_LENGTH;
                increment = -1;
            }
            case SOUTH ->
            {
                shiftX = 1;
                shiftZ = MAX_PLACEMENT_LENGTH;
                increment = 1;
            }
            case EAST ->
            {
                shiftX = MAX_PLACEMENT_LENGTH;
                shiftZ = 1;
                increment = 1;
            }
            case WEST ->
            {
                shiftX = -MAX_PLACEMENT_LENGTH;
                shiftZ = -1;
                increment = -1;
            }
        }

        boolean haveAnyPlaced = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);

        for (int x = posX; x != posX + shiftX; x += increment)
            for (int z = posZ; z != posZ + shiftZ; z += increment)
            {
                boolean hasPlaced = placeBlock(player, level, clickedBlock, pos, new BlockPos(x, posY, z), facing, toolItemStack, placingBlock);
                if (hasPlaced)
                    haveAnyPlaced = true;
                else
                    return haveAnyPlaced;
            }

        return haveAnyPlaced;
    }

    protected boolean placeBlocksMagnetic(Player player, Level level, BlockPos pos, Direction facing)
    {
        var offHandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!(offHandItemStack.getItem() instanceof BlockItem blockItem)) return false;

        var placingBlock = blockItem.getBlock();
        boolean isPlacingBlockCompatible = isBlockCompatible(level, placingBlock.defaultBlockState(), pos);

        var clickedBlockState = level.getBlockState(pos);
        var clickedBlock = clickedBlockState.getBlock();

        if (!isPlacingBlockCompatible) return false;

        var updatedPos = pos.relative(player.getDirection(), 2);
        final int posX = updatedPos.getX(), posY = updatedPos.getY(), posZ = updatedPos.getZ();

        boolean haveAnyPlaced = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);

        for (int y = posY + 1; y <= posY + 3; y++)
            for (int x = posX - 1; x <= posX + 1; x++)
                for (int z = posZ - 1; z <= posZ + 1; z++)
                {
                    if (y == posY + 2 && x == posX && z == posZ) continue;
                    var posToPlace = new BlockPos(x, y, z);
                    if (posToPlace.equals(pos.relative(player.getDirection()).above(2))) continue;

                    boolean hasPlaced = placeBlock(player, level, clickedBlock, pos, posToPlace, facing, toolItemStack, placingBlock);
                    if (hasPlaced)
                        haveAnyPlaced = true;
                }

        return haveAnyPlaced;
    }

    protected boolean placeBlock(Player player, Level level, Block clickedBlock, BlockPos clickedPos, BlockPos pos, Direction facing, ItemStack toolItemStack, Block placingBlock)
    {
        if (isBroken(toolItemStack)) return false;

        var offHandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int itemCount = offHandItemStack.getCount();
        if (itemCount < 1) return false;

        if (pos.equals(clickedPos)) return true;

        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        boolean isPlant = isPlant(block);
        if (!isAirOrWater(blockState) && !isPlant) return false;

        if (!level.isClientSide())
        {
            if (isPlant)
            {
                level.destroyBlock(pos, false);
                if (isPlant(level.getBlockState(pos.above()).getBlock()))
                    level.destroyBlock(pos.above(), false);
            }

            level.setBlock(pos, placingBlock.defaultBlockState(), 11);
            level.updateNeighborsAt(pos, placingBlock);

            if (player instanceof ServerPlayer serverPlayer)
            {
                var soundEvent = placingBlock.defaultBlockState().getSoundType().getPlaceSound();
                new PlaySoundPacket(soundEvent).sendToPlayer(serverPlayer);
            }

            damageItemIfSurvival(player, level, pos, placingBlock.defaultBlockState());
            offHandItemStack.setCount(itemCount - 1);
        }

        return true;
    }

    protected boolean isPlant(Block block)
    {
        return block instanceof BushBlock || block instanceof GrowingPlantBlock;
    }

    protected boolean isAirOrWater(BlockState blockState)
    {
        return blockState.isAir() || blockState.getMaterial() == Material.WATER;
    }

    protected boolean isBlockCompatible(Level level, BlockState blockState, BlockPos pos)
    {
        return !blockState.hasBlockEntity() && !(blockState.getBlock() instanceof FallingBlock)
                && (blockState.isSolidRender(level, pos) || blockState.getBlock() instanceof AbstractGlassBlock);
    }

    @Override
    public boolean isEnhancedRod()
    {
        return true;
    }
}
