package com.pekar.angelblock.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class ClientToServerPacket extends Packet
{
    protected ClientToServerPacket()
    {}

    protected ClientToServerPacket(FriendlyByteBuf buffer)
    {
        super(buffer);
    }

    public void sendToServer()
    {
        PacketHandler.INSTANCE.sendToServer(this);
    }

    @Override
    protected final NetworkDirection getDirection()
    {
        return NetworkDirection.PLAY_TO_SERVER;
    }

    @Override
    protected final void onReceive(@NotNull Supplier<NetworkEvent.Context> ctx)
    {
        var context = ctx.get();
        onReceive(context.getSender());
    }

    protected abstract void onReceive(ServerPlayer serverPlayer);
}
