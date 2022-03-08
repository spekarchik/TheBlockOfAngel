package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class DolphinsGraceEffect extends SwitchingArmorEffect
{
    public DolphinsGraceEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.DOLPHINS_GRACE, 0);
        availableIfSlotSet(EquipmentSlot.FEET);
        showIcon = true;
    }
}
