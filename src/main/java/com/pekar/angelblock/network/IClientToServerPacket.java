package com.pekar.angelblock.network;

import net.minecraft.server.level.ServerPlayer;

public interface IClientToServerPacket extends IPacket
{
    void sendToServer();
    void onReceive(ServerPlayer serverPlayer);
}
