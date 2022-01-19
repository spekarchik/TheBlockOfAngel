package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.events.player.IPlayer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

abstract class Armor implements IArmor
{
    protected final IPlayer player;
    private final Set<String> elementNames = new HashSet<>();

    protected Armor(IPlayer player)
    {
        this.player = player;

        elementNames.add(getBootsName());
        elementNames.add(getHelmetName());
        elementNames.add(getLeggingsName());
        elementNames.add(getChestPlateName());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Armor)) return false;
        Armor armor = (Armor) obj;
        return getBootsName().equals(armor.getBootsName());
    }

    @Override
    public int hashCode()
    {
        return getBootsName().hashCode();
    }

    @Override
    public Collection<String> getArmorElementNames()
    {
        return elementNames;
    }

    @Override
    public final boolean isAnyArmorElementPutOn()
    {
        return player.isAnyArmorElementPutOn(elementNames);
    }

//    protected void synchronizeEffect(IArmorEffect basicEffect, IArmorEffect dependentEffect)
//    {
//        if (basicEffect.isEffectOn() != dependentEffect.isEffectOn())
//        {
//            dependentEffect.invertSwitchState();
//        }
//    }
//
//    protected void synchronizeEffectInversely(IArmorEffect basicEffect, IArmorEffect dependentEffect)
//    {
//        if (basicEffect.isEffectOn() == dependentEffect.isEffectOn())
//        {
//            dependentEffect.invertSwitchState();
//        }
//    }

    protected void message(String message)
    {
        player.sendMessage(message);
    }
}
