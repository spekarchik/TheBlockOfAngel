package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class SlowFallingSwitchingEffect extends SwitchingArmorEffect
{
    public SlowFallingSwitchingEffect(IPlayer player, IArmor armor)
    {
        super(player, armor, MobEffects.SLOW_FALLING, 0);
        availableIfSlotsSet(EquipmentSlot.FEET, EquipmentSlot.LEGS);
    }
}
