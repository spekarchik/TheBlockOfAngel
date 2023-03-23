package com.pekar.angelblock.blocks.tile_entities.monsters;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ElderGuardian extends Monster
{
    public ElderGuardian(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.ElderGuardian;
    }

    @Override
    public Item getActionItem()
    {
        return Items.WET_SPONGE;
    }
}
