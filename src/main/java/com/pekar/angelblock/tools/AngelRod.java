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
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AngelRod extends EndRod
{
    public AngelRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        for (int i = 1; i <= 5; i++)
        {
            components.add(getDescription(i, false, false, i == 4 || i == 5, i <= 2));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
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

        if (player.isShiftKeyDown() && blockState.isSolidRender(level, pos))
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

                return InteractionResult.sidedSuccess(isClientSide);
            }
        }

        return super.useOn(context);
    }

    @Override
    public boolean isDamageable(ItemStack stack)
    {
        return true;
    }
}
