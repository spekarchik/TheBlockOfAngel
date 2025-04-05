package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class HasteSwitchingEffect extends SwitchingArmorEffect
{
    public HasteSwitchingEffect(IPlayer player, IArmor armor, int amplifier)
    {
        super(player, armor, MobEffects.HASTE, amplifier);
        availableOnChestPlateWithStrengthBooster();
    }
}
