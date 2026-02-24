package com.pekar.angelblock.events;

import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.events.scheduler.PlayerScheduler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class TickEvents implements IEventHandler
{
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
    public void onPlayerTick(PlayerTickEvent.Post event)
    {
        if (event.getEntity() instanceof ServerPlayer serverPlayer)
        {
            IPlayer player = PlayerManager.instance().getPlayerByUUID(serverPlayer.getUUID());
            boolean runHeavy = player.every(11);

            for (IPlayerArmor armor : player.getArmorTypesUsed())
            {
                if (runHeavy)
                    armor.onCreeperCheck();

                if (serverPlayer.isInWater())
                {
                    armor.onBeingInWater();
                }
                else if (serverPlayer.isInWaterOrRain())
                {
                    armor.onBeingUnderRain();
                }
                else if (serverPlayer.isInLava())
                {
                    armor.onBeingInLava();
                }
            }
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

        IAnimal animal = AnimalManager.instance().getAnimalByUUID(animalEntity.getUUID());
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
}
