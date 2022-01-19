package com.pekar.angelblock.events.armor;

import java.util.Collection;

public interface IArmor extends IArmorEvents
{
    String getHelmetName();
    String getChestPlateName();
    String getLeggingsName();
    String getBootsName();
    Collection<String> getArmorElementNames();
    boolean isAnyArmorElementPutOn();
}
