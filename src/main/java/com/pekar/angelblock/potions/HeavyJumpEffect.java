package com.pekar.angelblock.potions;

import com.pekar.angelblock.Main;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HeavyJumpEffect extends MobEffect
{
    private static final ResourceLocation JUMP_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(Main.MODID, "heavy_jump_modifier");
    private static final ResourceLocation SLOW_MOTION_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(Main.MODID, "slow_motion_modifier");

    protected HeavyJumpEffect()
    {
        super(MobEffectCategory.HARMFUL, 0x5A4D41);

        this.addAttributeModifier(
                BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(Attributes.JUMP_STRENGTH.getKey()),
                JUMP_MODIFIER_ID,
                -0.15,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        this.addAttributeModifier(
                BuiltInRegistries.ATTRIBUTE.getHolderOrThrow(Attributes.MOVEMENT_SPEED.getKey()),
                SLOW_MOTION_MODIFIER_ID,
                -0.15,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean isInstantenous()
    {
        return false;
    }
}
