package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.mob.IMob;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.BiPredicate;

public interface IArmorEffectSetup<E extends IArmorEffectWithOptions<M>, M extends IMob>
{
    IArmorEffectSetup<E, M> setupAvailability(BiPredicate<M, IArmor> predicate);
    IArmorEffectSetup<E, M> alwaysAvailable();
    IArmorEffectSetup<E, M> unavailableIfNotModArmor(EquipmentSlot slot);

    IArmorEffectSetup<E, M> showIcon();
    IArmorEffectSetup<E, M> hideIcon();
}
