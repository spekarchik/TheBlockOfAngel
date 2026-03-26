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
            if (levelData.getRainTime() == 0)
            {
                var weatherLasts = level.getRandom().nextIntBetweenInclusive(1200, 24000);
                levelData.setRainTime(weatherLasts);
            }
        }
    }

    public void thunder()
    {
        if (level.getLevelData() instanceof ServerLevelData levelData)
        {
            levelData.setRaining(true);
            levelData.setThundering(true);
            if (levelData.getRainTime() == 0 || levelData.getThunderTime() == 0)
            {
                var weatherLasts = level.getRandom().nextIntBetweenInclusive(1200, 24000);
                levelData.setRainTime(weatherLasts);
                levelData.setThunderTime(weatherLasts);
            }
        }
    }
}
