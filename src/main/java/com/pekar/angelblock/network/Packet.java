package com.pekar.angelblock.network;

import com.pekar.angelblock.Main;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.pekar.angelblock.utils.Resources.createResourceLocation;

public abstract class Packet implements IPacket, CustomPacketPayload
{
    private Type<Packet> type;

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
        return type == null
                ? (type = new Type<>(createResourceLocation(Main.MODID, getPacketId())))
                : type;
    }

    protected abstract boolean isServerToClient();

    protected abstract void onReceive(IPayloadContext contextContainer);
}
