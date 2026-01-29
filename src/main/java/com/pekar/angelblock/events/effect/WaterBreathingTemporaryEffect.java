package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class WaterBreathingTemporaryEffect extends TemporaryArmorEffect
{
    public WaterBreathingTemporaryEffect(IPlayer player, IArmor armor, int defaultAmplifier, int duration)
    {
        super(player, armor, MobEffects.WATER_BREATHING, defaultAmplifier, duration);
        availableIfSlotSet(EquipmentSlot.HEAD);
        showIcon();
    }
}
