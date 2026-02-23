package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.SwitchingArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class LevitationSwitchingEffect extends SwitchingArmorEffect
{
    public LevitationSwitchingEffect(IPlayer player, IPlayerArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.LEVITATION, amplifier);
        setup().availableIfSlotSet(EquipmentSlot.FEET).showIcon();
    }
}
