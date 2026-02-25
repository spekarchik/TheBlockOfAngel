package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.effect.base.PermanentAnimalArmorEffect;
import net.minecraft.world.effect.MobEffects;

public class HealthBoostAnimalPermanentArmorEffect extends PermanentAnimalArmorEffect
{
    public HealthBoostAnimalPermanentArmorEffect(IAnimal animal, IArmor armor, int amplifier)
    {
        super(animal, armor, MobEffects.HEALTH_BOOST, amplifier);
    }
}
