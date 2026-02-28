package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.AnimalArmorType;

public interface IAnimalArmor extends IArmor, IAnimalArmorEvents
{
    AnimalArmorType getArmorType();
}
