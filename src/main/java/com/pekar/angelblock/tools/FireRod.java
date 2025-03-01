package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.List;

public class FireRod extends MarineRod
{
    public FireRod(Tier material, boolean isMagnetic, Properties properties)
    {
        super(material, isMagnetic, properties);
    }

    @Override
    protected InteractionResult useOnInternal(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level();

        if (isEnhanced() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT))
            return super.useOnInternal(context);

        var interactionHand = context.getHand();
        var itemStack = player.getItemInHand(interactionHand);
        boolean isBroken = isBroken(itemStack);

        boolean isClientSide = level.isClientSide();

        var pos = context.getClickedPos();
        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (!isBroken)
        {
            var facing = context.getClickedFace();

            if (facing == Direction.UP)
            {
                if (block == Blocks.SOUL_SAND)
                {
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    return plant(player, level, pos, interactionHand, facing, Blocks.NETHER_WART);
                }

                var upPos = pos.above();
                var upBlock = level.getBlockState(upPos).getBlock();
                if (block == Blocks.OBSIDIAN && upBlock == Blocks.LAVA && level.getFluidState(upPos).getAmount() < FluidState.AMOUNT_FULL)
                {
                    if (!isClientSide)
                    {
                        level.setBlock(upPos, Blocks.LAVA.defaultBlockState(), 11);
                        utils.blocks.transformations.updateNeighbors(level, upPos);
                        damageMainHandItemIfSurvivalIgnoreClient(player, level);
                        new PlaySoundPacket(SoundType.LAVA_PLACED).sendToPlayer((ServerPlayer) player);
                    }

                    return getToolInteractionResult(true, isClientSide);
                }
            }
            else
            {
                if (block == Blocks.END_STONE)
                {
                    if (!isClientSide)
                    {
                        setBlock(player, pos, Blocks.NETHERRACK);
                        damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    }

                    return getToolInteractionResult(true, isClientSide);
                }
            }
        }

        var result = super.useOnInternal(context);
        if (result != InteractionResult.PASS) return result;

