package com.pekar.angelblock.armor;

import com.pekar.angelblock.events.armor.*;
import com.pekar.angelblock.events.player.IPlayer;

import java.util.function.Function;

public enum PlayerArmorType
{
    RENDELITE(RendeliteArmorController::new),
    DIAMITE(DiamiteArmorController::new),
    AQUARITE(AquariteArmorController::new),
    LYMONITE(LymoniteArmorController::new),
    AERYTE(FlyingArmorController::new),
    SUPERYTE(SuperyteArmorController::new),
    OTHER(p -> {throw new UnsupportedOperationException();});

    private final Function<IPlayer, ? extends IPlayerArmor> armorBehaviorFactory;

    PlayerArmorType(Function<IPlayer, ? extends IPlayerArmor> armorBehaviorFactory)
    {
        this.armorBehaviorFactory = armorBehaviorFactory;
    }

    public IPlayerArmor createController(IPlayer player)
    {
        return armorBehaviorFactory.apply(player);
    }
}
