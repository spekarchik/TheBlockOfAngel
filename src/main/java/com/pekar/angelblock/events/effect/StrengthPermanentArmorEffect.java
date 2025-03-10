package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class StrengthPermanentArmorEffect extends PermanentArmorEffect
{
    public StrengthPermanentArmorEffect(IPlayer player, IArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.DAMAGE_BOOST, amplifier);
        availableIfSlotSet(EquipmentSlot.CHEST);
    }
}
