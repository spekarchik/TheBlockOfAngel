package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.events.block_cleaner.LightCleaner;
import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;

public class AngelRodLightPacket extends ClientToServerPacket
{
    @Override
    public void onReceive(ServerPlayer serverPlayer)
    {
        var lightBlock = Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15);
        var level = serverPlayer.level();
        var pos = serverPlayer.blockPosition();

        if (level.getBlockState(pos).isAir())
        {
            level.setBlock(pos, lightBlock, 3);
            LightCleaner.add(serverPlayer, pos, 10);
        }
    }

    @Override
    public String getPacketId()
    {
        return Packets.AngelRodLightPacketId;
    }

    @Override
    public Packet decode(FriendlyByteBuf buffer)
    {
        return new AngelRodLightPacket();
    }
}
