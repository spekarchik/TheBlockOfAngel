package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.blocks.tile_entities.AngelRodBlockEntity;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

import java.util.List;

public class AngelRod extends EndRod
{
    public AngelRod(ModToolMaterial material, boolean isMagnetic, Properties properties)
    {
        super(material, isMagnetic, properties);
    }

    private String getRodId()
    {
        return ToolRegistry.ANGEL_ROD.getRegisteredName();
    }

    @Override
    protected void appendPlacingBlockInfo(List<Component> tooltipComponents, boolean selectAsNew)
    {
        super.appendPlacingBlockInfo(tooltipComponents, false);
    }

    @Override
    protected void appendBlockTransformInfo(List<Component> tooltipComponents, boolean selectAsNew)
    {
        super.appendBlockTransformInfo(tooltipComponents, false);

        for (int i = 2; i <= 5; i++)
        {
            if (i == 3) continue;
            tooltipComponents.add(getDescription(getRodId(), i,false, false, false, false, selectAsNew, false));
        }
    }

    @Override
    protected void appendMagneticInfo(List<Component> tooltipComponents)
    {
        super.appendMagneticInfo(tooltipComponents);
    }

    protected void appendCommonPostInfo(List<Component> tooltipComponents)
    {
        for (int i = 7; i <= 8; i++)
        {
            tooltipComponents.add(getDescription(getRodId(), i, false, false, false, false, false, i == 7));
        }
    }

    @Override
    protected InteractionResult useOnInternal(UseOnContext context)
    {
        var player = context.getPlayer();

        if (player == null) return InteractionResult.PASS;

        var level = context.getLevel();
        var pos = context.getClickedPos();
        var blockState = level.getBlockState(pos);
        var facing = context.getClickedFace();
        var mainHandItemStack = player.getMainHandItem();
        var offHandItemStack = player.getOffhandItem();
        var hand = !mainHandItemStack.isEmpty() && mainHandItemStack.getItem() == this
                ? InteractionHand.MAIN_HAND
                : InteractionHand.OFF_HAND;
        var itemStack = hand == InteractionHand.MAIN_HAND ? mainHandItemStack : offHandItemStack;

        if (player.isShiftKeyDown() && blockState.isSolidRender())
        {
            if (facing == Direction.UP && level.isEmptyBlock(pos.above()))
            {
                var isClientSide = level.isClientSide();
                if (!isClientSide)
                {
                    level.setBlock(pos.above(), BlockRegistry.ANGEL_ROD_BLOCK.get().defaultBlockState(), 11);
                    var blockEntity = level.getBlockEntity(pos.above());
                    if (blockEntity instanceof AngelRodBlockEntity angelRodBlockEntity)
                    {
                        angelRodBlockEntity.setDamage(getDamage(itemStack));
                    }

                    if (player instanceof ServerPlayer serverPlayer)
                    {
                        new PlaySoundPacket(SoundEvents.STONE_PLACE).sendToPlayer(serverPlayer);
                        CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos.above(), itemStack);
                    }
                }

                var itemCount = itemStack.getCount();
                if (itemCount > 0)
                    itemStack.setCount(itemCount - 1);

                return getToolInteractionResult(true, isClientSide);
            }
        }

        return super.useOnInternal(context);
    }

    @Override
    public boolean isDamageable(ItemStack stack)
    {
        return true;
    }
}
