package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.events.armor.IArmor;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class PermanentAnimalArmorEffect extends PermanentArmorEffect<IAnimal>
{
    protected PermanentAnimalArmorEffect(IAnimal mob, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier)
    {
        super(mob, armor, effectType, defaultAmplifier);
    }
}
