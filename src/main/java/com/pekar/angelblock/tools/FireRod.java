package com.pekar.angelblock.tools;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

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

//                var upPos = pos.above();
//                var upBlock = level.getBlockState(upPos).getBlock();
//                if (block == Blocks.OBSIDIAN && upBlock == Blocks.LAVA && level.getFluidState(upPos).getAmount() < FluidState.AMOUNT_FULL)
//                {
//                    if (!isClientSide)
//                    {
//                        level.setBlock(upPos, Blocks.LAVA.defaultBlockState(), 11);
//                        damageMainHandItemIfSurvivalIgnoreClient(player, level);
//                        new PlaySoundPacket(SoundType.LAVA_PLACED).sendToPlayer((ServerPlayer) player);
//                    }
//
//                    return getToolInteractionResult(true, isClientSide);
//                }
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
//            Block woolBlock = null;
//
//            if (!isClientSide)
//            {
//                woolBlock = utils.blocks.types.getDestroyingWoolBlock(block);
//
//                if (woolBlock != null)
//                {
//                    level.setBlock(pos, woolBlock.defaultBlockState(), 0);
//                    level.destroyBlock(pos, true, player, 1);
//                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
//                }
//            }
//
//            if (woolBlock != null)
//            {
//                return getToolInteractionResult(true, isClientSide);
//            }

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

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        var block = state.getBlock();

        return super.isCorrectToolForDrops(stack, state)
                || (!isBroken(stack) && (block == Blocks.SOUL_SAND || block == Blocks.LAVA || block == Blocks.END_STONE
                || utils.blocks.types.getDestroyingWoolBlock(block) != null || block == Blocks.MAGMA_BLOCK || block == Blocks.GLOWSTONE
                || block == Blocks.BASALT || block == Blocks.WARPED_STEM || block == Blocks.CRIMSON_STEM || block == Blocks.SHROOMLIGHT));
    }

    private String getRodId()
    {
        return ToolRegistry.FIRE_ROD.getRegisteredName();
    }

    @Override
    protected void appendPlacingBlockInfo(List<Component> tooltipComponents, boolean selectAsNew)
    {
        super.appendPlacingBlockInfo(tooltipComponents, false);

        for (int i = 2; i <= 3; i++)
        {
            tooltipComponents.add(getDescription(getRodId(), i,false, false, false, false, selectAsNew, false));
        }
    }

    @Override
    protected void appendBlockTransformInfo(List<Component> tooltipComponents, boolean selectAsNew)
    {
        super.appendBlockTransformInfo(tooltipComponents, false);

        for (int i = 7; i <= 12; i++)
        {
            var component = getDescription(getRodId(), i,false, false, false, false, selectAsNew, false);
            if (!component.getString().isEmpty())
                tooltipComponents.add(component);
        }
    }

    @Override
    protected void appendMagneticInfo(List<Component> tooltipComponents)
    {
        for (int i = 14; i <= 22; i++)
        {
            if (i == 22) tooltipComponents.add(Component.empty());
            tooltipComponents.add(getDescription(getRodId(), i, i == 14, false, false, false, false, i == 22));
        }
    }

    protected void appendCommonPostInfo(List<Component> tooltipComponents)
    {
        for (int i = 23; i <= 24; i++)
        {
            tooltipComponents.add(getDescription(getRodId(), i, false, false, false, false, false, i == 23));
        }
    }

    @Override
    protected boolean canBeReplaced(Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var replaceables = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Main.MODID, "nether_replaceables"));
        return blockState.is(replaceables) || super.canBeReplaced(level, pos);
    }

    @Override
    protected boolean isShiftingOre(Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var netherShiftingOres = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Main.MODID, "nether_shifting_ores"));
        return blockState.is(netherShiftingOres) || super.isShiftingOre(level, pos);
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
