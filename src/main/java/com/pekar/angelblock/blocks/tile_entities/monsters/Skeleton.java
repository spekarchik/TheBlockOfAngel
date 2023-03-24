package com.pekar.angelblock.blocks.tile_entities.monsters;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Skeleton extends Monster
{
    public Skeleton(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.AbstractSkeleton
                && !(livingEntity instanceof WitherSkeleton);
    }

    @Override
    public Item getActionItem()
    {
        return Items.BONE;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityType.SKELETON;
    }
}
