package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class Builder extends WorkRod
{
    public Builder(Tier material, Properties properties)
    {
        super(material, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level();
        var offHandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        var pos = context.getClickedPos();

        var offHandItem = offHandItemStack.getItem();
        var success = false;

        if (offHandItem instanceof BlockItem)
        {
            boolean isMagneticMode = this.isEnhanced() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT);
            success = isMagneticMode
                    ? placeBlocksMagnetic(player, level, pos, context.getClickedFace())
                    : placeBlocks(player, level, pos, context.getClickedFace());
        }

        var result = getToolInteractionResult(success, level.isClientSide());

        if (result == InteractionResult.CONSUME || result == InteractionResult.CONSUME_PARTIAL)
        {
            causePlayerExhaustion(player);
        }

        return result;
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
                    //if (y == posY + 2 && x == posX && z == posZ) continue;
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
        if (hasCriticalDamage(toolItemStack)) return false;

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
                var soundEvent = placingBlock.defaultBlockState().getSoundType(level, pos, player).getPlaceSound();
                new PlaySoundPacket(soundEvent).sendToPlayer(serverPlayer);
            }

            damageMainHandItemIfSurvivalIgnoreClient(player, level);
            offHandItemStack.setCount(itemCount - 1);
        }

        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 0; i <= 9; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1,  i == 4 || i == 2, false, i == 6, i == 8));
        }
    }

    protected boolean isPlant(Block block)
    {
        return block instanceof BushBlock || block instanceof GrowingPlantBlock;
    }

    protected boolean isAirOrWater(BlockState blockState)
    {
        return blockState.isAir() || blockState.is(Blocks.WATER);
    }

    protected boolean isBlockCompatible(Level level, BlockState blockState, BlockPos pos)
    {
        var block = blockState.getBlock();
        return !blockState.hasBlockEntity() && !(block instanceof FallingBlock)
                && (blockState.isSolidRender(level, pos) || utils.blocks.types.isGlassBlock(block));
    }

    @Override
    public boolean isEnhanced()
    {
        return true;
    }
}
