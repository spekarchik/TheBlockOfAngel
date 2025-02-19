package com.pekar.angelblock.potions;

import com.pekar.angelblock.Main;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HeavyJumpEffect extends MobEffect
{
    private static final String JUMP_MODIFIER_ID = "heavy_jump_modifier";

    protected HeavyJumpEffect()
    {
        super(MobEffectCategory.HARMFUL, 0x5A4D41);

        this.addAttributeModifier(
                BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(Attributes.JUMP_STRENGTH.getKey()),
                ResourceLocation.fromNamespaceAndPath(Main.MODID, JUMP_MODIFIER_ID),
                -0.2,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        );
    }

    @Override
    public boolean isInstantenous()
    {
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier)
    {
        return duration <= 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier)
    {
        return true;
    }
}
