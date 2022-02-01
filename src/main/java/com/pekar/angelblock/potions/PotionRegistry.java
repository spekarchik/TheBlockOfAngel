package com.pekar.angelblock.potions;

import com.pekar.angelblock.Main;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.RegistryObject;

public class PotionRegistry
{
    // Mob Effects
    public static final RegistryObject<MobEffect> BLOCK_BREAKER_EFFECT = Main.MOB_EFFECTS.register("block_breaker_effect",
            () -> new BlockBreakerMobEffect(MobEffectCategory.NEUTRAL, 0xFF22FF));

    public static final RegistryObject<MobEffect> SWORD_FIRE_MODE_EFFECT = Main.MOB_EFFECTS.register("sword_fire_mode_effect",
            () -> new SwordFireModeMobEffect());

    public static final RegistryObject<MobEffect> SWORD_EXPLOSION_MODE_EFFECT = Main.MOB_EFFECTS.register("sword_explosion_mode_effect",
            () -> new SwordExplosionModeMobEffect());

    public static final RegistryObject<MobEffect> SWORD_WEB_MODE_EFFECT = Main.MOB_EFFECTS.register("sword_web_mode_effect",
            () -> new SwordWebModeMobEffect());

    public static final RegistryObject<MobEffect> TOOL_ADVANCED_MODE_EFFECT = Main.MOB_EFFECTS.register("tool_advanced_mode_effect",
        () -> new ToolAdvancedModeMobEffect());


    // Potions
    public static final RegistryObject<Potion> BLOCK_BREAKER_POTION = Main.POTIONS.register("block_breaker_potion",
            () -> new Potion(new MobEffectInstance(BLOCK_BREAKER_EFFECT.get())));


    public static void initStatic()
    {
        // just to initialize static members
    }
}
