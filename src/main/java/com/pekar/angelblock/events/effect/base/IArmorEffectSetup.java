package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.mob.IMob;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.BiPredicate;

public interface IArmorEffectSetup<E extends IArmorEffectWithOptions<M, A>, M extends IMob, A extends IArmor>
{
    IArmorEffectSetup<E, M, A> setupAvailability(BiPredicate<M, A> predicate);
    IArmorEffectSetup<E, M, A> alwaysAvailable();
    IArmorEffectSetup<E, M, A> unavailableIfNotModArmor(EquipmentSlot slot);

    IArmorEffectSetup<E, M, A> showIcon();
    IArmorEffectSetup<E, M, A> hideIcon();
}
