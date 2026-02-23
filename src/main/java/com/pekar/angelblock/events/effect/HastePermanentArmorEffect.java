package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.PermanentPlayerArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class HastePermanentArmorEffect extends PermanentPlayerArmorEffect
{
    public HastePermanentArmorEffect(IPlayer player, IPlayerArmor armor)
    {
        super(player, armor, MobEffects.HASTE, 1);
        setup().availableOnChestPlateWithStrengthBooster();
    }
}
