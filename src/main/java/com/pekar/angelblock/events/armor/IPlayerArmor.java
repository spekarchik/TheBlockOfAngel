package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.PlayerArmorType;

public interface IPlayerArmor extends IArmor, IPlayerArmorEvents
{
    PlayerArmorType getArmorType();
    int getPriority();
}
