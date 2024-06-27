package com.pekar.angelblock.potions;

import com.pekar.angelblock.Main;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class PotionRegistry
{
    // Mob Effects
    public static final Holder<MobEffect> BLOCK_BREAKER_EFFECT = register("block_breaker_effect",
            new BlockBreakerMobEffect(MobEffectCategory.NEUTRAL, 0xFF22FF));

    public static final Holder<MobEffect> SWORD_FIRE_MODE_EFFECT = register("sword_fire_mode_effect", new SwordFireModeMobEffect());

    public static final Holder<MobEffect> SWORD_EXPLOSION_MODE_EFFECT = register("sword_explosion_mode_effect", new SwordExplosionModeMobEffect());

    public static final Holder<MobEffect> SWORD_WEB_MODE_EFFECT = register("sword_web_mode_effect", new SwordWebModeMobEffect());

    public static final Holder<MobEffect> TOOL_ADVANCED_MODE_EFFECT = register("tool_advanced_mode_effect", new ToolAdvancedModeMobEffect());

    public static final Holder<MobEffect> ROD_MAGNETIC_MODE_EFFECT = register("rod_magnetic_mode_effect", new RodMagneticModeEffect());

    public static final Holder<MobEffect> ARMOR_SUPER_JUMP_MODE_EFFECT = register("armor_super_jump_mode_effect", new SuperJumpModeEffect());


    // Potions
    public static final Holder<Potion> BLOCK_BREAKER_POTION = register("block_breaker_potion", new Potion(new MobEffectInstance(BLOCK_BREAKER_EFFECT)));


    public static void initStatic()
    {
        // just to initialize static members
    }

    private static Holder<Potion> register(String name, Potion potion)
    {
        return Registry.registerForHolder(BuiltInRegistries.POTION, ResourceLocation.tryBuild(Main.MODID, name), potion);
    }

    private static Holder<MobEffect> register(String name, MobEffect mobEffect)
    {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.tryBuild(Main.MODID, name), mobEffect);
    }

}
