package com.pekar.angelblock.clientaccess;

import com.pekar.angelblock.network.base.ClientToServerPacket;
import net.minecraft.sounds.SoundEvent;

public interface INetworkClientAccessor
{
    void sendToServer(ClientToServerPacket packet);
    boolean isPauseScreen();
    void playClientSound(SoundEvent sound, float volume, float pitch);
}
