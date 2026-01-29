package com.pekar.angelblock.blocks.tile_entities.monsters;

import com.pekar.angelblock.blocks.tile_entities.spawn.ISpawnStrategy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public interface IMonster
{
    int getId();
    boolean belongs(LivingEntity livingEntity);
    Item getActionItem();
    EntityType<? extends Entity> getEntityType();
    ISpawnStrategy getSpawnStrategy();
}
