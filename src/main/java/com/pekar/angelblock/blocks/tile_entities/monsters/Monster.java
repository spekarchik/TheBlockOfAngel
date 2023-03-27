package com.pekar.angelblock.blocks.tile_entities.monsters;

import com.pekar.angelblock.blocks.tile_entities.spawn.ISpawnStrategy;

abstract class Monster implements IMonster
{
    private final byte id;
    private ISpawnStrategy spawnStrategy;

    protected Monster(byte id)
    {
        this.id = id;
    }

    @Override
    public final byte getId()
    {
        return id;
    }

    @Override
    public int hashCode()
    {
        return Byte.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Monster other)) return false;
        return hashCode() == other.hashCode();
    }

    @Override
    public final ISpawnStrategy getSpawnStrategy()
    {
        return spawnStrategy == null ? (spawnStrategy = getSpawnStrategyInternal()) : spawnStrategy;
    }

    protected abstract ISpawnStrategy getSpawnStrategyInternal();
}
