package com.pekar.angelblock.network;

import com.pekar.angelblock.events.IEventHandler;
import com.pekar.angelblock.network.packets.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class NetworkingEventHandler implements IEventHandler
{
    public NetworkingEventHandler()
    {
    }

    @SubscribeEvent
    public void register(final RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar("1")
                .executesOn(HandlerThread.NETWORK);

        registerPacket(registrar, new CreeperDetectedPacket());
        registerPacket(registrar, new ClientTickPacket());
        registerPacket(registrar, new KeyPressedPacket());
        registerPacket(registrar, new ToolsModeChangePacket());
        registerPacket(registrar, new PlaySoundPacket(SoundType.UNDEFINED));
        registerPacket(registrar, new HoldingAngelRodPacket());
        registerPacket(registrar, new UpdateArmorDurabilityPacketToServer());
        registerPacket(registrar, new UpdateArmorDurabilityPacketToClient());
//        registerPacket(registrar, new AngelRodLightPacket());
    }

    private <T extends Packet> void registerPacket(PayloadRegistrar registrar, T packet)
    {
        var packetInfoProvider = new PacketInfoProvider<>(packet);

        if (packet.isServerToClient())
        {
            registrar.playToClient(packetInfoProvider.getType(), packetInfoProvider.getStreamCodec(), packetInfoProvider.getHandler());
        }
        else
        {
            registrar.playToServer(packetInfoProvider.getType(), packetInfoProvider.getStreamCodec(), packetInfoProvider.getHandler());
        }
    }
}
