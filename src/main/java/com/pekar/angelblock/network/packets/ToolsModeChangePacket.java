package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.Packet;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.potions.PotionUtils;
import com.pekar.angelblock.tools.IModTool;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class ToolsModeChangePacket extends ClientToServerPacket
{
    @Override
    protected void onReceive(ServerPlayer player)
    {
        boolean isAdvancedModeActive = player.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get());

        if (isAdvancedModeActive)
        {
            player.removeEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get());
        }
        else
        {
            var heldItem = player.getMainHandItem().getItem();
            if (!(heldItem instanceof IModTool)) return;

            var tool = (IModTool) heldItem;
            if (!tool.isEnhancedTool()) return;

            var effect = new MobEffectInstance(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get(),
                    PotionUtils.DURATION_UNLIMITED, 0, false, true);

            player.addEffect(effect);
        }

    }

    @Override
    protected int getPacketId()
    {
        return Packets.ToolsModeChangePacketId;
    }

    @Override
    protected Packet create(FriendlyByteBuf buffer)
    {
        return new ToolsModeChangePacket();
    }

//    @Override
//    public void onReceive(EntityPlayerMP player)
//    {
//        Item heldItem = player.getHeldItemMainhand().getItem();
//        if (heldItem == ToolRegistry.RENDELITHIC_SWORD)
//        {
//            boolean isFireModeActive = player.isPotionActive(PotionRegistry.FIRE_MODE_POTION);
//            if (isFireModeActive)
//            {
//                player.removePotionEffect(PotionRegistry.FIRE_MODE_POTION);
//            }
//            else
//            {
//                PotionEffect potionEffect = new PotionEffect(PotionRegistry.FIRE_MODE_POTION,
//                        PotionUtils.DURATION_UNLIMITED, 0, false, true);
//
//                player.addPotionEffect(potionEffect);
//            }
//        }
//        else if (heldItem == ToolRegistry.SUPER_SWORD)
//        {
//            boolean isFireModeActive = player.isPotionActive(PotionRegistry.FIRE_MODE_POTION);
//            boolean isExplosionModeActive = player.isPotionActive(PotionRegistry.EXPLOSION_MODE_POTION);
//
//            if (isFireModeActive)
//            {
//                player.removePotionEffect(PotionRegistry.FIRE_MODE_POTION);
//
//                PotionEffect potionEffect = new PotionEffect(PotionRegistry.EXPLOSION_MODE_POTION,
//                        PotionUtils.DURATION_UNLIMITED, 0, false, true);
//
//                player.addPotionEffect(potionEffect);
//            }
//            else if (isExplosionModeActive)
//            {
//                player.removePotionEffect(PotionRegistry.EXPLOSION_MODE_POTION);
//            }
//            else
//            {
//                PotionEffect potionEffect = new PotionEffect(PotionRegistry.FIRE_MODE_POTION,
//                        PotionUtils.DURATION_UNLIMITED, 0, false, true);
//
//                player.addPotionEffect(potionEffect);
//            }
//        }
//        else if (heldItem == ToolRegistry.DIAMITHIC_SWORD)
//        {
//            boolean isExplosionModeActive = player.isPotionActive(PotionRegistry.EXPLOSION_MODE_POTION);
//
//            if (isExplosionModeActive)
//            {
//                player.removePotionEffect(PotionRegistry.EXPLOSION_MODE_POTION);
//            }
//            else
//            {
//                PotionEffect potionEffect = new PotionEffect(PotionRegistry.EXPLOSION_MODE_POTION,
//                        PotionUtils.DURATION_UNLIMITED, 0, false, true);
//
//                player.addPotionEffect(potionEffect);
//            }
//        }
//        else if (Utils.isEnhancedTool(heldItem))
//        {
//            boolean isSingleModeActive = player.isPotionActive(PotionRegistry.SINGLE_MODE_POTION);
//
//            if (isSingleModeActive)
//            {
//                player.removePotionEffect(PotionRegistry.SINGLE_MODE_POTION);
//            }
//            else
//            {
//                PotionEffect potionEffect = new PotionEffect(PotionRegistry.SINGLE_MODE_POTION,
//                        PotionUtils.DURATION_UNLIMITED, 0, false, true);
//
//                player.addPotionEffect(potionEffect);
//            }
//        }
//    }
}
