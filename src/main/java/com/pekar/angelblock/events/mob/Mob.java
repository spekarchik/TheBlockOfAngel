package com.pekar.angelblock.events.mob;

import com.pekar.angelblock.events.effect.base.ITemporaryBaseArmorEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;

public abstract class Mob implements IMob
{
    private int tickCounter = 0;

    @Override
    public boolean every(int throttling)
    {
        tickCounter = (tickCounter + 1) % throttling;
        return tickCounter == 0;
    }

    public boolean isOverworld()
    {
        return getEntity().level().dimension().location().equals(Level.OVERWORLD.location());
    }

    public boolean isNether()
    {
        return getEntity().level().dimension().location().equals(Level.NETHER.location());
    }

    public boolean isEnd()
    {
        return getEntity().level().dimension().location().equals(Level.END.location());
    }

    public boolean isEffectActive(Holder<MobEffect> effect)
    {
        return getEntity().hasEffect(effect);
    }

    public boolean hasArmorEffect(Holder<MobEffect> effect)
    {
        var effectInstance = getEntity().getEffect(effect);
        if (effectInstance instanceof ModMobEffectInstance modMobEffectInstance && modMobEffectInstance.isMagicItemEffect())
        {
            return false;
        }

        return effectInstance != null && (!effectInstance.isVisible() || effectInstance.isInfiniteDuration());
    }

    public boolean hasAnotherEffect(Holder<MobEffect> effect)
    {
        var effectInstance = getEntity().getEffect(effect);
        return effectInstance != null && effectInstance.isVisible();
    }

    public IModMobEffectInstance setEffect(Holder<MobEffect> effect, int amplifier)
    {
        return setEffect(effect, MobEffectInstance.INFINITE_DURATION, amplifier);
    }

    public IModMobEffectInstance setEffect(Holder<MobEffect> effect, int amplifier, boolean showIcon)
    {
        return setEffect(effect, MobEffectInstance.INFINITE_DURATION, amplifier, showIcon);
    }

    public IModMobEffectInstance setEffect(Holder<MobEffect> effect, int duration, int amplifier)
    {
        return setEffect(effect, duration, amplifier, false);
    }

    public IModMobEffectInstance setEffect(Holder<MobEffect> effect, int duration, int amplifier, boolean showIcon)
    {
        var effectInstance = new ModMobEffectInstance(effect, duration, amplifier, false /*ambient*/, false /*visible*/, showIcon, false);
        getEntity().addEffect(effectInstance);
        return effectInstance;
    }

    public IModMobEffectInstance setMagicItemEffect(Holder<MobEffect> effect, int duration, int amplifier, boolean showIcon)
    {
        var effectInstance = new ModMobEffectInstance(effect, duration, amplifier, true /*ambient*/, true /*visible*/, showIcon, true);
        getEntity().addEffect(effectInstance);
        return effectInstance;
    }

    public IModMobEffectInstance setEffect(ITemporaryBaseArmorEffect armorEffect, int duration, int amplifier)
    {
        return setEffect(armorEffect, duration, amplifier, false);
    }

    public IModMobEffectInstance setEffect(ITemporaryBaseArmorEffect armorEffect, int duration, int amplifier, boolean showIcon)
    {
        var effectInstance = new ModMobEffectInstance(armorEffect.getEffect(), duration, amplifier, false /*ambient*/, false /*visible*/, showIcon /*showIcon*/,
                false, armorEffect::onDurationEnd);
        getEntity().addEffect(effectInstance);
        return effectInstance;
    }

    public MobEffectInstance getEffectInstance(Holder<MobEffect> effect)
    {
        return getEntity().getEffect(effect);
    }

    public void clearEffect(Holder<MobEffect> effect)
    {
        getEntity().removeEffect(effect);
    }
}