        if (!isBroken)
        {
            Block woolBlock = null;

            if (!isClientSide)
            {
                woolBlock = getDestroyingWoolBlock(block);

                if (woolBlock != null)
                {
                    level.setBlock(pos, woolBlock.defaultBlockState(), 0);
                    level.destroyBlock(pos, true, player, 1);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }
            }

            if (woolBlock != null)
            {
                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.MAGMA_BLOCK)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, Blocks.GLOWSTONE);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }

                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.GLOWSTONE)
            {
                if (!isClientSide)
                {
                    level.setBlock(pos, BlockRegistry.DESTROYING_BLAZE_POWDER.get().defaultBlockState(), 0);
                    level.destroyBlock(pos, true, player, 1);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }

                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.BASALT)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, Blocks.BLACKSTONE);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }
                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.WARPED_STEM || block == Blocks.CRIMSON_STEM)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, Blocks.SHROOMLIGHT);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }

                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.CLAY) // it's important to check CLAY near WATER (MarineRod) earlier
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, Blocks.TERRACOTTA);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }

                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.SHROOMLIGHT)
            {
                for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++)
                    for (int y = pos.getY() - 1; y <= pos.getY() + 1; y++)
                        for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++)
                        {
                            if (x == pos.getX() && y == pos.getY() && z == pos.getZ()) continue;
                            var block1 = level.getBlockState(new BlockPos(x, y, z)).getBlock();

                            if (block1 == Blocks.CRIMSON_STEM || block1 == Blocks.CRIMSON_NYLIUM || block1 == Blocks.NETHER_WART_BLOCK
                                    || block1 == Blocks.NETHER_WART || block1 == Blocks.CRIMSON_HYPHAE)
                            {
                                if (!isClientSide)
                                {
                                    setBlock(player, pos, Blocks.CRIMSON_STEM);
                                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                                }
                                return getToolInteractionResult(true, isClientSide);
                            }

                            if (block1 == Blocks.WARPED_STEM || block1 == Blocks.WARPED_NYLIUM || block1 == Blocks.WARPED_WART_BLOCK
                                    || block1 == Blocks.WARPED_HYPHAE)
                            {
                                if (!isClientSide)
                                {
                                    setBlock(player, pos, Blocks.WARPED_STEM);
                                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                                }
                                return getToolInteractionResult(true, isClientSide);
                            }
                        }
            }
        }

        return result;
    }

    private Block getDestroyingWoolBlock(Block block)
    {
        if (block == Blocks.WHITE_WOOL)
        {
            return BlockRegistry.DESTROYING_WHITE_WOOL_BY_ROD.get();
        }
        else if (block == Blocks.ORANGE_WOOL)
        {
            return BlockRegistry.DESTROYING_ORANGE_WOOL.get();
        }
        else if (block == Blocks.MAGENTA_WOOL)
        {
            return BlockRegistry.DESTROYING_MAGENTA_WOOL.get();
        }
        else if (block == Blocks.LIGHT_BLUE_WOOL)
        {
            return BlockRegistry.DESTROYING_LIGHT_BLUE_WOOL.get();
        }
        else if (block == Blocks.YELLOW_WOOL)
        {
            return BlockRegistry.DESTROYING_YELLOW_WOOL.get();
        }
        else if (block == Blocks.LIME_WOOL)
        {
            return BlockRegistry.DESTROYING_LIME_WOOL.get();
        }
        else if (block == Blocks.PINK_WOOL)
        {
            return BlockRegistry.DESTROYING_PINK_WOOL.get();
        }
        else if (block == Blocks.GRAY_WOOL)
        {
            return BlockRegistry.DESTROYING_GRAY_WOOL.get();
        }
        else if (block == Blocks.LIGHT_GRAY_WOOL)
        {
            return BlockRegistry.DESTROYING_LIGHT_GRAY_WOOL.get();
        }
        else if (block == Blocks.CYAN_WOOL)
        {
            return BlockRegistry.DESTROYING_CYAN_WOOL.get();
        }
        else if (block == Blocks.PURPLE_WOOL)
        {
            return BlockRegistry.DESTROYING_PURPLE_WOOL.get();
        }
        else if (block == Blocks.BLUE_WOOL)
        {
            return BlockRegistry.DESTROYING_BLUE_WOOL.get();
        }
        else if (block == Blocks.BROWN_WOOL)
        {
            return BlockRegistry.DESTROYING_BROWN_WOOL.get();
        }
        else if (block == Blocks.GREEN_WOOL)
        {
            return BlockRegistry.DESTROYING_GREEN_WOOL.get();
        }
        else if (block == Blocks.RED_WOOL)
        {
            return BlockRegistry.DESTROYING_RED_WOOL.get();
        }
        else if (block == Blocks.BLACK_WOOL)
        {
            return BlockRegistry.DESTROYING_BLACK_WOOL.get();
        }
        
        return null;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        var block = state.getBlock();

        return super.isCorrectToolForDrops(stack, state)
                || (!isBroken(stack) && (block == Blocks.SOUL_SAND || block == Blocks.LAVA || block == Blocks.END_STONE
                || getDestroyingWoolBlock(block) != null || block == Blocks.MAGMA_BLOCK || block == Blocks.GLOWSTONE
                || block == Blocks.BASALT || block == Blocks.WARPED_STEM || block == Blocks.CRIMSON_STEM || block == Blocks.SHROOMLIGHT));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        if (isEnhanced())
        {
            for (int i = 0; i <= 8; i++)
            {
                tooltipComponents.add(getDescription(i, false, false, false, i == 0));
            }
        }
        else
        {
            for (int i = 0; i <= 13; i++)
            {
                tooltipComponents.add(getDescription(i, i == 1 || i == 6, false, false, i == 13));
            }
        }
    }

    @Override
    protected boolean canBeReplaced(Level level, BlockPos pos)
    {
        if (utils.dimension.isNether(level.dimension()))
        {
            var block = level.getBlockState(pos).getBlock();
            return block == Blocks.NETHERRACK || block == Blocks.BASALT || block == Blocks.BLACKSTONE || block == Blocks.MAGMA_BLOCK;
        }

        return super.canBeReplaced(level, pos);
    }

    @Override
    protected boolean isShiftingOre(Block block)
    {
        return block == Blocks.ANCIENT_DEBRIS || super.isShiftingOre(block);
    }

    @Override
    protected int getShiftingRadius()
    {
        return 2;
    }

    @Override
    protected int getOreDepth()
    {
        return 9;
    }

    @Override
    protected int getAmethystDetectionDepth()
    {
        return 40;
    }

    @Override
    protected void oreFoundEvent(ServerPlayer player, DetectorFlags detectorFlags)
    {
        SoundType soundType;

        if (detectorFlags.isSculkVeinFound())
        {
            soundType = SoundType.SCULK_FOUND;
        }
        else if (detectorFlags.isDiamondOreFound())
        {
            soundType = SoundType.DIAMOND_FOUND;
        }
        else if (detectorFlags.isAmethystFound())
        {
            soundType = SoundType.AMETHYST_FOUND;
        }
        else if (detectorFlags.isShiftingOreFound())
        {
            soundType = SoundType.ORE_FOUND;
        }
        else
        {
            return;
        }

        new PlaySoundPacket(soundType).sendToPlayer(player);
    }
}
