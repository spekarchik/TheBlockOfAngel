package com.pekar.angelblock.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

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
        if (level instanceof ServerLevel serverLevel)
        {
            var weatherData = serverLevel.getWeatherData();
            weatherData.setRaining(false);
            weatherData.setThundering(false);
        }
    }

    public void rain()
    {
        if (level instanceof ServerLevel serverLevel)
        {
            var weatherData = serverLevel.getWeatherData();
            weatherData.setRaining(true);
            weatherData.setThundering(false);
            level.setRainLevel(0.3F);
            level.setThunderLevel(0);
            if (weatherData.getRainTime() == 0)
            {
                var weatherLasts = level.getRandom().nextIntBetweenInclusive(1200, 24000);
                weatherData.setRainTime(weatherLasts);
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
        if (level instanceof ServerLevel serverLevel)
        {
            var weatherData = serverLevel.getWeatherData();
            weatherData.setRaining(true);
            weatherData.setThundering(true);
            level.setThunderLevel(1.0F);
            level.setRainLevel(1.0F);
            if (weatherData.getRainTime() == 0 || weatherData.getThunderTime() == 0)
            {
                var weatherLasts = level.getRandom().nextIntBetweenInclusive(1200, 24000);
                weatherData.setRainTime(weatherLasts);
                weatherData.setThunderTime(weatherLasts);
            }
        }
        else
        {
            level.setThunderLevel(1.0F);
            level.setRainLevel(1.0F);
        }
    }
}
