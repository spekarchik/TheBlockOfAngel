package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

class TemporaryArmorEffect extends TemporaryBaseArmorEffect<ITemporaryArmorEffect> implements ITemporaryArmorEffect
{
    protected boolean isArmorEffect;

    protected TemporaryArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier, int defaultDuration)
    {
        super(player, armor, effectType, defaultAmplifier, defaultDuration);
    }

    @Override
    public void tryRemove()
    {
        super.tryRemove();
    }

    @Override
    public final boolean isArmorEffect()
    {
        return isArmorEffect;
    }

    @Override
    public final void onDurationEnd()
    {
        isArmorEffect = false;
        setState(State.OFF);
    }

    @Override
    public ITemporaryArmorEffect getSelf()
    {
        return this;
    }
}
