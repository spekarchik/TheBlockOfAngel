package com.pekar.angelblock.blocks.tile_entities.monsters;

import com.pekar.angelblock.blocks.tile_entities.spawn.ISpawnStrategy;
import com.pekar.angelblock.blocks.tile_entities.spawn.OnGroundMonsterSpawnStrategy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Creeper extends Monster
{
    public Creeper(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.Creeper;
    }

    @Override
    public Item getActionItem()
    {
        return Items.GUNPOWDER;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityType.CREEPER;
    }

    @Override
    protected ISpawnStrategy getSpawnStrategyInternal()
    {
        return new OnGroundMonsterSpawnStrategy();
    }
}
