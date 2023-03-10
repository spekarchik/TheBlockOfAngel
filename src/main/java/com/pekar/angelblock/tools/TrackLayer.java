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

    // 1. наклоны
    // +2. удаляет растения на пути
    // +3. быстро дропает рельсы по левой кнопке мыши
    // +4. пропускает уже уложенные рельсы
    // +5. дальность - всегда 64 или пока есть рельсы
    // 6. останавливается если разница в высоте > 2 блоков
    // +7. прокладывает также красную пыль

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
            dropBlocks(player, level, pos, 1, true);
        }

        return super.mineBlock(itemStack, level, blockState, pos, livingEntity);
    }

    protected boolean dropBlocks(Player player, Level level, BlockPos pos, int grabWidth, boolean shouldDrop)
    {
        final int DROP_LENGTH = 8;
        final int DROP_HALF_WIDTH = grabWidth / 2;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int a1 = 0, a2 = 0, b1 = 0, b2 = 0;
        switch (player.getDirection())
        {
            case NORTH:
                a1 = DROP_HALF_WIDTH; a2 = DROP_HALF_WIDTH; b1 = DROP_LENGTH - 1; b2 = 0; break;

            case SOUTH:
                a1 = DROP_HALF_WIDTH; a2 = DROP_HALF_WIDTH; b1 = 0; b2 = DROP_LENGTH - 1; break;

            case EAST:
                a1 = 0; a2 = DROP_LENGTH - 1; b1 = DROP_HALF_WIDTH; b2 = DROP_HALF_WIDTH; break;

            case WEST:
                a1 = DROP_LENGTH - 1; a2 = 0; b1 = DROP_HALF_WIDTH; b2 = DROP_HALF_WIDTH; break;
        }

        boolean haveAnyDone = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var originBlock = level.getBlockState(pos).getBlock();

        for (int x = posX - a1; x <= posX + a2; x++)
            for (int z = posZ - b1; z <= posZ + b2; z++)
            {
                boolean hasDone = dropBlock(player, level, originBlock, new BlockPos(x, posY, z), toolItemStack, shouldDrop);
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
            case NORTH:
                shiftX = -1; shiftZ = -MAX_PLACEMENT_LENGTH; increment = -1; break;

            case SOUTH:
                shiftX = 1; shiftZ = MAX_PLACEMENT_LENGTH; increment = 1; break;

            case EAST:
                shiftX = MAX_PLACEMENT_LENGTH; shiftZ = 1; increment = 1; break;

            case WEST:
                shiftX = -MAX_PLACEMENT_LENGTH; shiftZ = -1; increment = -1; break;
        }

        boolean haveAnyPlaced = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var originBlock = level.getBlockState(pos).getBlock();

        for (int x = posX; x != posX + shiftX; x += increment)
            for (int z = posZ; z != posZ + shiftZ; z += increment)
            {
                boolean hasPlaced = placeBlock(player, level, originBlock, new BlockPos(x, posY, z), facing, toolItemStack, placingBlock);
                if (hasPlaced)
                    haveAnyPlaced = true;
                else
                    return haveAnyPlaced;
            }

        return haveAnyPlaced;
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

        if (block instanceof LiquidBlock || blockState.isAir() || !blockState.isSolidRender(level, pos)) return false;

        var upperBlockState = level.getBlockState(pos.above());
        var upperBlock = upperBlockState.getBlock();
        boolean isBushBlock = upperBlock instanceof BushBlock;
        boolean canSkip = areSimilar(offHandBlock, upperBlock);

        if (canSkip) return true;

        if (!upperBlockState.isAir() && !isBushBlock) return false;

        if (!level.isClientSide())
        {
            if (isBushBlock)
            {
                level.destroyBlock(pos.above(), false);
            }

            level.setBlock(pos.above(), placingBlock.defaultBlockState(), 11);
            level.updateNeighborsAt(pos.above(), placingBlock);

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
