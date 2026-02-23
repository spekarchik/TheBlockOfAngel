package com.pekar.angelblock.events.effect.base;

import com.pekar.angelblock.armor.ModArmor;
import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.mob.IMob;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.function.BiPredicate;

public class ArmorEffectSetup<E extends IArmorEffectWithOptions<M>, M extends IMob> implements IArmorEffectSetup<E, M>
{
    protected final E effect;

    public ArmorEffectSetup(E effect)
    {
        this.effect = effect;
    }

    @Override
    public IArmorEffectSetup<E, M> setupAvailability(BiPredicate<M, IArmor> predicate)
    {
        effect.setupAvailability(predicate);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, M> alwaysAvailable()
    {
        effect.setupAvailability((mob, armor) -> true);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, M> unavailableIfNotModArmor(EquipmentSlot slot)
    {
        effect.setupAvailability((mob, armor) ->
        {
            var slotItemStack = mob.getEntity().getItemBySlot(slot);
            return !slotItemStack.isEmpty() && slotItemStack.getItem() instanceof ModArmor;
        });

        return this;
    }

    @Override
    public IArmorEffectSetup<E, M> showIcon()
    {
        effect.setShowIcon(true);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, M> hideIcon()
    {
        effect.setShowIcon(false);
        return this;
    }
}
