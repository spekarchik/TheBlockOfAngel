package com.pekar.angelblock.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;

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

    public <T extends GameRules.Value<T>> T get(GameRules.Key<T> gameRule)
    {
        return level.getGameRules().getRule(gameRule);
    }

    public void set(GameRules.Key<GameRules.BooleanValue> gameRule, boolean value)
    {
        level.getGameRules().getRule(gameRule).set(value, level.getServer());
    }
}
