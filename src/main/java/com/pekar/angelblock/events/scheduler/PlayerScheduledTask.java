package com.pekar.angelblock.events.scheduler;

import com.pekar.angelblock.events.scheduler.base.ScheduledTask;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class PlayerScheduledTask extends ScheduledTask<ServerPlayer> implements IPlayerScheduledTask
{
    public PlayerScheduledTask(ServerPlayer player, int ticks, Consumer<ServerPlayer> doOnComplete)
    {
        super(ticks, player, doOnComplete);
    }

    @Override
    public final boolean belongsTo(ServerPlayer player)
    {
        return getObject().getUUID().equals(player.getUUID());
    }
}
