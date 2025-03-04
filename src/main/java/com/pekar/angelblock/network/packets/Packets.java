package com.pekar.angelblock.network.packets;

class Packets
{
    private Packets()
    {}

    static final String CreeperDetectedPacketId = "creeper_detected";
    static final String KeyPressedPacketId = "key_pressed";
    static final String ClientTickPacketId = "client_tick";
    static final String ToolsModeChangePacketId = "tools_mode_change";
    static final String PlaySoundPacketId = "play_sound";
    static final String HoldAngelPacketId = "hold_angel";
    static final String AngelRodLightPacketId = "angel_light";
    static final String UpdateArmorDurabilityPacketToServerId = "uad_server";
    static final String UpdateArmorDurabilityPacketToClientId = "uad_client";
}
