package com.pekar.angelblock.events.mob;

import com.pekar.angelblock.events.effect.base.ITemporaryBaseArmorEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public interface IMob
{
    LivingEntity getEntity();

    boolean every(int throttling);

    boolean isOverworld();
    boolean isNether();
    boolean isEnd();

    boolean isEffectActive(Holder<MobEffect> effect);
    boolean hasArmorEffect(Holder<MobEffect> effect);
    boolean hasAnotherEffect(Holder<MobEffect> effect);

    IModMobEffectInstance setEffect(Holder<MobEffect> effect, int amplifier);
    IModMobEffectInstance setEffect(Holder<MobEffect> effect, int amplifier, boolean showIcon);
    IModMobEffectInstance setEffect(Holder<MobEffect> effect, int duration, int amplifier);
    IModMobEffectInstance setEffect(Holder<MobEffect> effect, int duration, int amplifier, boolean showIcon);
    IModMobEffectInstance setMagicItemEffect(Holder<MobEffect> effect, int duration, int amplifier, boolean showIcon);
    IModMobEffectInstance setEffect(ITemporaryBaseArmorEffect armorEffect, int duration, int amplifier);
    IModMobEffectInstance setEffect(ITemporaryBaseArmorEffect armorEffect, int duration, int amplifier, boolean showIcon);
    MobEffectInstance getEffectInstance(Holder<MobEffect> effect);
    void clearEffect(Holder<MobEffect> effect);
}
