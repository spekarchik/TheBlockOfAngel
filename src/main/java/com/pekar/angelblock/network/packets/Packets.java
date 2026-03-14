package com.pekar.angelblock.network.packets;

class Packets
{
    private Packets()
    {}

    static final String CreeperDetectedPacketId = "creeper_detected";
    static final String KeyPressedPacketId = "key_pressed";
    static final String ToolsModeChangePacketId = "tools_mode_change";
    static final String PlaySoundPacketId = "play_sound";
    static final String UpdateArmorDurabilityPacketToServerId = "uad_server";
    static final String UpdateArmorDurabilityPacketToClientId = "uad_client";
    static final String FindAllayPacketToClientId = "find_allay_client";
    static final String FindAllayPacketToServerId = "find_allay_server";
    static final String ForceLivingEquipmentChangeToClientId = "living_equipment_change_client";
    static final String ForceLivingEquipmentChangeToServerId = "living_equipment_change_server";
}
