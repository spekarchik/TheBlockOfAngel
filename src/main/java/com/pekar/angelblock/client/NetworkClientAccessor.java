package com.pekar.angelblock.client;

import com.mojang.logging.LogUtils;
import com.pekar.angelblock.clientaccess.INetworkClientAccessor;
import com.pekar.angelblock.network.base.ClientToServerPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.sounds.SoundEvent;
import org.slf4j.Logger;

public class NetworkClientAccessor implements INetworkClientAccessor
{
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void sendToServer(ClientToServerPacket packet)
    {
        var connection = Minecraft.getInstance().getConnection();
        if (connection != null)
        {
            var wrapper = new ServerboundCustomPayloadPacket(packet);
            connection.getConnection().send(wrapper);
        }
        else
        {
            LOGGER.warn("Unable to send packet to server: connection is null");
        }
    }

    @Override
    public boolean isPauseScreen()
    {
        return Minecraft.getInstance().screen instanceof PauseScreen;
    }

    @Override
    public void playClientSound(SoundEvent sound, float volume, float pitch)
    {
        if (Minecraft.getInstance().player instanceof LocalPlayer player)
           player.playSound(sound, volume, pitch);
    }
}
