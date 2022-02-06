package com.pekar.angelblock.network.packets;

public enum SoundType
{
    UNDEFINED,
    PLANT,
    BLOCK_CHANGED,
    WATER_PLACED;

    public static int getIndex(SoundType soundType)
    {
        return switch (soundType)
                {
                    case UNDEFINED -> 0;
                    case PLANT -> 1;
                    case BLOCK_CHANGED -> 2;
                    case WATER_PLACED -> 3;
                };
    }

    public static SoundType getByIndex(int index)
    {
        return switch (index)
                {
                    case 1 -> PLANT;
                    case 2 -> BLOCK_CHANGED;
                    case 3 -> WATER_PLACED;
                    default -> UNDEFINED;
                };
    }
}
