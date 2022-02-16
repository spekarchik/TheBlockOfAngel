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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class FireRod extends MarineRod
{
    public FireRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
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

        if (!isBroken)
        {
            var hand = context.getHand();
            var facing = context.getClickedFace();

            if (facing == Direction.UP)
            {
                if (block == Blocks.SOUL_SAND)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.NETHER_WART);
                }

                var upPos = pos.above();
                var upBlock = level.getBlockState(upPos).getBlock();
                if (block == Blocks.OBSIDIAN && upBlock == Blocks.LAVA && level.getFluidState(upPos).getAmount() < FluidState.AMOUNT_FULL)
                {
                    level.setBlock(upPos, Blocks.LAVA.defaultBlockState(), 11);
                    updateNeighbors(level, upPos);
                    damageItemIfSurvival(player, level, pos, blockState);
                    new PlaySoundPacket(SoundType.LAVA_PLACED).sendToPlayer((ServerPlayer) player);
                    return InteractionResult.CONSUME;
                }
            }
            else
            {
                if (block == Blocks.END_STONE)
                {
                    setBlock(player, pos, Blocks.NETHERRACK);
                    damageItemIfSurvival(player, level, pos, blockState);
                    return InteractionResult.CONSUME;
                }
            }

            if (block == Blocks.WHITE_WOOL)
            {
                level.setBlock(pos, BlockRegistry.DESTROYING_BONE_MEAL.get().defaultBlockState(), 0);
                level.destroyBlock(pos, true, player, 1);
                damageItemIfSurvival(player, level, pos, blockState);
                return InteractionResult.CONSUME;
            }

            if (block == Blocks.MAGMA_BLOCK)
            {
                setBlock(player, pos, Blocks.GLOWSTONE);
                damageItemIfSurvival(player, level, pos, blockState);
                return InteractionResult.CONSUME;
            }

            if (block == Blocks.GLOWSTONE)
            {
                level.setBlock(pos, BlockRegistry.DESTROYING_BLAZE_POWDER.get().defaultBlockState(), 0);
                level.destroyBlock(pos, true, player, 1);
                damageItemIfSurvival(player, level, pos, blockState);
                return InteractionResult.CONSUME;
            }

            if (block == Blocks.BASALT)
            {
                setBlock(player, pos, Blocks.BLACKSTONE);
                damageItemIfSurvival(player, level, pos, blockState);
                return InteractionResult.CONSUME;
            }

            if (block == Blocks.WARPED_STEM || block == Blocks.CRIMSON_STEM)
            {
                setBlock(player, pos, Blocks.SHROOMLIGHT);
                damageItemIfSurvival(player, level, pos, blockState);
                return InteractionResult.CONSUME;
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
                                setBlock(player, pos, Blocks.CRIMSON_STEM);
                                damageItemIfSurvival(player, level, pos, blockState);
                                return InteractionResult.CONSUME;
                            }

                            if (block1 == Blocks.WARPED_STEM || block1 == Blocks.WARPED_NYLIUM || block1 == Blocks.WARPED_WART_BLOCK
                                    || block1 == Blocks.WARPED_HYPHAE)
                            {
                                setBlock(player, pos, Blocks.WARPED_STEM);
                                damageItemIfSurvival(player, level, pos, blockState);
                                return InteractionResult.CONSUME;
                            }
                        }
            }
        }

        return super.useOn(context);
    }

    @Override
    protected boolean canBeReplaced(Level level, BlockPos pos)
    {
        if (Utils.isNether(level.dimension()))
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
    protected int getShiftDepth()
    {
        return 9;
    }

    @Override
    protected int getAmethystDetectionDepth()
    {
        return 20;
    }

    @Override
    protected void oreFoundEvent(ServerPlayer player, boolean isOreFound, boolean isDiamondOreFound, boolean isAmethystFound)
    {
        var sound = isAmethystFound ? SoundType.AMETHYST_FOUND :
                (isDiamondOreFound ? SoundType.DIAMOND_FOUND : SoundType.ORE_FOUND);
        new PlaySoundPacket(sound).sendToPlayer(player);
    }
}
