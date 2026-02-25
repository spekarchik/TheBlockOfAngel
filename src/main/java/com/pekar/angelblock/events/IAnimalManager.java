package com.pekar.angelblock.events;

import com.pekar.angelblock.events.animal.IAnimal;

import java.util.UUID;

public interface IAnimalManager
{
    IAnimal getAnimalByUUID(UUID uuid);
}
