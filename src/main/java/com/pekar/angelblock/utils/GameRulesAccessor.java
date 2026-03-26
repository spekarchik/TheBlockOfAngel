package com.pekar.angelblock.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gamerules.GameRule;

public class GameRulesAccessor
{
    private final ServerLevel level;

    private GameRulesAccessor(ServerLevel level)
    {
        this.level = level;
    }

    public static GameRulesAccessor of(ServerLevel level)
    {
        return new GameRulesAccessor(level);
    }

    public <T> T get(GameRule<T> gameRule)
    {
        return level.getGameRules().get(gameRule);
    }

    public <T> void set(GameRule<T> gameRule, T value)
    {
        level.getGameRules().set(gameRule, value, level.getServer());
    }
}
