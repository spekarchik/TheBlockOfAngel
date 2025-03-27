package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class Planter extends WorkRod
{
    public Planter(Tier material, Properties properties)
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

        if (offHandItem == Items.BONE_MEAL)
        {
            success = bonemealPlants(player, level, pos, context.getClickedFace());
        }
        else if (offHandItem instanceof BlockItem)
        {
            success = plantOffHandItems(player, level, pos, context.getClickedFace());
        }
        else if (offHandItem instanceof ShearsItem)
        {
            success = grabPlants(player, level, pos, 5, false);
            if (success)
            {
                damageOffHandItemIfSurvivalIgnoreClient(player, level);
            }
        }

        var result = getToolInteractionResult(success, level.isClientSide());

        if (result == InteractionResult.CONSUME || result == InteractionResult.CONSUME_PARTIAL)
        {
            causePlayerExhaustion(player);
        }

        return result;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity)
    {
        if (!level.isClientSide() && blockState.getBlock() instanceof BushBlock && livingEntity instanceof Player player)
        {
            grabPlants(player, level, pos, 3, true); // no tool damage
        }

        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return isPlanterCompatible(state.getBlock());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 0; i <= 9; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1 || i == 3,  false, i == 6, i == 5, i == 8));
        }
    }

    protected boolean grabPlants(Player player, Level level, BlockPos pos, int grabWidth, boolean shouldDrop)
    {
        final int GRAB_LENGTH = 5;
        final int GRAB_HALF_WIDTH = grabWidth / 2;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int x1 = 0, x2 = 0, z1 = 0, z2 = 0, incX = 1, incZ = 1;
        switch (player.getDirection())
        {
            case NORTH:
                x1 = GRAB_HALF_WIDTH; x2 = GRAB_HALF_WIDTH + 1; z1 = 0; z2 = -GRAB_LENGTH; incZ = -1; break;

            case SOUTH:
                x1 = GRAB_HALF_WIDTH; x2 = GRAB_HALF_WIDTH + 1; z1 = 0; z2 = GRAB_LENGTH; break;

            case EAST:
                x1 = 0; x2 = GRAB_LENGTH; z1 = GRAB_HALF_WIDTH; z2 = GRAB_HALF_WIDTH + 1; break;


            case WEST: // towards negative X
                x1 = 0; x2 = -GRAB_LENGTH; incX = -1; z1 = GRAB_HALF_WIDTH; z2 = GRAB_HALF_WIDTH + 1; break;

        }

        boolean haveAnyTransformed = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var originBlock = level.getBlockState(pos).getBlock();

        int y = originBlock instanceof BushBlock ? posY : pos.above().getY();
        for (int x = posX - x1; x != posX + x2; x += incX)
            for (int z = posZ - z1; z != posZ + z2; z += incZ)
            {
                boolean hasTransformed = grabPlant(player, level, originBlock, new BlockPos(x, y, z), toolItemStack, shouldDrop);
                if (hasTransformed)
                    haveAnyTransformed = true;
            }

        return haveAnyTransformed;
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

    private boolean grabPlant(Player player, Level level, Block originBlock, BlockPos pos, ItemStack toolItemStack, boolean shouldDrop)
    {
        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (!(block instanceof BushBlock)) return false;

        if (!level.isClientSide())
        {
            level.destroyBlock(pos, shouldDrop);
        }

        return true;
    }

    private boolean plantOffHandItem(Player player, Level level, Block originBlock, BlockPos pos, Direction facing, ItemStack toolItemStack, Block plantBlock)
    {
        if (hasCriticalDamage(toolItemStack)) return false;

        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (block != originBlock) return false;

        var itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int itemCount = itemStack.getCount();
        if (itemCount < 1) return false;

        var result = plant(player, level, pos, InteractionHand.OFF_HAND, facing, plantBlock);
        if (result.consumesAction())
        {
            damageMainHandItemIfSurvivalIgnoreClient(player, level);
            itemStack.setCount(itemCount - 1);
        }

        return result.consumesAction();
    }

    private boolean bonemealPlant(Player player, Level level, Block originBlock, BlockPos pos, Direction facing, ItemStack toolItemStack)
    {
        if (hasCriticalDamage(toolItemStack)) return false;

        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (!(block instanceof BonemealableBlock bonemealableBlock)) return false;

        var itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int itemCount = itemStack.getCount();
        if (itemCount < 1) return false;

        if (bonemealableBlock.isValidBonemealTarget(level, pos, blockState))
        {
            if (!level.isClientSide())
            {
                bonemealableBlock.performBonemeal((ServerLevel) level, level.random, pos, blockState);

                if (bonemealableBlock.isBonemealSuccess(level, level.random, pos, blockState))
                {
                    if (player instanceof ServerPlayer serverPlayer)
                    {
                        new PlaySoundPacket(SoundType.BONEMEAL).sendToPlayer(serverPlayer);
                    }

                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    itemStack.setCount(itemCount - 1);
                }
            }
        }

        return true;
    }
}
