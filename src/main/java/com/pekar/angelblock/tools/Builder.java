package com.pekar.angelblock.tools;

import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
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
        int offHandItemCountBeforeUse = offHandItemStack.getCount();
        var pos = context.getClickedPos();

        var offHandItem = offHandItemStack.getItem();
        var success = false;

        if (offHandItem instanceof BlockItem && player.getFoodData().getFoodLevel() > 0)
        {
            boolean isMagneticMode = this.isEnhanced() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT);
            success = isMagneticMode
                    ? placeBlocksMagnetic(player, level, pos, context.getClickedFace())
                    : placeBlocks(player, level, pos, context.getClickedFace());
        }

        var result = getToolInteractionResult(success, level.isClientSide());

        if (result == InteractionResult.CONSUME || result == InteractionResult.CONSUME_PARTIAL)
        {
            int offHandItemCountAfterUse = offHandItemStack.getCount();
            if (offHandItemCountAfterUse < offHandItemCountBeforeUse)
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

        var isReplaceable = isBuilderReplaceable(blockState);
        if (!isReplaceable) return false;

        if (placingBlock instanceof FallingBlock && level.isEmptyBlock(pos.below())) return false;

        if (!level.isClientSide())
        {
            if (!blockState.isAir())
            {
                level.destroyBlock(pos, false);
                if (isPlant(level.getBlockState(pos.above()).getBlock()))
                    level.destroyBlock(pos.above(), false);
            }

            level.setBlock(pos, placingBlock.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            level.updateNeighborsAt(pos, placingBlock);

            damageMainHandItemIfSurvivalIgnoreClient(player, level);

            if (!player.isCreative())
                offHandItemStack.setCount(itemCount - 1);
        }

        var soundEvent = placingBlock.defaultBlockState().getSoundType(level, pos, player).getPlaceSound();
        utils.sound.playSoundByBlock(player, pos, soundEvent);

        return true;
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        for (int i = 0; i <= 9; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1)
                    .styledAs(TextStyle.Subheader, i == 4 || i == 2)
                    .styledAs(TextStyle.ImportantNotice, i == 6)
                    .styledAs(TextStyle.DarkGray, i >= 7 && i <= 8)
                    .apply();

            if (i == 6)
                tooltip.addEmptyLine();
        }
    }

    protected boolean isPlant(Block block)
    {
        return block instanceof BushBlock || block instanceof GrowingPlantBlock;
    }

    private boolean isBuilderReplaceable(BlockState blockState)
    {
        return blockState.is(BlockTags.REPLACEABLE) && !blockState.is(Blocks.LAVA);
    }

    protected boolean isAirOrWater(BlockState blockState)
    {
        return blockState.isAir() || blockState.is(Blocks.WATER);
    }

    protected boolean isBlockCompatible(Level level, BlockState blockState, BlockPos pos)
    {
        var block = blockState.getBlock();
        return !blockState.hasBlockEntity()
                && (blockState.isSolidRender(level, pos) || utils.blocks.types.isGlassBlock(block));
    }

    @Override
    public boolean isEnhanced()
    {
        return true;
    }
}
