package com.pekar.angelblock.blocks.tile_entities.monsters;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Spider extends Monster
{
    public Spider(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.Spider;
    }

    @Override
    public Item getActionItem()
    {
        return Items.STRING;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityType.SPIDER;
    }
}
