package com.pekar.angelblock.potions;

import com.pekar.angelblock.Main;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import java.util.function.Supplier;

public class PotionRegistry
{
    // Mob Effects
    public static final Holder<MobEffect> BLOCK_BREAKER_EFFECT = registerMobEffect("block_breaker_effect",
            () -> new BlockBreakerMobEffect(MobEffectCategory.NEUTRAL, 0xFF22FF));

    public static final Holder<MobEffect> SWORD_FIRE_MODE_EFFECT = registerMobEffect("sword_fire_mode_effect", SwordFireModeMobEffect::new);

    public static final Holder<MobEffect> SWORD_EXPLOSION_MODE_EFFECT = registerMobEffect("sword_explosion_mode_effect", SwordExplosionModeMobEffect::new);

    public static final Holder<MobEffect> SWORD_WEB_MODE_EFFECT = registerMobEffect("sword_web_mode_effect", SwordWebModeMobEffect::new);

    public static final Holder<MobEffect> TOOL_ADVANCED_MODE_EFFECT = registerMobEffect("tool_advanced_mode_effect", ToolAdvancedModeMobEffect::new);

    public static final Holder<MobEffect> ROD_MAGNETIC_MODE_EFFECT = registerMobEffect("rod_magnetic_mode_effect", RodMagneticModeEffect::new);

    public static final Holder<MobEffect> ARMOR_SUPER_JUMP_MODE_EFFECT = registerMobEffect("armor_super_jump_mode_effect", SuperJumpModeEffect::new);

    public static final Holder<MobEffect> ARMOR_HEAVY_JUMP_EFFECT = registerMobEffect("armor_heavy_jump_effect", HeavyJumpEffect::new);


    // Potions
    public static final Holder<Potion> BLOCK_BREAKER_POTION = registerPotion("block_breaker_potion", () -> new Potion(new MobEffectInstance[]{new MobEffectInstance(BLOCK_BREAKER_EFFECT)}));


    public static void initStatic()
    {
        // just to initialize static members
    }

    private static Holder<Potion> registerPotion(String name, Supplier<Potion> potion)
    {
        return Main.POTIONS.register(name, potion);
        //return Registry.registerForHolder(BuiltInRegistries.POTION, Objects.requireNonNull(ResourceLocation.tryBuild(Main.MODID, name)), potion);
    }

    private static Holder<MobEffect> registerMobEffect(String name, Supplier<MobEffect> mobEffect)
    {
        return Main.MOB_EFFECTS.register(name, mobEffect);
        //return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Objects.requireNonNull(ResourceLocation.tryBuild(Main.MODID, name)), mobEffect);
    }

}
