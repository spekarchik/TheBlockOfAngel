package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class NauseaTemporaryEffect extends TemporaryArmorEffect
{
    public NauseaTemporaryEffect(IPlayer player, IArmor armor, int duration)
    {
        super(player, armor, MobEffects.CONFUSION, 12, duration);
    }

    @Override
    public boolean trySwitch(int amplifier)
    {
        if (player.isEffectActive(effectType)) return false;
        return super.trySwitch(amplifier);
    }
}
