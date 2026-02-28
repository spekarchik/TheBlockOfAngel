package com.pekar.angelblock.armor;

import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.events.armor.IAnimalArmor;
import com.pekar.angelblock.events.armor.LymoniteHorseArmorController;

import java.util.function.Function;

public enum AnimalArmorType
{
//    RENDELITE_WOLF(RendeliteWolfArmorController::new),
    LYMONITE_HORSE(LymoniteHorseArmorController::new),
    OTHER(a -> {throw new UnsupportedOperationException();});

    private final Function<IAnimal, ? extends IAnimalArmor> armorBehaviorFactory;

    AnimalArmorType(Function<IAnimal, ? extends IAnimalArmor> armorBehaviorFactory)
    {
        this.armorBehaviorFactory = armorBehaviorFactory;
    }

    public IAnimalArmor createController(IAnimal animal)
    {
        return armorBehaviorFactory.apply(animal);
    }
}
