package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Random;

public class MarineRod extends AncientRod
{
    public MarineRod(Tier material, boolean isMagnetic, Properties properties)
    {
        super(material, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level();

        if (isEnhanced() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT))
            return super.useOn(context);

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        var itemStack = player.getItemInHand(context.getHand());
        boolean isBroken = isBroken(itemStack);

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

                        utils.blocks.transformations.updateNeighbors(level, upPos);
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

        var result = super.useOn(context);
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
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        var block = state.getBlock();
        boolean isBroken = isBroken(stack);

        return super.isCorrectToolForDrops(stack, state) || (!isBroken &&
                (block == Blocks.MELON || block == Blocks.POWDER_SNOW || block == Blocks.GRAVEL || block == Blocks.CLAY
                || block == Blocks.WATER));
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
            for (int i = 0; i <= 12; i++)
            {
                tooltipComponents.add(getDescription(i, i == 1 || i == 8, false, false, i == 12));
            }
        }
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
