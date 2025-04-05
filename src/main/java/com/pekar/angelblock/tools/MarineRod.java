package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.text.ITooltip;
import com.pekar.angelblock.text.TextStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class MarineRod extends AncientRod
{
    public MarineRod(ModToolMaterial material, boolean isMagnetic, Properties properties)
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

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        var itemStack = player.getItemInHand(context.getHand());
        boolean isBroken = hasCriticalDamage(itemStack);

        var hand = context.getHand();
        var facing = context.getClickedFace();
        var itemRand = new Random();

        if (!isBroken)
        {
            boolean isClientSide = level.isClientSide();

            if (facing == Direction.UP)
            {
                BlockPos upPos = pos.above();

                if (level.isWaterAt(upPos) || ((level.isEmptyBlock(upPos))
                        && ((utils.blocks.types.isFarmTypeBlock(level, upPos.north()) && utils.blocks.types.isFarmTypeBlock(level, upPos.south()))
                        || (utils.blocks.types.isFarmTypeBlock(level, upPos.east()) && utils.blocks.types.isFarmTypeBlock(level, upPos.west())))))
                {
                    if (!isClientSide)
                    {
                        level.setBlock(upPos, Blocks.WATER.defaultBlockState(), 11);
                        new PlaySoundPacket(SoundType.WATER_PLACED).sendToPlayer((ServerPlayer) player);
                        damageMainHandItemIfSurvivalIgnoreClient(player, level); // pos, not upPos
                    }

                    return getToolInteractionResult(true, isClientSide);
                }
            }

            if (block == Blocks.MELON)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, Blocks.SLIME_BLOCK);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }

                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.POWDER_SNOW)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, Blocks.SNOW_BLOCK);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }

                return getToolInteractionResult(true, isClientSide);
            }
        }

        var result = super.useOnInternal(context);
        if (result != InteractionResult.PASS) return result;

        if (!isBroken && facing == Direction.UP)
        {
            if (block == Blocks.PODZOL || block == Blocks.MYCELIUM)
            {
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                int randomValue = itemRand.nextInt() & 1;
                var plantBlock = randomValue == 0 ? Blocks.BROWN_MUSHROOM : Blocks.RED_MUSHROOM;
                return plant(player, level, pos, hand, facing, plantBlock);
            }

            if (block == Blocks.RED_SAND || block == Blocks.GRAVEL)
            {
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                return plant(player, level, pos, hand, facing, Blocks.BAMBOO);
            }

            if (block == Blocks.CLAY && utils.blocks.conditions.isNearWaterHorizontal(level, pos))
            {
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                return plant(player, level, pos, hand, facing, Blocks.SMALL_DRIPLEAF);
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void appendPlacingBlockInfo(ITooltip tooltip, boolean selectAsNew)
    {
        super.appendPlacingBlockInfo(tooltip, false);

        for (int i = 2; i <= 5; i++)
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

    protected void appendCommonPostInfo(ITooltip tooltip)
    {
        for (int i = 22; i <= 23; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.DarkGray, i == 22).apply();
        }
    }

    private String getRodId()
    {
        return ToolRegistry.MARINE_ROD.getRegisteredName();
    }

    @Override
    protected String getRodDescriptionId()
    {
        return getRodDescriptionId(getRodId());
    }

    @Override
    protected int getOreDepth()
    {
        return 7;
    }

    @Override
    protected void oreFoundEvent(ServerPlayer player, DetectorFlags detectorFlags)
    {
        if (detectorFlags.isDiamondOreFound())
            new PlaySoundPacket(SoundType.DIAMOND_FOUND).sendToPlayer(player);
        else if (detectorFlags.isAmethystFound())
            new PlaySoundPacket(SoundType.AMETHYST_FOUND).sendToPlayer(player);
        else if (detectorFlags.isShiftingOreFound())
            new PlaySoundPacket(SoundType.ORE_FOUND).sendToPlayer(player);
    }
}
