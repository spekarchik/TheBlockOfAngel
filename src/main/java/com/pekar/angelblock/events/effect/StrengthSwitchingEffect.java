package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class StrengthSwitchingEffect extends SwitchingArmorEffect
{
    public StrengthSwitchingEffect(IPlayer player, IArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.STRENGTH, amplifier);
        availableIfSlotSet(EquipmentSlot.CHEST);
    }
}
