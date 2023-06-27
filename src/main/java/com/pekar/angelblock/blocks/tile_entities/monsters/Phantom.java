package com.pekar.angelblock.blocks.tile_entities.monsters;

import com.pekar.angelblock.blocks.tile_entities.spawn.FlyingMonsterSpawnStrategy;
import com.pekar.angelblock.blocks.tile_entities.spawn.ISpawnStrategy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Phantom extends Monster
{
    public Phantom(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.Phantom;
    }

    @Override
    public Item getActionItem()
    {
        return Items.PHANTOM_MEMBRANE;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityType.PHANTOM;
    }

    @Override
    protected ISpawnStrategy getSpawnStrategyInternal()
    {
        return new FlyingMonsterSpawnStrategy();
    }
}
