package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class WaterBreathingPermanentEffect extends PermanentArmorEffect
{
    public WaterBreathingPermanentEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.WATER_BREATHING, 0);
        availableIfSlotSet(EquipmentSlot.HEAD);
    }
}
