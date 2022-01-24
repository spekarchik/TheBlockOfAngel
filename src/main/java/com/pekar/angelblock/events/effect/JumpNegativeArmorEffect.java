package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.world.effect.MobEffects;

public class JumpNegativeArmorEffect extends TemporaryArmorEffect
{
    public JumpNegativeArmorEffect(IPlayer player, IArmor armor, int defaultAmplifier, int duration)
    {
        super(player, armor, MobEffects.JUMP, defaultAmplifier, duration);
    }

    @Override
    public boolean trySwitch(int amplifier)
    {
        if (isActive())
        {
            player.clearEffect(effectType);
        }
        return super.trySwitch(amplifier);
    }

    @Override
    protected boolean canClearEffect()
    {
        return false;
    }

    @Override
    protected boolean isAvailable()
    {
        return player.isFullArmorSetPutOn(armor.getArmorElementNames());
    }
}
