package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.TemporaryPlayerArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class WaterBreathingTemporaryEffect extends TemporaryPlayerArmorEffect
{
    public WaterBreathingTemporaryEffect(IPlayer player, IPlayerArmor armor, int defaultAmplifier, int duration)
    {
        super(player, armor, MobEffects.WATER_BREATHING, defaultAmplifier, duration);
        setup().availableIfSlotSet(EquipmentSlot.HEAD).showIcon();
    }
}
