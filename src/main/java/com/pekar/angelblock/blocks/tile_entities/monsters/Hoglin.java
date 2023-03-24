package com.pekar.angelblock.blocks.tile_entities.monsters;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Hoglin extends Monster
{
    public Hoglin(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof HoglinBase;
    }

    @Override
    public Item getActionItem()
    {
        return Items.PORKCHOP;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityType.HOGLIN;
    }
}
