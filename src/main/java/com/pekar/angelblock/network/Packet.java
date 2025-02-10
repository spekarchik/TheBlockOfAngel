package com.pekar.angelblock.network;

import com.pekar.angelblock.Main;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public abstract class Packet implements IPacket, CustomPacketPayload
{
    private final Type<Packet> type = new Type<>(ResourceLocation.fromNamespaceAndPath(Main.MODID, getPacketId()));

    protected Packet()
    {
    }

    final void handlePacket(final IPayloadContext context)
    {
        context.enqueueWork(() -> onReceive(context))
                        .exceptionally(e ->
                        {
                            context.disconnect(Component.translatable(Main.MODID + " networking failed: ", e.getMessage()));
                            return null;
                        });
    }

    @Override
    public final Type<Packet> type()
    {
        return type;
    }

    protected abstract boolean isServerToClient();

    protected abstract void onReceive(IPayloadContext contextContainer);
}
