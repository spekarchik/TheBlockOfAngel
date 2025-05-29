package com.pekar.angelblock.items;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GuardianEye extends ModItemWithMultipleHoverText
{
    public GuardianEye()
    {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 1; i <= 3; i++)
        {
            tooltipComponents.add(getDescription(i, false, false, i == 3, false));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!player.hasEffect(MobEffects.NIGHT_VISION))
        {
            if (player instanceof ServerPlayer serverPlayer)
            {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0, true, true));
                new PlaySoundPacket(SoundEvents.LEVER_CLICK, 2.0F).sendToPlayer(serverPlayer);
            }

            return InteractionResultHolder.sidedSuccess(player.getItemInHand(interactionHand), level.isClientSide());
        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
