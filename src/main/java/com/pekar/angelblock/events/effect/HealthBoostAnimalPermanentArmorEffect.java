package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.events.armor.IAnimalArmor;
import com.pekar.angelblock.events.effect.base.PermanentAnimalArmorEffect;
import net.minecraft.world.effect.MobEffects;

public class HealthBoostAnimalPermanentArmorEffect extends PermanentAnimalArmorEffect
{
    public HealthBoostAnimalPermanentArmorEffect(IAnimal animal, IAnimalArmor armor, int amplifier)
    {
        super(animal, armor, MobEffects.HEALTH_BOOST, amplifier);
    }
}
