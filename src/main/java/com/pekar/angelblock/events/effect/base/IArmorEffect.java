package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.animal.IAnimal;
import com.pekar.angelblock.events.armor.IAnimalArmor;
import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.mob.IMob;
import com.pekar.angelblock.events.player.IPlayer;

public interface IArmorEffect extends IArmorEffectBase
{
    boolean isActive();
    boolean isAnotherActive();
    boolean isAnyActive();
    boolean isAvailable();
    boolean isInfinite();
    void forceRemove();
    void onActivated();
    void onDeactivated();

    IArmorEffectSetup<IArmorEffectWithOptions<IAnimal, IAnimalArmor>, IAnimal, IAnimalArmor> setupAnimal();
    <E extends IArmorEffectWithOptions<IAnimal, IAnimalArmor>> IArmorEffectSetup<IArmorEffectWithOptions<IAnimal, IAnimalArmor>, IAnimal, IAnimalArmor> setupAnimal(E effect);
    IPlayerArmorEffectSetup<IArmorEffectWithOptions<IPlayer, IPlayerArmor>> setup();
    <E extends IArmorEffectWithOptions<IPlayer, IPlayerArmor>> IPlayerArmorEffectSetup<E> setup(E effect);
}
