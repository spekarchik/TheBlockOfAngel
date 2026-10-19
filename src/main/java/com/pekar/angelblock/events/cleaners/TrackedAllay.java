package com.pekar.angelblock.events.cleaners;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class TrackedAllay extends TrackedTarget<Allay>
{
    private final UUID ownerUuid;

    public TrackedAllay(Allay allay, UUID ownerUuid)
    {
        super(allay, null, 6000);
        this.ownerUuid = ownerUuid;
    }

    @Override
    public Player getOwner()
    {
        return getTargetLevel().getPlayerByUUID(ownerUuid);
    }

    @Override
    public ServerLevel getTargetLevel()
    {
        return (ServerLevel) getTargetInstance().level();
    }

    @Override
    public void remove()
    {
        var targetInstance = getTargetInstance();

        if (!targetInstance.isRemoved())
            targetInstance.discard();
    }

    @Override
    public String getId()
    {
        return "Allay:" + getTargetInstance().getUUID();
    }

    @Override
    protected ITargetBehavior createBehavior()
    {
        return new AllayTargetBehavior(this);
    }
}
