package com.pekar.angelblock.events.cleaners;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;

public class TrackedAllay extends TrackedTarget<Allay>
{
    public TrackedAllay(Allay allay, BlockPos pos, Player owner)
    {
        super(allay, pos, owner, 6000, false, TrackedAllay::removeTarget, 10.0, 60.0);
    }

    private static boolean removeTarget(ITrackedTarget target)
    {
        if (!(target instanceof TrackedAllay allayTarget)) return false;

        var allay = allayTarget.getTargetInstance();

        if (allay.isHolding(stack -> !stack.isEmpty())
            || allay.hasCustomName()
            || allay.isPersistenceRequired()) return false;

        allay.discard();
        return true;
    }
}
