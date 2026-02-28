package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.events.armor.IAnimalArmor;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class PermanentAnimalArmorEffect extends PermanentArmorEffect<IAnimal, IAnimalArmor>
{
    protected PermanentAnimalArmorEffect(IAnimal mob, IAnimalArmor armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        super(mob, armor, effectType, defaultAmplifier);
    }
}
