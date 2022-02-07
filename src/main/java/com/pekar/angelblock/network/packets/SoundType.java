package com.pekar.angelblock.network.packets;

public enum SoundType
{
    UNDEFINED,
    PLANT,
    BLOCK_CHANGED,
    WATER_PLACED,
    LAVA_PLACED,
    STEAM;

    public static int getIndex(SoundType soundType)
    {
        return switch (soundType)
                {
                    case UNDEFINED -> 0;
                    case PLANT -> 1;
                    case BLOCK_CHANGED -> 2;
                    case WATER_PLACED -> 3;
                    case LAVA_PLACED -> 4;
                    case STEAM -> 5;
                };
    }

    public static SoundType getByIndex(int index)
    {
        return switch (index)
                {
                    case 1 -> PLANT;
                    case 2 -> BLOCK_CHANGED;
                    case 3 -> WATER_PLACED;
                    case 4 -> LAVA_PLACED;
                    case 5 -> STEAM;
                    default -> UNDEFINED;
                };
    }
}
