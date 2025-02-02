package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.events.PlayerManager;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;

public class HoldingAngelRodPacket extends ClientToServerPacket
{
    private static final double EFFECTIVE_RADIUS = 20.0;

    @Override
    public void onReceive(ServerPlayer serverPlayer)
    {
        String playerName = serverPlayer.getName().getString();
        IPlayer player = PlayerManager.instance().getPlayerByEntityName(playerName);
        var playerEntity = player.getEntity();

        var monsters = player.getEntity().level().getEntities((LivingEntity)null,
                playerEntity.getBoundingBox().inflate(EFFECTIVE_RADIUS),
                entity -> entity instanceof Enemy);

        for (Entity entity : monsters)
        {
            entity.discard();
        }
    }

    @Override
    public int getPacketId()
    {
        return Packets.HoldAngelPacketId;
    }

    @Override
    public Packet decode(FriendlyByteBuf buffer)
    {
        return new HoldingAngelRodPacket();
    }
}
