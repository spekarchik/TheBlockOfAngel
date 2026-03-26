package com.pekar.angelblock.utils;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;

public class Weather
{
    private final Level level;

    private Weather(Level level)
    {
        this.level = level;
    }

    public static Weather of(Level level)
    {
        return new Weather(level);
    }

    public void clear()
    {
        if (level.getLevelData() instanceof ServerLevelData levelData)
        {
            levelData.setRaining(false);
            levelData.setThundering(false);
        }
        else
        {
            level.getLevelData().setRaining(false);
        }
    }

    public void rain()
    {
        if (level.getLevelData() instanceof ServerLevelData levelData)
        {
            levelData.setRaining(true);
            levelData.setThundering(false);
            level.setRainLevel(0.3F);
            level.setThunderLevel(0);
            if (levelData.getRainTime() == 0)
            {
                var weatherLasts = level.getRandom().nextIntBetweenInclusive(1200, 24000);
                levelData.setRainTime(weatherLasts);
            }
        }
        else
        {
            level.setRainLevel(0.3F);
            level.setThunderLevel(0);
        }
    }

    public void thunder()
    {
        if (level.getLevelData() instanceof ServerLevelData levelData)
        {
            levelData.setRaining(true);
            levelData.setThundering(true);
            level.setThunderLevel(1.0F);
            level.setRainLevel(1.0F);
            if (levelData.getRainTime() == 0 || levelData.getThunderTime() == 0)
            {
                var weatherLasts = level.getRandom().nextIntBetweenInclusive(1200, 24000);
                levelData.setRainTime(weatherLasts);
                levelData.setThunderTime(weatherLasts);
            }
        }
        else
        {
            level.setThunderLevel(1.0F);
            level.setRainLevel(1.0F);
        }
    }
}
