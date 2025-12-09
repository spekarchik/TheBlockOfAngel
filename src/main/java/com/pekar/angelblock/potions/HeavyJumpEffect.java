package com.pekar.angelblock.potions;

import com.pekar.angelblock.Main;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HeavyJumpEffect extends MobEffect
{
    private static final Identifier JUMP_MODIFIER_ID = Identifier.fromNamespaceAndPath(Main.MODID, "heavy_jump_modifier");
    private static final Identifier SLOW_MOTION_MODIFIER_ID = Identifier.fromNamespaceAndPath(Main.MODID, "slow_motion_modifier");

    protected HeavyJumpEffect()
    {
        super(MobEffectCategory.HARMFUL, 0x5A4D41);

        this.addAttributeModifier(
                Attributes.JUMP_STRENGTH,
                JUMP_MODIFIER_ID,
                -0.15,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        this.addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
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
