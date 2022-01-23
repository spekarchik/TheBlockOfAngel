package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.events.PlayerManager;
import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class KeyPressedPacket extends ClientToServerPacket
{
    private String pressedKeyName;

    public KeyPressedPacket(String pressedKeyName)
    {
        this.pressedKeyName = pressedKeyName;
    }

    @Override
    protected void onReceive(ServerPlayer serverPlayer)
    {
        for (IArmor armor : PlayerManager.instance().getPlayerByEntityName(serverPlayer.getName().getContents()).getArmorTypesUsed())
        {
            armor.onKeyInputEvent(pressedKeyName);
        }
    }

    @Override
    protected int getPacketId()
    {
        return Packets.KeyPressedPacketId;
    }

    @Override
    protected Packet create(FriendlyByteBuf buffer)
    {
        return new KeyPressedPacket(buffer.readUtf());
    }

    @Override
    protected void encode(FriendlyByteBuf buffer)
    {
        buffer.writeUtf(pressedKeyName);
    }
}
