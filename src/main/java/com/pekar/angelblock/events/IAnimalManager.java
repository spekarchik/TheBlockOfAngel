package com.pekar.angelblock.events;

import com.pekar.angelblock.events.animal.IAnimal;
import net.minecraft.world.entity.animal.Animal;

import java.util.UUID;

public interface IAnimalManager
{
    IAnimal getAnimalByUUID(UUID uuid);
    void addAnimal(Animal animal);
}
