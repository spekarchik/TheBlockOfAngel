package com.pekar.angelblock.events.effect;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public interface ITemporaryBaseArmorEffect
{
    void tryActivate();
    void tryActivate(int duration);
    void tryActivate(int amplifier, int duration);
    void onDurationEnd();
    Holder<MobEffect> getEffect();
}
