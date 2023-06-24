package com.pekar.angelblock.network.packets;

public enum SoundType
{
    UNDEFINED,
    PLANT,
    BLOCK_CHANGED,
    WATER_PLACED,
    LAVA_PLACED,
    STEAM,
    AMETHYST_FOUND,
    DIAMOND_FOUND,
    SCULK_FOUND,
    ORE_FOUND,
    INFESTED_BLOCK,
    BONEMEAL,
    RAIL_PLACED,
    REDSTONE_WIRE_PLACED,
    WOOD_PLACED,
    STONE_PLACED,
    MINECRAFT,
    RAILS_FOUND;

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
                    case AMETHYST_FOUND -> 6;
                    case DIAMOND_FOUND -> 7;
                    case ORE_FOUND -> 8;
                    case SCULK_FOUND -> 9;
                    case INFESTED_BLOCK -> 10;
                    case BONEMEAL -> 11;
                    case RAIL_PLACED -> 12;
                    case REDSTONE_WIRE_PLACED -> 13;
                    case WOOD_PLACED -> 14;
                    case STONE_PLACED -> 15;
                    case MINECRAFT -> 16;
                    case RAILS_FOUND -> 17;
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
                    case 6 -> AMETHYST_FOUND;
                    case 7 -> DIAMOND_FOUND;
                    case 8 -> ORE_FOUND;
                    case 9 -> SCULK_FOUND;
                    case 10 -> INFESTED_BLOCK;
                    case 11 -> BONEMEAL;
                    case 12 -> RAIL_PLACED;
                    case 13 -> REDSTONE_WIRE_PLACED;
                    case 14 -> WOOD_PLACED;
                    case 15 -> STONE_PLACED;
                    case 16 -> MINECRAFT;
                    case 17 -> RAILS_FOUND;
                    default -> UNDEFINED;
                };
    }
}
