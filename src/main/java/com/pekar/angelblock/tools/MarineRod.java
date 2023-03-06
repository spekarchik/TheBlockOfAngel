package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class MarineRod extends AncientRod
{
    public MarineRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;

//        if (level.isClientSide) return InteractionResult.PASS;
//        if (!canUseToolEffect(player)) return InteractionResult.PASS;
        if (isEnhancedRod() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT.get()))
            return super.useOn(context);

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        var itemStack = player.getItemInHand(context.getHand());
        boolean isBroken = itemStack.getMaxDamage() - itemStack.getDamageValue() <= 1;

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
                        && ((isFarmTypeBlock(level, upPos.north()) && isFarmTypeBlock(level, upPos.south()))
                        || (isFarmTypeBlock(level, upPos.east()) && isFarmTypeBlock(level, upPos.west())))))
                {
                    if (!isClientSide)
                    {
                        level.setBlock(upPos, Blocks.WATER.defaultBlockState(), 11);
                        new PlaySoundPacket(SoundType.WATER_PLACED).sendToPlayer((ServerPlayer) player);
                        damageItemIfSurvival(player, level, pos, blockState); // pos, not upPos

                        updateNeighbors(level, upPos);
                    }

                    return InteractionResult.sidedSuccess(isClientSide);
                }
            }

            if (block == Blocks.MELON)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, Blocks.SLIME_BLOCK);
                    damageItemIfSurvival(player, level, pos, blockState);
                }

                return InteractionResult.sidedSuccess(isClientSide);
            }

            if (block == Blocks.POWDER_SNOW)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, Blocks.SNOW_BLOCK);
                    damageItemIfSurvival(player, level, pos, blockState);
                }

                return InteractionResult.sidedSuccess(isClientSide);
            }
        }

        var result = super.useOn(context);
        if (result != InteractionResult.PASS) return result;

        if (!isBroken && facing == Direction.UP)
        {
            if (block == Blocks.PODZOL || block == Blocks.MYCELIUM)
            {
                damageItemIfSurvival(player, level, pos, blockState);
                int randomValue = itemRand.nextInt() & 1;
                var plantBlock = randomValue == 0 ? Blocks.BROWN_MUSHROOM : Blocks.RED_MUSHROOM;
                return plant(player, level, pos, hand, facing, plantBlock);
            }

            if (block == Blocks.RED_SAND || block == Blocks.GRAVEL)
            {
                damageItemIfSurvival(player, level, pos, blockState);
                return plant(player, level, pos, hand, facing, Blocks.BAMBOO);
            }

            if (block == Blocks.CLAY && Utils.isNearWaterHorizontal(level, pos))
            {
                damageItemIfSurvival(player, level, pos, blockState);
                return plant(player, level, pos, hand, facing, Blocks.SMALL_DRIPLEAF);
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        if (!isEnhancedRod()) return;
        for (int i = 1; i <= 5; i++)
            components.add(getDisplayName(i).withStyle(ChatFormatting.GRAY));
    }

    @Override
    protected int getShiftDepth()
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
