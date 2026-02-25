package com.pekar.angelblock.events.animal;

import com.pekar.angelblock.events.armor.IAnimalArmor;
import com.pekar.angelblock.events.mob.IMob;
import net.minecraft.world.entity.animal.Animal;

public interface IAnimal extends IMob
{
    Iterable<IAnimalArmor> getArmorTypesUsed();

    Animal getAnimalEntity();
    IAnimalArmor getArmor();
    void updateArmorUsed();
}
