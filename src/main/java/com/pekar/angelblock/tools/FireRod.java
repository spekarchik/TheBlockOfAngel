package com.pekar.angelblock.tools;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.SoundType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FireRod extends MarineRod
{
    public FireRod(ModToolMaterial material, boolean isMagnetic, Properties properties)
    {
        super(material, isMagnetic, properties);
    }

    @Override
    protected void additionalActionOnMineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity entity)
    {
        super.additionalActionOnMineBlock(itemStack, level, blockState, pos, entity);
        if (blockState.getBlock() == Blocks.GLOWSTONE)
        {
            if (!level.isClientSide() && entity instanceof Player player && !hasCriticalDamage(itemStack) && player.getFoodData().getFoodLevel() > 0)
            {
                level.setBlock(pos, BlockRegistry.DESTROYING_BLAZE_POWDER.get().defaultBlockState(), 0);
                level.destroyBlock(pos, true, entity, 1);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                causeMinePlayerExhaustion(player);
            }
        }
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
        boolean isBrokenOrPlayerExhausted = hasCriticalDamage(itemStack) || player.getFoodData().getFoodLevel() <= 0;

        boolean isClientSide = level.isClientSide();

        var pos = context.getClickedPos();
        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (!isBrokenOrPlayerExhausted)
        {
            var facing = context.getClickedFace();

            if (facing == Direction.UP)
            {
                if (block == Blocks.SOUL_SAND && level.isEmptyBlock(pos.above()))
                {
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    return plant(player, level, pos, interactionHand, facing, Blocks.NETHER_WART);
                }
            }
            else
            {
                if (block == Blocks.END_STONE)
                {
                    setBlockWithClientSound(player, pos, Blocks.NETHERRACK);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    return getToolInteractionResult(true, isClientSide);
                }
            }
        }

        var result = super.useOnInternal(context);
        if (result != InteractionResult.PASS) return result;

        if (!isBrokenOrPlayerExhausted)
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
                setBlockWithClientSound(player, pos, Blocks.GLOWSTONE);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.BASALT)
            {
                setBlockWithClientSound(player, pos, Blocks.BLACKSTONE);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.WARPED_STEM || block == Blocks.CRIMSON_STEM)
            {
                setBlockWithClientSound(player, pos, Blocks.SHROOMLIGHT);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.CLAY) // it's important to check CLAY near WATER (MarineRod) earlier
            {
                setBlockWithClientSound(player, pos, Blocks.TERRACOTTA);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                return getToolInteractionResult(true, isClientSide);
            }

//            if (block == Blocks.SHROOMLIGHT)
//            {
//                for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++)
//                    for (int y = pos.getY() - 1; y <= pos.getY() + 1; y++)
//                        for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++)
//                        {
//                            if (x == pos.getX() && y == pos.getY() && z == pos.getZ()) continue;
//                            var block1 = level.getBlockState(new BlockPos(x, y, z)).getBlock();
//
//                            if (block1 == Blocks.CRIMSON_STEM || block1 == Blocks.CRIMSON_NYLIUM || block1 == Blocks.NETHER_WART_BLOCK
//                                    || block1 == Blocks.NETHER_WART || block1 == Blocks.CRIMSON_HYPHAE)
//                            {
//                                if (!isClientSide)
//                                {
//                                    setBlock(player, pos, Blocks.CRIMSON_STEM);
//                                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
//                                }
//                                return getToolInteractionResult(true, isClientSide);
//                            }
//
//                            if (block1 == Blocks.WARPED_STEM || block1 == Blocks.WARPED_NYLIUM || block1 == Blocks.WARPED_WART_BLOCK
//                                    || block1 == Blocks.WARPED_HYPHAE)
//                            {
//                                if (!isClientSide)
//                                {
//                                    setBlock(player, pos, Blocks.WARPED_STEM);
//                                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
//                                }
//                                return getToolInteractionResult(true, isClientSide);
//                            }
//                        }
//            }
        }

        return result;
    }

    private String getRodDescriptionId()
    {
        return formatDescriptionId(getRodId());
    }

    private String getRodId()
    {
        return ToolRegistry.FIRE_ROD.getRegisteredName();
    }

    @Override
    protected void appendDestroyingBlockInfo(ITooltip tooltip, boolean selectAsNew)
    {
        tooltip.includeEmptyLines();
        super.appendDestroyingBlockInfo(tooltip, false);

        tooltip.addLine(getRodDescriptionId(), 1).withFormatting(ChatFormatting.WHITE, selectAsNew).apply();
    }

    @Override
    protected void appendPlacingBlockInfo(ITooltip tooltip, boolean selectAsNew)
    {
        tooltip.includeEmptyLines();
        super.appendPlacingBlockInfo(tooltip, false);

        tooltip.addLine(getRodDescriptionId(), 5).withFormatting(ChatFormatting.WHITE, selectAsNew).apply();
    }

    @Override
    protected void appendBlockTransformInfo(ITooltip tooltip, boolean selectAsNew)
    {
        super.appendBlockTransformInfo(tooltip, false);

        tooltip.ignoreEmptyLines();

        for (int i = 7; i <= 12; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).withFormatting(ChatFormatting.WHITE, selectAsNew).apply();
        }
    }

    @Override
    protected void appendMagneticInfo(ITooltip tooltip)
    {
        tooltip.includeEmptyLines();

        for (int i = 14; i <= 22; i++)
        {
            if (i == 22) tooltip.addEmptyLine();
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.Header, i == 14).styledAs(TextStyle.DarkGray, i == 22).apply();
        }
    }

    protected void appendCommonPostInfo(ITooltip tooltip)
    {
        tooltip.includeEmptyLines();

        for (int i = 23; i <= 24; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.DarkGray, i == 23).apply();
        }
    }

    @Override
    protected boolean canBeReplaced(Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var replaceables = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Main.MODID, "nether_replaceables"));
        return blockState.is(replaceables) || super.canBeReplaced(level, pos);
    }

    @Override
    protected boolean isShiftingOre(Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var netherShiftingOres = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Main.MODID, "nether_shifting_ores"));
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
    protected void oreFoundEvent(ServerPlayer player, BlockPos pos, DetectorFlags detectorFlags)
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

        utils.sound.playSoundOnBothSides(player, pos, soundType, SoundSource.BLOCKS, 5F);
    }
}
