package com.pekar.angelblock.events.effect.base;

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

    <M extends IMob> IArmorEffectSetup<IArmorEffectWithOptions<M>, M> setupBasic();
    <E extends IArmorEffectWithOptions<M>, M extends IMob> IArmorEffectSetup<IArmorEffectWithOptions<M>, M> setupBasic(E effect);
    IPlayerArmorEffectSetup<IArmorEffectWithOptions<IPlayer>> setup();
    <E extends IArmorEffectWithOptions<IPlayer>> IPlayerArmorEffectSetup<E> setup(E effect);
}
