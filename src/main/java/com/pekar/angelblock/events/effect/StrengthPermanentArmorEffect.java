package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.PermanentPlayerArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class StrengthPermanentArmorEffect extends PermanentPlayerArmorEffect
{
    public StrengthPermanentArmorEffect(IPlayer player, IPlayerArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.DAMAGE_BOOST, amplifier);
        setup().availableIfSlotSet(EquipmentSlot.CHEST);
    }
}
