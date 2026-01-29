package com.pekar.angelblock.events;

import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public interface ILivingDeathEventHandler
{
    void onLivingDeathEvent(LivingDeathEvent event);
    BlockPos getPosition();
}
