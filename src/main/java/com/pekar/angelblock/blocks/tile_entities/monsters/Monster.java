package com.pekar.angelblock.blocks.tile_entities.monsters;

abstract class Monster implements IMonster
{
    private final byte id;

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
}
