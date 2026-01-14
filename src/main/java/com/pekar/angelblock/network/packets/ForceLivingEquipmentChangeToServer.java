package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Set;

public class ForceLivingEquipmentChangeToServer extends ClientToServerPacket
{
    @SuppressWarnings("resource")
    @Override
    public void onReceive(ServerPlayer serverPlayer)
    {
        if (serverPlayer.level().isClientSide()) return;

        // Simulate equipment change for all slots by posting a LivingEquipmentChangeEvent
        for (EquipmentSlot slot : Set.of(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND))
        {
            ItemStack current = serverPlayer.getItemBySlot(slot);
            LivingEquipmentChangeEvent event = new LivingEquipmentChangeEvent(serverPlayer, slot, ItemStack.EMPTY, current);
            NeoForge.EVENT_BUS.post(event);
        }
    }

    @Override
    public String getPacketId()
    {
        return Packets.ForceLivingEquipmentChangeToServerId;
    }

    @Override
    public IPacket decode(FriendlyByteBuf buffer)
    {
        return new ForceLivingEquipmentChangeToServer();
    }
}
