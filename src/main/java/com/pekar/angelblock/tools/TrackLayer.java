package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
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

public class TrackLayer extends ModRod
{
    public TrackLayer(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
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
            success = placeBlocks(player, level, pos, context.getClickedFace());
        }

        return success ? InteractionResult.sidedSuccess(level.isClientSide()) : InteractionResult.PASS;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity)
    {
        var block = blockState.getBlock();
        if (isTrackLayerCompatible(block) && livingEntity instanceof Player player && !level.isClientSide())
        {
            damageItemIfSurvival(player, level, pos.below(), level.getBlockState(pos.below()));
            dropBlocks(player, level, pos);
        }

        return super.mineBlock(itemStack, level, blockState, pos, livingEntity);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
    {
        return isTrackLayerCompatible(blockState.getBlock()) ? 10F : 1F;
    }

    protected boolean dropBlocks(Player player, Level level, BlockPos pos)
    {
        final int DROP_LENGTH = 8;
        final int DROP_HALF_WIDTH = 0;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int a1 = 0, a2 = 0, b1 = 0, b2 = 0;
        switch (player.getDirection())
        {
            case NORTH ->
            {
                a1 = DROP_HALF_WIDTH;
                a2 = DROP_HALF_WIDTH;
                b1 = DROP_LENGTH - 1;
                b2 = 0;
            }
            case SOUTH ->
            {
                a1 = DROP_HALF_WIDTH;
                a2 = DROP_HALF_WIDTH;
                b1 = 0;
                b2 = DROP_LENGTH - 1;
            }
            case EAST ->
            {
                a1 = 0;
                a2 = DROP_LENGTH - 1;
                b1 = DROP_HALF_WIDTH;
                b2 = DROP_HALF_WIDTH;
            }
            case WEST ->
            {
                a1 = DROP_LENGTH - 1;
                a2 = 0;
                b1 = DROP_HALF_WIDTH;
                b2 = DROP_HALF_WIDTH;
            }
        }

        boolean haveAnyDone = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var originBlock = level.getBlockState(pos).getBlock();

        int y = posY;
        for (int x = posX - a1; x <= posX + a2; x++)
            for (int z = posZ - b1; z <= posZ + b2; z++)
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

        if (!isTrackLayerCompatible(placingBlock))
            return false;

        final int MAX_PLACEMENT_LENGTH = 64;
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

        int y = posY;
        for (int x = posX; x != posX + shiftX; x += increment)
            for (int z = posZ; z != posZ + shiftZ; z += increment)
            {
                var updatedPos = checkNextPosToPlace(level, new BlockPos(x, y, z), placingBlock);
                y = updatedPos.getY();
                boolean hasPlaced = placeBlock(player, level, originBlock, updatedPos, facing, toolItemStack, placingBlock);
                if (hasPlaced)
                    haveAnyPlaced = true;
                else
                    return haveAnyPlaced;
            }

        return haveAnyPlaced;
    }

    private BlockPos checkNextPosToPlace(Level level, BlockPos originPos, Block placingBlock)
    {
        var originBlockState = level.getBlockState(originPos);
        var originBlock = originBlockState.getBlock();

        if (areSimilar(originBlock, placingBlock)) return originPos.below();

        if (originBlockState.isAir() || originBlock instanceof BushBlock)
        {
            var updatedPos = originPos.below();
            boolean isBelowBlockSolid = level.getBlockState(updatedPos).isSolidRender(level, updatedPos);
            return isBelowBlockSolid ? updatedPos : originPos;
        }

        var upPos = originPos.above();
        var upperBlockState = level.getBlockState(upPos);
        boolean isUpperBlockSolid = upperBlockState.isSolidRender(level, upPos);

        if (isUpperBlockSolid)
        {
            var theBlockStateAboveUpper = level.getBlockState(upPos.above());
            var theBlockAboveUpper = theBlockStateAboveUpper.getBlock();
            boolean canGoUp = theBlockStateAboveUpper.isAir() || theBlockAboveUpper instanceof BushBlock
                    || areSimilar(theBlockAboveUpper, placingBlock);

            return canGoUp ? upPos : originPos;
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

    private boolean isTrackLayerCompatible(Block block)
    {
        return block instanceof BaseRailBlock
                || block instanceof FenceBlock
                || block == Blocks.REDSTONE_WIRE;
    }

    private boolean dropBlock(Player player, Level level, Block originBlock, BlockPos pos, ItemStack toolItemStack, boolean shouldDrop)
    {
        boolean isBroken = toolItemStack.getMaxDamage() - toolItemStack.getDamageValue() <= 1;
        if (isBroken) return false;

        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();
        boolean canDrop = areSimilar(originBlock, block);

        if (!canDrop) return false;

        if (!level.isClientSide())
        {
            level.destroyBlock(pos, shouldDrop);
            damageItemIfSurvival(player, level, pos.below(), level.getBlockState(pos.below()));
        }

        return true;
    }

    private boolean placeBlock(Player player, Level level, Block originBlock, BlockPos pos, Direction facing, ItemStack toolItemStack, Block placingBlock)
    {
        boolean isBroken = toolItemStack.getMaxDamage() - toolItemStack.getDamageValue() <= 1;
        if (isBroken) return false;

        var offHandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int itemCount = offHandItemStack.getCount();
        if (itemCount < 1) return false;

        var offHandBlock = ((BlockItem)offHandItemStack.getItem()).getBlock();

        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (areSimilar(offHandBlock, block)) return true;

        boolean isBlockSolid = blockState.isSolidRender(level, pos);
        if (block instanceof LiquidBlock || blockState.isAir() || !isBlockSolid) return false;

        var upPos = pos.above();
        var upperBlockState = level.getBlockState(upPos);
        var upperBlock = upperBlockState.getBlock();
        boolean isUpperBushBlock = upperBlock instanceof BushBlock;
        boolean canSkip = areSimilar(offHandBlock, upperBlock);

        if (canSkip) return true;

        if (!upperBlockState.isAir() && !isUpperBushBlock) return false;

        if (!level.isClientSide())
        {
            if (isUpperBushBlock)
            {
                level.destroyBlock(upPos, false);
            }

            level.setBlock(upPos, placingBlock.defaultBlockState(), 11);
            level.updateNeighborsAt(upPos, placingBlock);

            if (player instanceof ServerPlayer serverPlayer)
            {
                var soundType = getSoundType(placingBlock);
                new PlaySoundPacket(soundType).sendToPlayer(serverPlayer);
            }

            damageItemIfSurvival(player, level, pos, blockState);
            offHandItemStack.setCount(itemCount - 1);
        }

        return true;
    }

    private boolean areSimilar(Block offHandBlock, Block upperBlock)
    {
        return offHandBlock == upperBlock
                || (offHandBlock instanceof BaseRailBlock && upperBlock instanceof BaseRailBlock)
                || (offHandBlock instanceof FenceBlock && upperBlock instanceof FenceBlock);
    }

    @NotNull
    private SoundType getSoundType(Block placingBlock)
    {
        var soundType = SoundType.UNDEFINED;

        if (placingBlock instanceof BaseRailBlock)
        {
            soundType = SoundType.RAIL_PLACED;
        }
        else if (placingBlock instanceof FenceBlock)
        {
            soundType = SoundType.WOOD_PLACED;
        }
        else
        {
            soundType = SoundType.REDSTONE_WIRE_PLACED;
        }
        return soundType;
    }
}
