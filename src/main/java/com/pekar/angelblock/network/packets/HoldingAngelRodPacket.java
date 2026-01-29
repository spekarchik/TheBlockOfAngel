package com.pekar.angelblock.network.packets;

import com.pekar.angelblock.network.ClientToServerPacket;
import com.pekar.angelblock.network.Packet;
import com.pekar.angelblock.utils.Utils;
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
        var monsters = serverPlayer.level().getEntities((LivingEntity)null,
                serverPlayer.getBoundingBox().inflate(EFFECTIVE_RADIUS),
                entity -> entity instanceof Enemy);

        for (Entity entity : monsters)
        {
            if (serverPlayer.getFoodData().getFoodLevel() > 0)
            {
                entity.discard();
                Utils.instance.player.causePlayerExhaustion(serverPlayer, 2);
            }
        }
    }

    @Override
    public String getPacketId()
    {
        return Packets.HoldAngelPacketId;
    }

    @Override
    public Packet decode(FriendlyByteBuf buffer)
    {
        return new HoldingAngelRodPacket();
    }
}
