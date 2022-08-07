package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.events.effect.IArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.HashSet;
import java.util.Set;

abstract class Armor implements IArmor
{
    protected final IPlayer player;
    private final Set<EquipmentSlot> equipmentSlots = new HashSet<>();

    protected Armor(IPlayer player)
    {
        this.player = player;

        equipmentSlots.add(EquipmentSlot.FEET);
        equipmentSlots.add(EquipmentSlot.LEGS);
        equipmentSlots.add(EquipmentSlot.CHEST);
        equipmentSlots.add(EquipmentSlot.HEAD);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Armor)) return false;
        Armor armor = (Armor) obj;
        return getModelName().equals(armor.getModelName());
    }

    @Override
    public int hashCode()
    {
        return getModelName().hashCode();
    }

    protected void synchronizeEffect(IArmorEffect basicEffect, IArmorEffect dependentEffect)
    {
        if (basicEffect.isEffectOn() != dependentEffect.isEffectOn())
        {
            dependentEffect.invertSwitchState();
        }
    }

    protected void synchronizeEffectInversely(IArmorEffect basicEffect, IArmorEffect dependentEffect)
    {
        if (basicEffect.isEffectOn() == dependentEffect.isEffectOn())
        {
            dependentEffect.invertSwitchState();
        }
    }
}
