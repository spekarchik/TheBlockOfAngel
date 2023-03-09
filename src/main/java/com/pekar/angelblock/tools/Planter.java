package com.pekar.angelblock.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
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

        var pos = context.getClickedPos();
        return processBlocks(player, level, pos, context.getClickedFace())
                ? InteractionResult.sidedSuccess(level.isClientSide)
                : InteractionResult.PASS;

    }

    protected boolean processBlocks(Player player, Level level, BlockPos pos, Direction facing)
    {
        if (facing != Direction.UP) return false;

        var toolHand = InteractionHand.MAIN_HAND;
        var seedHand = InteractionHand.OFF_HAND;

        var seedInHand = player.getItemInHand(seedHand);
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
                shiftX = seedCount; shiftZ = -1; increment = -1; break;
        }

        boolean haveAnyTransformed = false;
        var toolItemStack = player.getItemInHand(toolHand);
        var originBlock = level.getBlockState(pos).getBlock();

        for (int x = posX; x != posX + shiftX; x += increment)
            for (int z = posZ; z != posZ + shiftZ; z += increment)
            {
                boolean hasTransformed = onBlockProcessing(player, level, originBlock, new BlockPos(x, posY, z), facing, toolItemStack, plantableBlock);
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

    private boolean onBlockProcessing(Player player, Level level, Block originBlock, BlockPos pos, Direction facing, ItemStack toolItemStack, Block plantBlock)
    {
        boolean isBroken = toolItemStack.getMaxDamage() - toolItemStack.getDamageValue() <= 1;
        if (isBroken) return false;

        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (block != originBlock) return false;
//        if (plantBlock != Blocks.NETHER_WART && block != Blocks.FARMLAND) return false;
//        if (plantBlock == Blocks.NETHER_WART && block != Blocks.SOUL_SAND) return false;

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
}
