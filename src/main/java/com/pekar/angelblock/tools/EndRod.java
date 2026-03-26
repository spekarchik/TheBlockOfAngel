package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.Weather;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class EndRod extends AmethystRod
{

    public EndRod(ModToolMaterial material, boolean isMagnetic, Properties properties)
    {
        super(material, isMagnetic, properties);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
    {
        if (blockState.getBlock() == Blocks.TUFF)
            return 25.0F;

        return super.getDestroySpeed(itemStack, blockState);
    }

    @Override
    protected void additionalActionOnMineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity entity)
    {
        super.additionalActionOnMineBlock(itemStack, level, blockState, pos, entity);

        if (blockState.getBlock() == Blocks.TUFF)
        {
            if (!level.isClientSide() && entity instanceof Player player && !hasCriticalDamage(itemStack) && player.getFoodData().getFoodLevel() > 0)
            {
                level.setBlock(pos, BlockRegistry.DESTROYING_SALTPETER.get().defaultBlockState(), 0);
                level.destroyBlock(pos, true, player, 1);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
                causeMinePlayerExhaustion(player);
            }
        }
    }

    @Override
    protected InteractionResult useOnInternal(UseOnContext context)
    {
        var player = context.getPlayer();

        if (isEnhanced() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT))
            return super.useOnInternal(context);

        var itemStack = player.getItemInHand(context.getHand());

        if (!hasCriticalDamage(itemStack) && player.getFoodData().getFoodLevel() > 0)
        {
            var pos = context.getClickedPos();
            var level = player.level();
            var blockState = level.getBlockState(pos);
            var block = blockState.getBlock();

            var hand = context.getHand();
            var facing = context.getClickedFace();

            if (facing == Direction.UP)
            {
                if (block == Blocks.END_STONE && level.isEmptyBlock(pos.above()))
                {
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    return plant(player, level, pos, hand, facing, Blocks.CHORUS_FLOWER);
                }
            }
        }

        return super.useOnInternal(context);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand)
    {
        var offHandItemStack = player.getOffhandItem();

        if (offHandItemStack.isEmpty() || hasCriticalDamage(player.getMainHandItem())
                || (isEnhanced() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT))
                || player.getFoodData().getFoodLevel() <= 0)
            return InteractionResult.PASS;

        if (!utils.dimension.isOverworld(level.dimension()))
            return InteractionResult.FAIL;

        var offHandItem = offHandItemStack.getItem();

        final int WEATHER_CHANGE_EXHAUSTION_MULTIPLIER = 16;

        if (offHandItem == ItemRegistry.FLAME_STONE.get())
        {
            Weather.of(level).clear();
            if (player instanceof ServerPlayer serverPlayer)
            {
                playWeatherSound(serverPlayer);
                damageMainHandItem(1, player);
                utils.player.causePlayerExhaustion(player, WEATHER_CHANGE_EXHAUSTION_MULTIPLIER);
            }
            else
            {
                player.swing(interactionHand);
            }

            return InteractionResult.CONSUME;
        }
        else if (offHandItem == ItemRegistry.MARINE_CRYSTAL.get())
        {
            Weather.of(level).rain();
            if (player instanceof ServerPlayer serverPlayer)
            {
                playWeatherSound(serverPlayer);
                damageMainHandItem(1, player);
                utils.player.causePlayerExhaustion(player, WEATHER_CHANGE_EXHAUSTION_MULTIPLIER);
            }
            else
            {
                player.swing(interactionHand);
            }

            return InteractionResult.CONSUME;
        }
        else if (offHandItem == ItemRegistry.STRENGTH_PEARL.get())
        {
            Weather.of(level).thunder();
            if (player instanceof ServerPlayer serverPlayer)
            {
                playWeatherSound(serverPlayer);
                damageMainHandItem(1, player);
                utils.player.causePlayerExhaustion(player, WEATHER_CHANGE_EXHAUSTION_MULTIPLIER);
            }
            else
            {
                player.swing(interactionHand);
            }

            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    private void playWeatherSound(ServerPlayer serverPlayer)
    {
        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.MASTER);
    }

    private String getRodDescriptionId()
    {
        return formatDescriptionId(getRodId());
    }

    private String getRodId()
    {
        return ToolRegistry.END_ROD.getRegisteredName();
    }

    @Override
    protected void appendPlacingBlockInfo(ITooltip tooltip, boolean selectAsNew)
    {
        super.appendPlacingBlockInfo(tooltip, false);

        tooltip.addLine(getRodDescriptionId(), 3).withFormatting(ChatFormatting.WHITE, selectAsNew).apply();
    }

    @Override
    protected void appendDestroyingBlockInfo(ITooltip tooltip, boolean selectAsNew)
    {
        super.appendDestroyingBlockInfo(tooltip, false);

        tooltip.addLine(getRodDescriptionId(), 1).withFormatting(ChatFormatting.WHITE, selectAsNew).apply();
    }

    @Override
    protected void appendBlockTransformInfo(ITooltip tooltip, boolean selectAsNew)
    {
        super.appendBlockTransformInfo(tooltip, false);

        for (int i = 4; i <= 5; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).withFormatting(ChatFormatting.WHITE, selectAsNew).styledAs(TextStyle.Header, i == 4).apply();
        }
    }

    @Override
    protected void appendMagneticInfo(ITooltip tooltip)
    {
        for (int i = 7; i <= 15; i++)
        {
            if (i == 15) tooltip.addEmptyLine();
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.Header, i == 7).styledAs(TextStyle.DarkGray, i == 15).apply();
        }
    }

    protected void appendMainPostInfo(ITooltip tooltip)
    {
        for (int i = 16; i <= 18; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.DarkGray, i <= 17).apply();
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
