package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.world.effect.MobEffects;

public class JumpNegativeArmorEffect extends NegativeTemporaryArmorEffect
{
    public JumpNegativeArmorEffect(IPlayer player, IArmor armor, int duration)
    {
        super(player, armor, PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT, 0, duration);
        showIcon();
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
}
