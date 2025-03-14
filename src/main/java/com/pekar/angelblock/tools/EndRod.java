package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.storage.ServerLevelData;

import java.util.List;

public class EndRod extends AmethystRod
{
    public EndRod(Tier material, boolean isMagnetic, Properties properties)
    {
        super(material, isMagnetic, properties);
    }

    @Override
    protected InteractionResult useOnInternal(UseOnContext context)
    {
        var player = context.getPlayer();

        if (isEnhanced() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT))
            return super.useOnInternal(context);

        var itemStack = player.getItemInHand(context.getHand());

        if (!isBroken(itemStack))
        {
            var pos = context.getClickedPos();
            var level = player.level();
            var blockState = level.getBlockState(pos);
            var block = blockState.getBlock();

            var hand = context.getHand();
            var facing = context.getClickedFace();

            boolean isClientSide = level.isClientSide();

            if (facing == Direction.UP)
            {
                if (block == Blocks.END_STONE)
                {
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    return plant(player, level, pos, hand, facing, Blocks.CHORUS_FLOWER);
                }
            }

            if (block == Blocks.TUFF)
            {
                if (!isClientSide)
                {
                    level.setBlock(pos, BlockRegistry.DESTROYING_SALTPETER.get().defaultBlockState(), 0);
                    level.destroyBlock(pos, true, player, 1);
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }

                return getToolInteractionResult(true, isClientSide);
            }
        }

        return super.useOnInternal(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        var offHandItemStack = player.getOffhandItem();

        if (offHandItemStack.isEmpty() || isBroken(player.getMainHandItem()) || (isEnhanced() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT)))
            return InteractionResultHolder.pass(player.getItemInHand(interactionHand));

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
                damageMainHandItem(1, player);
            }
            else if (level.getLevelData() instanceof ClientLevel.ClientLevelData levelData)
            {
                player.swing(interactionHand);
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
                damageMainHandItem(1, player);
            }
            else if (level.getLevelData() instanceof ClientLevel.ClientLevelData levelData)
            {
                player.swing(interactionHand);
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
                damageMainHandItem(1, player);
            }
            else if (level.getLevelData() instanceof ClientLevel.ClientLevelData levelData)
            {
                player.swing(interactionHand);
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

    private String getRodId()
    {
        return ToolRegistry.END_ROD.getRegisteredName();
    }

    @Override
    protected void appendPlacingBlockInfo(List<Component> tooltipComponents, boolean selectAsNew)
    {
        super.appendPlacingBlockInfo(tooltipComponents, false);

        for (int i = 2; i <= 3; i++)
        {
            tooltipComponents.add(getDescription(getRodId(), i,false, false, false, false, selectAsNew));
        }
    }

    @Override
    protected void appendBlockTransformInfo(List<Component> tooltipComponents, boolean selectAsNew)
    {
        super.appendBlockTransformInfo(tooltipComponents, false);

        for (int i = 4; i <= 5; i++)
        {
            tooltipComponents.add(getDescription(getRodId(), i,i == 4, false, false, false, selectAsNew));
        }
    }

    @Override
    protected void appendMagneticInfo(List<Component> tooltipComponents)
    {
        for (int i = 7; i <= 15; i++)
        {
            if (i == 15) tooltipComponents.add(Component.empty());
            tooltipComponents.add(getDescription(getRodId(), i, i == 7, false, i == 15, false, false));
        }
    }

    protected void appendCommonPostInfo(List<Component> tooltipComponents)
    {
        for (int i = 16; i <= 17; i++)
        {
            tooltipComponents.add(getDescription(getRodId(), i, false, false, i == 16, false, false));
        }
    }

    @Override
    protected int getOreDepth()
    {
        return 15;
    }

    @Override
    protected int getShiftingRadius()
    {
        return 3;
    }

    @Override
    protected int getRailsDetectionDepth()
    {
        return 128;
    }

    @Override
    protected int getSculkDetectionDepth()
    {
        return 192;
    }
}
