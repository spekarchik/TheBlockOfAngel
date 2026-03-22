package com.pekar.angelblock.tools;

import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.SoundType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AmethystRod extends FireRod
{
    public AmethystRod(ModToolMaterial material, boolean isMagnetic, Properties properties)
    {
        super(material, isMagnetic, properties);
    }

    @Override
    protected InteractionResult useOnInternal(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level();

        var result = super.useOnInternal(context);
        if (result != InteractionResult.PASS) return result;

        var itemStack = player.getItemInHand(context.getHand());

        boolean isClientSide = level.isClientSide();

        if (!hasCriticalDamage(itemStack) && player.getFoodData().getFoodLevel() > 0)
        {
            var pos = context.getClickedPos();
            var blockState = level.getBlockState(pos);
            var block = blockState.getBlock();

            var upPos = pos.above();
            var upBlock = level.getBlockState(upPos).getBlock();

            if (block == Blocks.OBSIDIAN && upBlock != Blocks.LAVA)
            {
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                setBlockWithClientSound(player, pos, Blocks.CRYING_OBSIDIAN);

                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.STONE || block == Blocks.GRANITE || block == Blocks.ANDESITE
                    || block == Blocks.DIORITE || block == Blocks.DRIPSTONE_BLOCK)
            {
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                return setOnBlockSide(context, this::setGlowLichen);
            }

            if (block == Blocks.DIAMOND_BLOCK)
            {
                setBlockWithClientSound(player, pos, Blocks.BUDDING_AMETHYST);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);

                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.BONE_BLOCK)
            {
                setBlockWithClientSound(player, pos, Blocks.CALCITE);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);

                return getToolInteractionResult(true, isClientSide);
            }
        }

        return InteractionResult.PASS;
    }

    private String getRodDescriptionId()
    {
        return formatDescriptionId(getRodId());
    }

    private String getRodId()
    {
        return ToolRegistry.AMETHYST_ROD.getRegisteredName();
    }

    @Override
    protected void appendDestroyingBlockInfo(ITooltip tooltip, boolean selectAsNew)
    {
        super.appendDestroyingBlockInfo(tooltip, false);
    }

    @Override
    protected void appendPlacingBlockInfo(ITooltip tooltip, boolean selectAsNew)
    {
        super.appendPlacingBlockInfo(tooltip, false);

        for (int i = 2; i <= 2; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).withFormatting(ChatFormatting.WHITE, selectAsNew).apply();
        }
    }

    @Override
    protected void appendBlockTransformInfo(ITooltip tooltip, boolean selectAsNew)
    {
        super.appendBlockTransformInfo(tooltip, false);

        for (int i = 9; i <= 11; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).withFormatting(ChatFormatting.WHITE, selectAsNew).apply();
        }
    }

    @Override
    protected void appendMagneticInfo(ITooltip tooltip)
    {
        for (int i = 13; i <= 21; i++)
        {
            if (i == 21) tooltip.addEmptyLine();
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.Header, i == 13).styledAs(TextStyle.DarkGray, i == 21).apply();
        }
    }

    protected void appendMainPostInfo(ITooltip tooltip)
    {
        for (int i = 22; i <= 24; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.DarkGray, i <= 23).apply();
        }
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
        else if (detectorFlags.isRailsFound())
        {
            soundType = SoundType.RAILS_FOUND;
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

    @Override
    protected int getOreDepth()
    {
        return 12;
    }

    @Override
    protected int getSculkDetectionDepth()
    {
        return 128;
    }

    private InteractionResult setGlowLichen(BlockPlaceContext context, BlockPos pos)
    {
        var level = context.getLevel();
        BlockState state = Blocks.GLOW_LICHEN.getStateForPlacement(context);

        if (!level.isEmptyBlock(pos)) return InteractionResult.FAIL;

        var isClientSide = context.getLevel().isClientSide();
        if (!isClientSide && state != null)
        {
            level.setBlock(pos, state, Block.UPDATE_ALL_IMMEDIATE);
        }

        utils.sound.playSoundByBlock(context.getPlayer(), pos, SoundType.BLOCK_CHANGED);

        return getToolInteractionResult(true, isClientSide);
    }
}
