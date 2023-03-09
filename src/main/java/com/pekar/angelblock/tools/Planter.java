package com.pekar.angelblock.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;

public class Planter extends ModRod
{
    public Planter(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
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

        if (offHandItemStack.getItem() == Items.BONE_MEAL)
        {
            bonemealPlants(player, level, pos, context.getClickedFace());
        }
        else
        {
            plantOffHandItems(player, level, pos, context.getClickedFace());
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    protected boolean plantOffHandItems(Player player, Level level, BlockPos pos, Direction facing)
    {
        if (facing != Direction.UP) return false;

        var seedInHand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (!(seedInHand.getItem() instanceof BlockItem blockItem)) return false;

        var plantableBlock = blockItem.getBlock();

        if (!isPlanterCompatible(plantableBlock))
            return false;

        int seedCount = seedInHand.getCount();
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int shiftX = 0, shiftZ = 0, increment = 0;

        switch (player.getDirection())
        {
            case NORTH:
                shiftX = -1; shiftZ = -seedCount; increment = -1; break;

            case SOUTH:
                shiftX = 1; shiftZ = seedCount; increment = 1; break;

            case EAST:
                shiftX = seedCount; shiftZ = 1; increment = 1; break;

            case WEST:
                shiftX = -seedCount; shiftZ = -1; increment = -1; break;
        }

        boolean haveAnyTransformed = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var originBlock = level.getBlockState(pos).getBlock();

        for (int x = posX; x != posX + shiftX; x += increment)
            for (int z = posZ; z != posZ + shiftZ; z += increment)
            {
                boolean hasTransformed = plantOffHandItem(player, level, originBlock, new BlockPos(x, posY, z), facing, toolItemStack, plantableBlock);
                if (hasTransformed)
                    haveAnyTransformed = true;
                else
                    return haveAnyTransformed;
            }

        return haveAnyTransformed;
    }

    protected boolean bonemealPlants(Player player, Level level, BlockPos pos, Direction facing)
    {
        var bonemealInHand = player.getItemInHand(InteractionHand.OFF_HAND);
        if (bonemealInHand.getItem() != Items.BONE_MEAL) return false;

        final int MAX_BONEMEALABLE_LENGTH = 64;
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int shiftX = 0, shiftZ = 0, increment = 0;

        switch (player.getDirection())
        {
            case NORTH:
                shiftX = -1; shiftZ = -MAX_BONEMEALABLE_LENGTH; increment = -1; break;

            case SOUTH:
                shiftX = 1; shiftZ = MAX_BONEMEALABLE_LENGTH; increment = 1; break;

            case EAST:
                shiftX = MAX_BONEMEALABLE_LENGTH; shiftZ = 1; increment = 1; break;

            case WEST:
                shiftX = -MAX_BONEMEALABLE_LENGTH; shiftZ = -1; increment = -1; break;
        }

        boolean haveAnyTransformed = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var originBlock = level.getBlockState(pos).getBlock();

        for (int x = posX; x != posX + shiftX; x += increment)
            for (int z = posZ; z != posZ + shiftZ; z += increment)
            {
                boolean hasTransformed = bonemealPlant(player, level, originBlock, new BlockPos(x, posY, z), facing, toolItemStack);
                if (hasTransformed)
                    haveAnyTransformed = true;
                else
                    return haveAnyTransformed;
            }

        return haveAnyTransformed;
    }

    private boolean isPlanterCompatible(Block block)
    {
        return block instanceof BushBlock
                || block == Blocks.CACTUS
                || block == Blocks.BAMBOO
                || block == Blocks.SUGAR_CANE;
    }

    private boolean plantOffHandItem(Player player, Level level, Block originBlock, BlockPos pos, Direction facing, ItemStack toolItemStack, Block plantBlock)
    {
        boolean isBroken = toolItemStack.getMaxDamage() - toolItemStack.getDamageValue() <= 1;
        if (isBroken) return false;

        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (block != originBlock) return false;

        var itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int itemCount = itemStack.getCount();
        if (itemCount < 1) return false;

        var result = plant(player, level, pos, InteractionHand.OFF_HAND, facing, plantBlock);
        if (result == InteractionResult.CONSUME)
        {
            damageItemIfSurvival(player, level, pos, blockState);
            itemStack.setCount(itemCount - 1);
        }

        return result.shouldAwardStats();
    }

    private boolean bonemealPlant(Player player, Level level, Block originBlock, BlockPos pos, Direction facing, ItemStack toolItemStack)
    {
        boolean isBroken = toolItemStack.getMaxDamage() - toolItemStack.getDamageValue() <= 1;
        if (isBroken) return false;

        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (!(block instanceof BonemealableBlock bonemealableBlock)) return false;

        var itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int itemCount = itemStack.getCount();
        if (itemCount < 1) return false;

        if (bonemealableBlock.isValidBonemealTarget(level, pos, blockState, true))
        {
            if (!level.isClientSide())
            {
                bonemealableBlock.performBonemeal((ServerLevel) level, level.random, pos, blockState);

                if (bonemealableBlock.isBonemealSuccess(level, level.random, pos, blockState))
                {
                    damageItemIfSurvival(player, level, pos.below(), level.getBlockState(pos.below()));
                    itemStack.setCount(itemCount - 1);
                }
            }
        }

        return true;
    }
}
