package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.SwitchingArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class StrengthSwitchingEffect extends SwitchingArmorEffect
{
    public StrengthSwitchingEffect(IPlayer player, IPlayerArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.STRENGTH, amplifier);
        setup().availableIfSlotSet(EquipmentSlot.CHEST);
    }
}
