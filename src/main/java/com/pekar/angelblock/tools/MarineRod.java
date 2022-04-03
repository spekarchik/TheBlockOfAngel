package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;

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

        if (level.isClientSide) return InteractionResult.PASS;
        if (!canUseToolEffect(player)) return InteractionResult.PASS;

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
            if (facing == Direction.UP)
            {
                BlockPos upPos = pos.above();

                if (level.isWaterAt(upPos) || ((level.isEmptyBlock(upPos))
                        && ((isFarmTypeBlock(level, upPos.north()) && isFarmTypeBlock(level, upPos.south()))
                        || (isFarmTypeBlock(level, upPos.east()) && isFarmTypeBlock(level, upPos.west())))))
                {
                    level.setBlock(upPos, Blocks.WATER.defaultBlockState(), 11);
                    new PlaySoundPacket(SoundType.WATER_PLACED).sendToPlayer((ServerPlayer) player);
                    damageItemIfSurvival(player, level, pos, blockState); // pos, not upPos

                    updateNeighbors(level, upPos);

                    return InteractionResult.CONSUME;
                }
            }

            if (block == Blocks.MELON)
            {
                setBlock(player, pos, Blocks.SLIME_BLOCK);
                damageItemIfSurvival(player, level, pos, blockState);
                return InteractionResult.CONSUME;
            }

            if (block == Blocks.POWDER_SNOW)
            {
                setBlock(player, pos, Blocks.SNOW_BLOCK);
                damageItemIfSurvival(player, level, pos, blockState);
                return InteractionResult.CONSUME;
            }

            if (block == Blocks.PRISMARINE)
            {
                level.setBlock(pos, BlockRegistry.DESTROYING_PRISMARINE_CRYSTALS.get().defaultBlockState(), 0);
                level.destroyBlock(pos, true, player, 1);
                damageItemIfSurvival(player, level, pos, blockState);
                return InteractionResult.CONSUME;
            }
        }

        var result = super.useOn(context);
        if (result.shouldAwardStats()) return result;

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

            if (Utils.isNearWaterHorizoltal(level, pos) && block == Blocks.CLAY)
            {
                damageItemIfSurvival(player, level, pos, blockState);
                return plant(player, level, pos, hand, facing, Blocks.SMALL_DRIPLEAF);
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected int getShiftDepth()
    {
        return 7;
    }

    @Override
    protected void oreFoundEvent(ServerPlayer player, boolean isOreFound, boolean isDiamondOreFound, boolean isAmethystFound)
    {
        if (isAmethystFound)
            new PlaySoundPacket(SoundType.AMETHYST_FOUND).sendToPlayer(player);
        else if (isOreFound)
            new PlaySoundPacket(SoundType.ORE_FOUND).sendToPlayer(player);
    }
}
