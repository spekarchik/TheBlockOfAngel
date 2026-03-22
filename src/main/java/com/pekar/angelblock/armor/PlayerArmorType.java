package com.pekar.angelblock.armor;

import com.pekar.angelblock.events.armor.*;
import com.pekar.angelblock.events.player.IPlayer;

import java.util.function.Function;

public enum PlayerArmorType
{
    RENDELITE(RendeliteArmorController::new, ModArmorMaterial.RENDELITHIC),
    DIAMITE(DiamiteArmorController::new, ModArmorMaterial.DIAMITHIC),
    AQUARITE(AquariteArmorController::new, ModArmorMaterial.LAPIS),
    LYMONITE(LymoniteArmorController::new, ModArmorMaterial.LIMONITE),
    AERYTE(FlyingArmorController::new, ModArmorMaterial.FLYING),
    SUPERYTE(SuperyteArmorController::new, ModArmorMaterial.SUPER),
    OTHER(p -> {throw new UnsupportedOperationException();}, null);

    private final Function<IPlayer, ? extends IPlayerArmor> armorControllerFactory;
    private final ModArmorMaterial material;

    PlayerArmorType(Function<IPlayer, ? extends IPlayerArmor> armorControllerFactory, ModArmorMaterial material)
    {
        this.armorControllerFactory = armorControllerFactory;
        this.material = material;
    }

    public IPlayerArmor createController(IPlayer player)
    {
        return armorControllerFactory.apply(player);
    }

    public ModArmorMaterial getMaterial()
    {
        return material;
    }
}
