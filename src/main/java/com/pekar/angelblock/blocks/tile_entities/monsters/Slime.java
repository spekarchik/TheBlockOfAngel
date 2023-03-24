package com.pekar.angelblock.blocks.tile_entities.monsters;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Slime extends Monster
{
    public Slime(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.Slime
                && !(livingEntity instanceof MagmaCube);
    }

    @Override
    public Item getActionItem()
    {
        return Items.SLIME_BALL;
    }

    @Override
    public EntityType<? extends Entity> getEntityType()
    {
        return EntityType.SLIME;
    }
}
