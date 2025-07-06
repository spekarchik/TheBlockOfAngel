package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.utils.SoundType;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TrackLayer extends WorkRod
{
    public TrackLayer(ModToolMaterial material, Properties properties)
    {
        super(material, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level();
        var offHandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int offHandItemCountBeforeUse = offHandItemStack.getCount();
        var pos = context.getClickedPos();

        var offHandItem = offHandItemStack.getItem();
        var success = false;

        if (offHandItem instanceof BlockItem)
        {
            success = placeBlocks(player, level, pos, context.getClickedFace());
        }

        var result = getToolInteractionResult(success, level.isClientSide());

        if (result == InteractionResult.SUCCESS || result == InteractionResult.SUCCESS_SERVER)
        {
            int offHandItemCountAfterUse = offHandItemStack.getCount();
            if (offHandItemCountAfterUse < offHandItemCountBeforeUse)
                causePlayerExhaustion(player);
        }

        return result;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity)
    {
        if (hasCriticalDamage(itemStack)) return false;

        if (!level.isClientSide() && isTrackLayerCompatible(blockState) && livingEntity instanceof Player player)
        {
            dropBlocks(player, level, pos);
            damageMainHandItemIfSurvivalIgnoreClient(player, level);
        }

        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
    {
        if (hasCriticalDamage(itemStack)) return 1F;

        return isTrackLayerCompatible(blockState) ? 10F : 1F;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return isTrackLayerCompatible(state);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        for (int i = 0; i <= 8; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1 || i == 3)
                    .styledAs(TextStyle.ImportantNotice, i == 5)
                    .styledAs(TextStyle.DarkGray, i == 7)
                    .apply();
        }
    }

    protected boolean dropBlocks(Player player, Level level, BlockPos pos)
    {
        final int DROP_LENGTH = 8;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int shiftX = 1, shiftZ = 1, incX = 1, incZ = 1;
        switch (player.getDirection())
        {
            case NORTH -> // towards negative Z ??
            {
                shiftZ = -DROP_LENGTH;
                incZ = -1;
            }
            case SOUTH ->
            {
                shiftZ = DROP_LENGTH;
            }
            case EAST ->
            {
                shiftX = DROP_LENGTH;
            }
            case WEST -> // towards negative X
            {
                shiftX = -DROP_LENGTH;
                incX = -1;
            }
        }

        boolean haveAnyDone = false;
        final var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        final var originBlock = level.getBlockState(pos).getBlock();

        int y = posY;
        for (int x = posX; x != posX + shiftX; x += incX)
            for (int z = posZ; z != posZ + shiftZ; z += incZ)
            {
                var updatedPos = checkNextPosToDrop(level, new BlockPos(x, y, z), originBlock);
                y = updatedPos.getY();
                boolean hasDone = dropBlock(player, level, originBlock, new BlockPos(x, y, z), toolItemStack, true);
                if (hasDone)
                    haveAnyDone = true;
            }

        return haveAnyDone;
    }

    protected boolean placeBlocks(Player player, Level level, BlockPos pos, Direction facing)
    {
        if (facing != Direction.UP) return false;

        var offHandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!(offHandItemStack.getItem() instanceof BlockItem blockItem)) return false;

        var placingBlock = blockItem.getBlock();

        if (!isTrackLayerCompatible(placingBlock.defaultBlockState()))
            return false;

        final int MAX_PLACEMENT_LENGTH = 65;
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int shiftX = 0, shiftZ = 0, increment = 0;

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
        var originBlock = level.getBlockState(pos).getBlock();

        int y = utils.blocks.types.isRail(originBlock) ? posY - 1 : posY; // only rails can be continued when click on it
        for (int x = posX; x != posX + shiftX; x += increment)
            for (int z = posZ; z != posZ + shiftZ; z += increment)
            {
                var updatedPos = checkPosToPlaceOn(level, new BlockPos(x, y, z), placingBlock);
                y = updatedPos.getY();
                boolean hasPlaced = placeBlock(player, level, originBlock, updatedPos, facing, toolItemStack, placingBlock);
                if (hasPlaced)
                    haveAnyPlaced = true;
                else
                    return haveAnyPlaced;
            }

        return haveAnyPlaced;
    }

    private BlockPos checkPosToPlaceOn(Level level, BlockPos originPos, Block placingBlock)
    {
        var originBlockState = level.getBlockState(originPos);
        var originBlock = originBlockState.getBlock(); // the block to place on

        boolean areBothFence = originBlock instanceof FenceBlock && placingBlock instanceof FenceBlock;
        boolean areBothWall = originBlock instanceof WallBlock && placingBlock instanceof WallBlock;
        boolean areBothFenceOrWall = areBothWall || areBothFence;
        if (areBothFenceOrWall) return originPos; // we can place fence on lower fence
        if (areSimilar(originBlock, placingBlock)) return originPos.below();

        // check to go up
        var upPos = originPos.above();
        boolean isUpperBlockSolid = isBlockSolidOrTransparent(level, upPos);

        if (isUpperBlockSolid)
        {
            var theBlockStateAboveUpper = level.getBlockState(upPos.above());
            var theBlockAboveUpper = theBlockStateAboveUpper.getBlock();
            boolean canGoUp = theBlockStateAboveUpper.isAir() || theBlockAboveUpper instanceof VegetationBlock
                    || isAllowedReplaceWater(theBlockStateAboveUpper, placingBlock) || areSimilar(theBlockAboveUpper, placingBlock);

            return canGoUp ? upPos : originPos;
        }

        // check to go down
        if (originBlockState.isAir() || originBlock instanceof VegetationBlock || isAllowedReplaceWater(originBlockState, placingBlock))
        {
            var updatedPos = originPos.below();
            boolean isBlockBelowSolid = isBlockSolidOrTransparent(level, updatedPos);
            return isBlockBelowSolid ? updatedPos : originPos;
        }

        return originPos;
    }

    private BlockPos checkNextPosToDrop(Level level, BlockPos originPos, Block firstDroppedBlock)
    {
        var blockState = level.getBlockState(originPos);
        var block = blockState.getBlock();

        if (areSimilar(block, firstDroppedBlock)) return originPos;

        if (areSimilar(level.getBlockState(originPos.below()).getBlock(), firstDroppedBlock))
            return originPos.below();

        if (areSimilar(level.getBlockState(originPos.above()).getBlock(), firstDroppedBlock))
            return originPos.above();

        return originPos;
    }

    private boolean isTrackLayerCompatible(BlockState blockState)
    {
        return blockState.is(BlockRegistry.TRACK_LAYER_COMPATIBLE);
    }

    private boolean dropBlock(Player player, Level level, Block originBlock, BlockPos pos, ItemStack toolItemStack, boolean shouldDrop)
    {
        if (hasCriticalDamage(toolItemStack)) return false;

        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();
        boolean canDrop = areSimilar(originBlock, block);

        if (!canDrop) return false;

        if (!level.isClientSide())
        {
            level.destroyBlock(pos, shouldDrop);
            damageMainHandItemIfSurvivalIgnoreClient(player, level);
        }

        return true;
    }

    private boolean placeBlock(Player player, Level level, Block originBlock, BlockPos pos, Direction facing, ItemStack toolItemStack, Block placingBlock)
    {
        if (hasCriticalDamage(toolItemStack)) return false;

        var offHandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int itemCount = offHandItemStack.getCount();
        if (itemCount < 1) return false;

        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        boolean areBothFence = block instanceof FenceBlock && placingBlock instanceof FenceBlock;
        boolean areBothWall = block instanceof WallBlock && placingBlock instanceof WallBlock;
        boolean areBothFenceOrWall = areBothWall || areBothFence;
        if (!areBothFenceOrWall && areSimilar(placingBlock, block)) return true;

        boolean isBlockSolid = isBlockSolidOrTransparent(level, pos);
        if (block instanceof LiquidBlock || blockState.isAir() || (!isBlockSolid && !areBothFenceOrWall)) return false;

        var upPos = pos.above();
        var upperBlockState = level.getBlockState(upPos);
        var upperBlock = upperBlockState.getBlock();
        boolean isUpperBlockVegetation = upperBlock instanceof VegetationBlock;
        boolean canSkip = upperBlock != Blocks.REDSTONE_WIRE && areSimilar(placingBlock, upperBlock);
        boolean isUpperWaterReplacingByRails = isAllowedReplaceWater(upperBlockState, placingBlock);

        if (canSkip) return true;

        if (!upperBlockState.isAir() && !isUpperWaterReplacingByRails && !isUpperBlockVegetation) return false;

        if (!level.isClientSide())
        {
            if (isUpperBlockVegetation)
            {
                level.destroyBlock(upPos, false);
            }

            level.setBlock(upPos, placingBlock.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);

            damageMainHandItemIfSurvivalIgnoreClient(player, level);

            if (!player.isCreative())
                offHandItemStack.setCount(itemCount - 1);
        }

        var soundType = getSoundType(placingBlock);
        utils.sound.playSoundByBlock(player, upPos, soundType);

        return true;
    }

    private boolean areSimilar(Block block1, Block block2)
    {
        return block1 == block2
                || (block1 instanceof BaseRailBlock && block2 instanceof BaseRailBlock)
                || (block1 instanceof FenceBlock && block2 instanceof FenceBlock)
                || (block1 instanceof WallBlock && block2 instanceof WallBlock);
    }

    private boolean isAllowedReplaceWater(BlockState replacingBlockState, Block placingBlock)
    {
        return replacingBlockState.getBlock() == Blocks.WATER
                && utils.blocks.types.isRail(placingBlock);
    }

    private boolean isBlockSolidOrTransparent(Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();
        return blockState.isSolidRender() || block instanceof TransparentBlock;
    }

    @NotNull
    private SoundType getSoundType(Block placingBlock)
    {
        var soundType = SoundType.UNDEFINED;

        if (utils.blocks.types.isRail(placingBlock))
        {
            soundType = SoundType.RAIL_PLACED;
        }
        else if (placingBlock instanceof FenceBlock)
        {
            soundType = SoundType.WOOD_PLACED;
        }
        else if (placingBlock instanceof WallBlock)
        {
            soundType = SoundType.STONE_PLACED;
        }
        else
        {
            soundType = SoundType.REDSTONE_WIRE_PLACED;
        }
        return soundType;
    }
}
