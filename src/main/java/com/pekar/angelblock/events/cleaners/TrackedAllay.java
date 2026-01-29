package com.pekar.angelblock.events.cleaners;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;

public class TrackedAllay extends TrackedTarget<Allay>
{
    public TrackedAllay(Allay allay, Player owner)
    {
        super(allay, owner, 6000);
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
