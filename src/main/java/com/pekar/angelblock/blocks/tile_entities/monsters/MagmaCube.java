package com.pekar.angelblock.blocks.tile_entities.monsters;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class MagmaCube extends Monster
{
    public MagmaCube(byte id)
    {
        super(id);
    }

    @Override
    public boolean belongs(LivingEntity livingEntity)
    {
        return livingEntity instanceof net.minecraft.world.entity.monster.MagmaCube;
    }

    @Override
    public Item getActionItem()
    {
        return Items.MAGMA_CREAM;
    }
}
