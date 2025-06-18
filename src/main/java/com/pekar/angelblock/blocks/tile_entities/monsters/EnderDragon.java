package com.pekar.angelblock.blocks.tile_entities.monsters;

import com.pekar.angelblock.blocks.tile_entities.spawn.FlyingMonsterSpawnStrategy;
import com.pekar.angelblock.blocks.tile_entities.spawn.ISpawnStrategy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class EnderDragon extends Monster
{
    public EnderDragon(int id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon;
    }

    @Override
    public Item getActionItem()
    {
        return Items.END_CRYSTAL;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ISpawnStrategy getSpawnStrategyInternal()
    {
        return new FlyingMonsterSpawnStrategy();
    }
}
