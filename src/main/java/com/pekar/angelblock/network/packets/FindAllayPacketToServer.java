package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.cleaners.TrackedAllaysData;
import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class FindAllayPacketToServer extends ClientToServerPacket
{
    @Override
    public void onReceive(ServerPlayer player)
    {
        var level = player.level();
        var storage = TrackedAllaysData.get(level);
        var trackedAllays = TrackedAllaysData.restoreAllays(level, storage);
        for (var target : trackedAllays)
        {
            Cleaner.add(target);
            storage.remove(target);
        }
    }

    @Override
    public String getPacketId()
    {
        return Packets.FindAllayPacketToServerId;
    }

    @Override
    public IPacket decode(FriendlyByteBuf buffer)
    {
        return new FindAllayPacketToServer();
    }
}
