package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class HasteArmorEffect extends PermanentArmorEffect
{
    public HasteArmorEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.DIG_SPEED, 1);
        availableIfSlotSet(EquipmentSlot.CHEST);
    }
}
