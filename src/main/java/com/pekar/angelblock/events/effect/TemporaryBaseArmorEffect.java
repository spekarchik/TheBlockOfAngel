package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

abstract class TemporaryBaseArmorEffect<T extends IArmorEffect> extends ArmorEffect<T> implements ITemporaryBaseArmorEffect
{
    protected final int defaultDuration;

    public TemporaryBaseArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier, int defaultDuration)
    {
        super(player, armor, effectType, defaultAmplifier);
        this.defaultDuration = defaultDuration;
    }

    @Override
    public void tryActivate()
    {
        tryActivateInternal(defaultAmplifier, defaultDuration);
    }

    @Override
    public final void tryActivate(int duration)
    {
        tryActivateInternal(defaultAmplifier, duration);
    }

    @Override
    public void updateActivity()
    {
        super.updateActivity(defaultAmplifier, defaultDuration);
    }

    @Override
    public final Holder<MobEffect> getEffect()
    {
        return effectType;
    }
}
