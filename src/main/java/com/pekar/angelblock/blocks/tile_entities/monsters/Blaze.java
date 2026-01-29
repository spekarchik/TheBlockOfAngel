package com.pekar.angelblock.blocks.tile_entities.monsters;

import com.pekar.angelblock.blocks.tile_entities.spawn.FlyingMonsterSpawnStrategy;
import com.pekar.angelblock.blocks.tile_entities.spawn.ISpawnStrategy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Blaze extends Monster
{
    public Blaze(int id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.Blaze;
    }

    @Override
    public Item getActionItem()
    {
        return Items.BLAZE_ROD;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityType.BLAZE;
    }

    @Override
    protected ISpawnStrategy getSpawnStrategyInternal()
    {
        return new FlyingMonsterSpawnStrategy();
    }
}
