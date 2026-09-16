package com.pekar.angelblock.blocks.tile_entities.monsters;

import com.pekar.angelblock.blocks.tile_entities.spawn.ISpawnStrategy;
import com.pekar.angelblock.blocks.tile_entities.spawn.TallMonsterSpawnStrategy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Enderman extends Monster
{
    public Enderman(int id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.Enderman;
    }

    @Override
    public Item getActionItem()
    {
        return Items.ENDER_PEARL;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityTypes.ENDERMAN;
    }

    @Override
    protected ISpawnStrategy getSpawnStrategyInternal()
    {
        return new TallMonsterSpawnStrategy();
    }
}
