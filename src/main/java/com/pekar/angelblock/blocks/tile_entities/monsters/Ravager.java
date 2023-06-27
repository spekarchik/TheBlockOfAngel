package com.pekar.angelblock.blocks.tile_entities.monsters;

import com.pekar.angelblock.blocks.tile_entities.spawn.ISpawnStrategy;
import com.pekar.angelblock.blocks.tile_entities.spawn.TallMonsterSpawnStrategy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Ravager extends Monster
{
    public Ravager(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.Ravager;
    }

    @Override
    public Item getActionItem()
    {
        return Items.SADDLE;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityType.RAVAGER;
    }

    @Override
    protected ISpawnStrategy getSpawnStrategyInternal()
    {
        return new TallMonsterSpawnStrategy();
    }
}
