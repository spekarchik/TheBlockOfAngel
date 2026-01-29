package com.pekar.angelblock.blocks.tile_entities.monsters;

import com.pekar.angelblock.blocks.tile_entities.spawn.ISpawnStrategy;
import com.pekar.angelblock.blocks.tile_entities.spawn.InWaterMonsterSpawnStrategy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Guardian extends Monster
{
    public Guardian(int id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.Guardian
                && !(livingEntity instanceof ElderGuardian);
    }

    @Override
    public Item getActionItem()
    {
        return Items.PRISMARINE_SHARD;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityType.GUARDIAN;
    }

    @Override
    protected ISpawnStrategy getSpawnStrategyInternal()
    {
        return new InWaterMonsterSpawnStrategy();
    }
}
