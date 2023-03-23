package com.pekar.angelblock.blocks.tile_entities.monsters;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class Piglin extends Monster
{
    public Piglin(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof AbstractPiglin;
    }

    @Override
    public Item getActionItem()
    {
        return Items.GOLD_NUGGET;
    }
}
