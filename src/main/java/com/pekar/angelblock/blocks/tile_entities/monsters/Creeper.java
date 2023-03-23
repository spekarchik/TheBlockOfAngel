package com.pekar.angelblock.blocks.tile_entities.monsters;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

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
}
