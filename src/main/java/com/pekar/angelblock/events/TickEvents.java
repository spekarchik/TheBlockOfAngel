package com.pekar.angelblock.events;

import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.scheduler.PlayerScheduler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TickEvents implements IEventHandler
{
    // Track ticks per animal UUID so we only run armor environment callbacks once every 5 ticks
    private static final Map<UUID, Integer> tickCounter = new ConcurrentHashMap<>();

    @SubscribeEvent
    public void onWorldTickEvent(LevelTickEvent.Post event)
    {
        var level = event.getLevel();
        if (level.isClientSide) return;

        Cleaner.decrementOrRemove();
//        LightCleaner.decrementOrRemove();
    }

    @SubscribeEvent
    public void onTick(PlayerTickEvent.Post event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
        {
            PlayerScheduler.doOnTick(player);
        }
    }

    @SubscribeEvent
    public void onLivingTick(EntityTickEvent.Post event)
    {
        if (!(event.getEntity() instanceof Animal animalEntity)) return;
        if (animalEntity.level().isClientSide()) return;

        boolean isTameAnimal = (animalEntity instanceof TamableAnimal tamable && tamable.isTame());
        boolean isTamedHorse = (animalEntity instanceof AbstractHorse horse && horse.isTamed());
        if (!isTameAnimal && !isTamedHorse) return;

        UUID uuid = animalEntity.getUUID();

        // initialize counter if missing
        if (!tickCounter.containsKey(uuid))
        {
            tickCounter.put(uuid, 0);
            return;
        }

        int count = tickCounter.get(uuid);
        if (count >= 4)
        {
            // reset before running to avoid re-entrancy issues
            tickCounter.put(uuid, 0);

            IAnimal animal = AnimalManager.instance().getAnimalByUUID(uuid);
            if (animal == null) return;

            for (var armor : animal.getArmorTypesUsed())
            {
                if (animalEntity.isInWater())
                    armor.onBeingInWater();
                else if (animalEntity.isInWaterOrRain())
                    armor.onBeingUnderRain();
                else if (animalEntity.isInLava())
                    armor.onBeingInLava();
                else
                    armor.onBeingInNormalEnvironment();
            }
        }
        else
        {
            tickCounter.put(uuid, count + 1);
        }
    }
}