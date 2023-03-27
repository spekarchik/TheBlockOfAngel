package com.pekar.angelblock.blocks.tile_entities.monsters;

import com.pekar.angelblock.blocks.tile_entities.spawn.BigFlyingMonsterSpawnStrategy;
import com.pekar.angelblock.blocks.tile_entities.spawn.ISpawnStrategy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Ghast extends Monster
{
    public Ghast(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.Ghast;
    }

    @Override
    public Item getActionItem()
    {
        return Items.GHAST_TEAR;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityType.GHAST;
    }

    @Override
    protected ISpawnStrategy getSpawnStrategyInternal()
    {
        return new BigFlyingMonsterSpawnStrategy();
    }
}
