package com.pekar.angelblock.blocks.tile_entities;

import com.pekar.angelblock.events.ILivingDeathEventHandler;
import com.pekar.angelblock.events.PlayerInteractionEvents;
import com.pekar.angelblock.events.PlayerManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DevilBlockEntity extends BlockEntity implements ILivingDeathEventHandler
{
    public DevilBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        super(EntityRegistry.DEVIL_BLOCK_ENTITY.get(), blockPos, blockState);
        activate();
    }

    public void activate()
    {
        PlayerManager.instance().sendMessage("activate");
        PlayerInteractionEvents.subscribeLivingDeath(this);
    }

    public void dispose()
    {
        PlayerManager.instance().sendMessage("dispose");
        PlayerInteractionEvents.unsubscribeLivingDeath(this);
    }

    @Override
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        LivingEntity entity = event.getEntityLiving();
        PlayerManager.instance().sendMessage("living death");
        if (!(entity instanceof Enemy)) return;

        var pos = getPosition();
        double distance = entity.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());

        if (distance < 4900)
        {
            entity.setHealth(entity.getMaxHealth());
            event.setCanceled(true);
        }
    }

    @Override
    public BlockPos getPosition()
    {
        return getBlockPos();
    }
}
