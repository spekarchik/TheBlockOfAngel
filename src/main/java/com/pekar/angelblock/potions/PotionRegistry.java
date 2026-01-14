package com.pekar.angelblock.potions;

import com.pekar.angelblock.Main;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class PotionRegistry
{
    // Mob Effects
    public static final Holder<MobEffect> SWORD_FIRE_MODE_EFFECT = registerMobEffect("sword_fire_mode_effect", SwordFireModeMobEffect::new);

    public static final Holder<MobEffect> SWORD_EXPLOSION_MODE_EFFECT = registerMobEffect("sword_explosion_mode_effect", SwordExplosionModeMobEffect::new);

    public static final Holder<MobEffect> SWORD_WEB_MODE_EFFECT = registerMobEffect("sword_web_mode_effect", SwordWebModeMobEffect::new);

    public static final Holder<MobEffect> TOOL_ADVANCED_MODE_EFFECT = registerMobEffect("tool_advanced_mode_effect", ToolAdvancedModeMobEffect::new);

    public static final Holder<MobEffect> ROD_MAGNETIC_MODE_EFFECT = registerMobEffect("rod_magnetic_mode_effect", RodMagneticModeEffect::new);

    public static final Holder<MobEffect> ARMOR_SUPER_JUMP_MODE_EFFECT = registerMobEffect("armor_super_jump_mode_effect", SuperJumpModeEffect::new);

    public static final Holder<MobEffect> ARMOR_HEAVY_JUMP_EFFECT = registerMobEffect("armor_heavy_jump_effect", HeavyJumpEffect::new);

    public static final Holder<MobEffect> BIOS_DIAMOND_COOLDOWN_EFFECT = registerMobEffect("bios_diamond_cooldown_effect", CooldownEffect::new);

    public static final Holder<MobEffect> END_SAPPHIRE_COOLDOWN_EFFECT = registerMobEffect("end_sapphire_cooldown_effect", CooldownEffect::new);

    public static final Holder<MobEffect> ELDER_GUARDIAN_EYE_EFFECT = registerMobEffect("elder_guardian_eye_effect", ElderGuardianEyeEffect::new);

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownPotion>> BLOCK_BREAKER_POTION =
            registerThrownPotion("block_breaker_potion", BlockBreakerPotion::new);


    public static void initStatic()
    {
        // just to initialize static members
    }

    private static Holder<Potion> registerPotion(String name, Supplier<Potion> potion)
    {
        return Main.POTIONS.register(name, potion);
    }

    private static Holder<MobEffect> registerMobEffect(String name, Supplier<MobEffect> mobEffect)
    {
        return Main.MOB_EFFECTS.register(name, mobEffect);
    }

    private static DeferredHolder<EntityType<?>, EntityType<ThrownPotion>> registerThrownPotion(String name, EntityType.EntityFactory<ThrownPotion> potionFactory)
    {
        return Main.ENTITY_TYPES.register(name, () -> EntityType.Builder.of(potionFactory, MobCategory.MISC)
                .sized(0.25F, 0.25F) // Entity size
                .build(name));
    }
}
