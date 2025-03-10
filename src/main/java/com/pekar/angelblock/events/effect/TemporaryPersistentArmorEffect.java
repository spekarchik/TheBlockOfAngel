package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IArmor;
import com.pekar.angelblock.events.player.IPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

class TemporaryPersistentArmorEffect extends TemporaryBaseArmorEffect<ITemporaryPersistentArmorEffect> implements ITemporaryPersistentArmorEffect
{
    protected TemporaryPersistentArmorEffect(IPlayer player, IArmor armor, Holder<MobEffect> effectType, int defaultAmplifier, int defaultDuration)
    {
        super(player, armor, effectType, defaultAmplifier, defaultDuration);
    }

    @Override
    protected boolean isOn()
    {
        return true;
    }

    @Override
    protected void setEffect(int amplifier, int duration)
    {
        player.setEffect(effectType, duration, amplifier, getShowIcon());
    }

    @Override
    protected boolean canClearEffect()
    {
        return false;
    }

    @Override
    protected boolean shouldPersist()
    {
        return true;
    }

    @Override
    public void updateActivity()
    {
        // ignore: super.updateActivity(p1, p2) should only be called from tryActivate()
    }

    @Override
    public ITemporaryPersistentArmorEffect getSelf()
    {
        return this;
    }
}
