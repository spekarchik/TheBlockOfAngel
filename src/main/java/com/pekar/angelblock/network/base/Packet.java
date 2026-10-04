package com.pekar.angelblock.network.base;

import com.pekar.angelblock.Main;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import static com.pekar.angelblock.utils.Resources.createResourceLocation;

public abstract class Packet implements IPacket, CustomPacketPayload
{
    private Type<Packet> type;

    protected Packet()
    {
    }

    @Override
    public final Type<Packet> type()
    {
        return type == null
                ? (type = new Type<>(createResourceLocation(Main.MODID, getPacketId())))
                : type;
    }

    protected abstract boolean isServerToClient();

    protected abstract void onReceive(Player player);
}
