package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.PermanentPlayerArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class WaterBreathingPermanentEffect extends PermanentPlayerArmorEffect
{
    public WaterBreathingPermanentEffect(IPlayer player, IPlayerArmor armor)
    {
        super(player, armor, MobEffects.WATER_BREATHING, 0);
        setup().availableIfSlotSet(EquipmentSlot.HEAD);
    }
}
