package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.ServerLevelData;

import java.util.ArrayList;

public class EndRod extends AmethystRod
{
    public EndRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();

        if (isEnhancedRod() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT.get()))
            return super.useOn(context);

        var itemStack = player.getItemInHand(context.getHand());

        if (!isBroken(itemStack))
        {
            var pos = context.getClickedPos();
            var level = player.level;
            var blockState = level.getBlockState(pos);
            var block = blockState.getBlock();

            var hand = context.getHand();
            var facing = context.getClickedFace();

            boolean isClientSide = level.isClientSide();

            if (facing == Direction.UP)
            {
                if (block == Blocks.END_STONE)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.CHORUS_FLOWER);
                }
            }

            if (block == Blocks.TUFF)
            {
                if (!isClientSide)
                {
                    level.setBlock(pos, BlockRegistry.DESTROYING_GUNPOWDER.get().defaultBlockState(), 0);
                    level.destroyBlock(pos, true, player, 1);
                    damageItemIfSurvival(player, level, pos, blockState);
                }

                return InteractionResult.sidedSuccess(isClientSide);
            }
        }

        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        var mainHandItemStack = player.getMainHandItem();
        var offHandItemStack = player.getOffhandItem();

        if (offHandItemStack.isEmpty() || (isEnhancedRod() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT.get())))
            return InteractionResultHolder.pass(mainHandItemStack);

        var offHandItem = offHandItemStack.getItem();

        if (offHandItem == ItemRegistry.FLAME_STONE.get())
        {
            if (level.getLevelData() instanceof ServerLevelData levelData && player instanceof ServerPlayer serverPlayer)
            {
                playWeatherSound(serverPlayer);
                levelData.setRainTime(0);
                levelData.setThunderTime(0);
                levelData.setRaining(false);
                levelData.setThundering(false);
                damageItem(1, player);
            }
            else if (level.getLevelData() instanceof ClientLevel.ClientLevelData levelData)
            {
                levelData.setRaining(false);
            }

            return InteractionResultHolder.consume(player.getItemInHand(interactionHand));
        }
        else if (offHandItem == ItemRegistry.MARINE_CRYSTAL.get())
        {
            if (level.getLevelData() instanceof ServerLevelData levelData && player instanceof ServerPlayer serverPlayer)
            {
                playWeatherSound(serverPlayer);
                levelData.setRaining(true);
                levelData.setThundering(false);
                level.setRainLevel(0.3F);
                level.setThunderLevel(0);
                if (levelData.getRainTime() == 0)
                {
                    var weatherLasts = level.random.nextIntBetweenInclusive(1200, 24000);
                    levelData.setRainTime(weatherLasts);
                }
                levelData.setThunderTime(0);
                damageItem(1, player);
            }
            else if (level.getLevelData() instanceof ClientLevel.ClientLevelData levelData)
            {
                levelData.setRaining(true);
                level.setRainLevel(0.3F);
                level.setThunderLevel(0);
            }

            return InteractionResultHolder.consume(player.getItemInHand(interactionHand));
        }
        else if (offHandItem == ItemRegistry.STRENGTH_PEARL.get())
        {
            if (level.getLevelData() instanceof ServerLevelData levelData && player instanceof ServerPlayer serverPlayer)
            {
                playWeatherSound(serverPlayer);
                levelData.setClearWeatherTime(0);
                levelData.setRaining(true);
                levelData.setThundering(true);
                level.setThunderLevel(1.0F);
                level.setRainLevel(1.0F);
                if (levelData.getRainTime() == 0 || levelData.getThunderTime() == 0)
                {
                    var weatherLasts = level.random.nextIntBetweenInclusive(1200, 24000);
                    levelData.setRainTime(weatherLasts);
                    levelData.setThunderTime(weatherLasts);
                }
                damageItem(1, player);
            }
            else if (level.getLevelData() instanceof ClientLevel.ClientLevelData levelData)
            {
                levelData.setRaining(true);
                level.setThunderLevel(1.0F);
                level.setRainLevel(1.0F);
            }

            return InteractionResultHolder.consume(player.getItemInHand(interactionHand));
        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    private void playWeatherSound(ServerPlayer serverPlayer)
    {
        new PlaySoundPacket(SoundEvents.EXPERIENCE_ORB_PICKUP).sendToPlayer(serverPlayer);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        var block = state.getBlock();

        return super.isCorrectToolForDrops(stack, state)
                || (!isBroken(stack) && (block == Blocks.TUFF));
    }

    @Override
    protected int getShiftDepth()
    {
        return 15;
    }

    @Override
    protected int getShiftingRadius()
    {
        return 3;
    }

    @Override
    protected int getSculkDetectionDepth()
    {
        return 192;
    }
}
