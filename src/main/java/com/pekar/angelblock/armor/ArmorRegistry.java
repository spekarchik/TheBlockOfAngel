package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;

public class ArmorRegistry
{
    public static final DeferredItem<ModArmor> RENDELITHIC_HELMET = Main.ITEMS.registerItem("rendelithic_helmet", p ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, ArmorType.HELMET, p));
    public static final DeferredItem<ModArmor> RENDELITHIC_CHESTPLATE = Main.ITEMS.registerItem("rendelithic_chestplate", p ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, ArmorType.CHESTPLATE, p));
    public static final DeferredItem<ModArmor> RENDELITHIC_CHESTPLATE_WITH_LEVITATION = Main.ITEMS.registerItem("rendelithic_chestplate_with_levitation", p ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC2, ArmorType.CHESTPLATE, p).withSlowFalling());
    public static final DeferredItem<ModArmor> RENDELITHIC_LEGGINGS = Main.ITEMS.registerItem("rendelithic_leggings", p ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, ArmorType.LEGGINGS, p));
    public static final DeferredItem<ModArmor> RENDELITHIC_LEGGINGS_WITH_HEALTH_REGENERATOR = Main.ITEMS.registerItem("rendelithic_leggings_with_regenerator", p ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC2, ArmorType.LEGGINGS, p).withHealthRegenerator());
    public static final DeferredItem<ModArmor> RENDELITHIC_BOOTS = Main.ITEMS.registerItem("rendelithic_boots", p ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, ArmorType.BOOTS, p));
    public static final DeferredItem<ModArmor> RENDELITHIC_BOOTS_WITH_STRENGTH_BOOST = Main.ITEMS.registerItem("rendelithic_boots_with_strength_boost", p ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC2, ArmorType.BOOTS, p).withJumpBooster());

    public static final DeferredItem<ModArmor> LAPIS_HELMET = Main.ITEMS.registerItem("lapis_helmet", p ->
            new ModArmor(ModArmorMaterial.LAPIS, ArmorType.HELMET, p));
    public static final DeferredItem<ModArmor> LAPIS_HELMET_WITH_DETECTOR = Main.ITEMS.registerItem("lapis_helmet_with_detector", p ->
            new ModArmor(ModArmorMaterial.LAPIS2, ArmorType.HELMET, p).withDetector());
    public static final DeferredItem<ModArmor> LAPIS_CHESTPLATE = Main.ITEMS.registerItem("lapis_chestplate", p ->
            new ModArmor(ModArmorMaterial.LAPIS, ArmorType.CHESTPLATE, p));
    public static final DeferredItem<ModArmor> LAPIS_CHESTPLATE_WITH_STRENGTH = Main.ITEMS.registerItem("lapis_chestplate_with_strength", p ->
            new ModArmor(ModArmorMaterial.LAPIS2, ArmorType.CHESTPLATE, p).withStrengthBooster());
    public static final DeferredItem<ModArmor> LAPIS_LEGGINGS = Main.ITEMS.registerItem("lapis_leggings", p ->
            new ModArmor(ModArmorMaterial.LAPIS, ArmorType.LEGGINGS, p));
    public static final DeferredItem<ModArmor> LAPIS_LEGGINGS_WITH_REGENERATOR = Main.ITEMS.registerItem("lapis_leggings_with_regenerator", p ->
            new ModArmor(ModArmorMaterial.LAPIS2, ArmorType.LEGGINGS, p).withHealthRegenerator());
    public static final DeferredItem<ModArmor> LAPIS_BOOTS = Main.ITEMS.registerItem("lapis_boots", p ->
            new ModArmor(ModArmorMaterial.LAPIS, ArmorType.BOOTS, p));
    public static final DeferredItem<ModArmor> LAPIS_BOOTS_WITH_SEA_POWER = Main.ITEMS.registerItem("lapis_boots_with_sea_power", p ->
            new ModArmor(ModArmorMaterial.LAPIS2, ArmorType.BOOTS, p).withSeaPower());

    public static final DeferredItem<ModArmor> LIMONITE_HELMET = Main.ITEMS.registerItem("limonite_helmet", p ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE, ArmorType.HELMET, p));
    public static final DeferredItem<ModArmor> LIMONITE_HELMET_WITH_DETECTOR = Main.ITEMS.registerItem("limonite_helmet_with_detector", p ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE2, ArmorType.HELMET, p).withDetector());
    public static final DeferredItem<ModArmor> LIMONITE_CHESTPLATE = Main.ITEMS.registerItem("limonite_chestplate", p ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE, ArmorType.CHESTPLATE, p));
    public static final DeferredItem<ModArmor> LIMONITE_CHESTPLATE_WITH_LUCK = Main.ITEMS.registerItem("limonite_chestplate_with_luck", p ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE2, ArmorType.CHESTPLATE, p).withLuck());
    public static final DeferredItem<ModArmor> LIMONITE_LEGGINGS = Main.ITEMS.registerItem("limonite_leggings", p ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE, ArmorType.LEGGINGS, p));
    public static final DeferredItem<ModArmor> LIMONITE_LEGGINGS_WITH_REGENERATOR = Main.ITEMS.registerItem("limonite_leggings_with_regenerator", p ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE2, ArmorType.LEGGINGS, p).withHealthRegenerator());
    public static final DeferredItem<ModArmor> LIMONITE_BOOTS = Main.ITEMS.registerItem("limonite_boots", p ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE, ArmorType.BOOTS, p));
    public static final DeferredItem<ModArmor> LIMONITE_BOOTS_WITH_STRENGTH = Main.ITEMS.registerItem("limonite_boots_with_strength", p ->
            new LimoniteArmor(ModArmorMaterial.LIMONITE2, ArmorType.BOOTS, p).withJumpBooster());

    public static final DeferredItem<ModArmor> DIAMITHIC_HELMET = Main.ITEMS.registerItem("diamithic_helmet", p ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, ArmorType.HELMET, p));
    public static final DeferredItem<ModArmor> DIAMITHIC_HELMET_WITH_DETECTOR = Main.ITEMS.registerItem("diamithic_helmet_with_detector", p ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.HELMET, p).withDetector());
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE = Main.ITEMS.registerItem("diamithic_chestplate", p ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, ArmorType.CHESTPLATE, p));
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE_WITH_STRENGTH = Main.ITEMS.registerItem("diamithic_chestplate_with_strength", p ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE, p).withStrengthBooster());
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE_WITH_LEVITATION = Main.ITEMS.registerItem("diamithic_chestplate_with_levitation", p ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE, p).withSlowFalling());
    public static final DeferredItem<ModArmor> DIAMITHIC_CHESTPLATE_WITH_STRENGTH_AND_LEVITATION = Main.ITEMS.registerItem("diamithic_chestplate_with_strength_and_levitation", p ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.CHESTPLATE, p).withStrengthBooster().withSlowFalling());
    public static final DeferredItem<ModArmor> DIAMITHIC_LEGGINGS = Main.ITEMS.registerItem("diamithic_leggings", p ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, ArmorType.LEGGINGS, p));
    public static final DeferredItem<ModArmor> DIAMITHIC_LEGGINGS_WITH_REGENERATOR = Main.ITEMS.registerItem("diamithic_leggings_with_regenerator", p ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.LEGGINGS, p).withHealthRegenerator());
    public static final DeferredItem<ModArmor> DIAMITHIC_BOOTS = Main.ITEMS.registerItem("diamithic_boots", p ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, ArmorType.BOOTS, p));
    public static final DeferredItem<ModArmor> DIAMITHIC_BOOTS_WITH_STRENGTH = Main.ITEMS.registerItem("diamithic_boots_with_strength", p ->
            new ModArmor(ModArmorMaterial.DIAMITHIC2, ArmorType.BOOTS, p).withJumpBooster());

    public static final DeferredItem<ModArmor> SUPER_HELMET = Main.ITEMS.registerItem("super_helmet", p ->
            new SuperArmor(ModArmorMaterial.SUPER, ArmorType.HELMET, p));
    public static final DeferredItem<ModArmor> SUPER_HELMET_WITH_DETECTOR = Main.ITEMS.registerItem("super_helmet_with_detector", p ->
            new SuperArmor(ModArmorMaterial.SUPER2, ArmorType.HELMET, p).withDetector());
    public static final DeferredItem<ModArmor> SUPER_CHESTPLATE = Main.ITEMS.registerItem("super_chestplate", p ->
            new SuperArmor(ModArmorMaterial.SUPER, ArmorType.CHESTPLATE, p));
    public static final DeferredItem<ModArmor> SUPER_CHESTPLATE_FLYING = Main.ITEMS.registerItem("super_chestplate_flying", p ->
            new SuperArmorFlying(ModArmorMaterial.SUPER2, ArmorType.CHESTPLATE, p));
    public static final DeferredItem<ModArmor> SUPER_LEGGINGS = Main.ITEMS.registerItem("super_leggings", p ->
            new SuperArmor(ModArmorMaterial.SUPER, ArmorType.LEGGINGS, p));
    public static final DeferredItem<ModArmor> SUPER_LEGGINGS_WITH_REGENERATOR2 = Main.ITEMS.registerItem("super_leggings_with_regenerator", p ->
            new SuperArmor(ModArmorMaterial.SUPER2, ArmorType.LEGGINGS, p).withHealthRegenerator());
    public static final DeferredItem<ModArmor> SUPER_BOOTS = Main.ITEMS.registerItem("super_boots", p ->
            new SuperArmor(ModArmorMaterial.SUPER, ArmorType.BOOTS, p));
    public static final DeferredItem<ModArmor> SUPER_BOOTS_WITH_STRENGTH = Main.ITEMS.registerItem("super_boots_with_strength", p ->
            new SuperArmor(ModArmorMaterial.SUPER2, ArmorType.BOOTS, p).withJumpBooster());
    public static final DeferredItem<ModArmor> SUPER_BOOTS_WITH_SEA_POWER = Main.ITEMS.registerItem("super_boots_with_sea_power", p ->
            new SuperArmor(ModArmorMaterial.SUPER2, ArmorType.BOOTS, p).withSeaPower());
    public static final DeferredItem<ModArmor> SUPER_BOOTS_WITH_STRENGTH_AND_SEA_POWER = Main.ITEMS.registerItem("super_boots_with_strength_and_sea_power", p ->
            new SuperArmor(ModArmorMaterial.SUPER2, ArmorType.BOOTS, p).withSeaPower().withJumpBooster());

    public static final DeferredItem<ModArmor> FLYING_HELMET = Main.ITEMS.registerItem("flying_helmet", p ->
            new FlyingArmor(ModArmorMaterial.FLYING, ArmorType.HELMET, p));
    public static final DeferredItem<ModArmor> FLYING_CHESTPLATE = Main.ITEMS.registerItem("flying_chestplate", p ->
            new FlyingArmor(ModArmorMaterial.FLYING, ArmorType.CHESTPLATE, p));
    public static final DeferredItem<ModArmor> FLYING_LEGGINGS = Main.ITEMS.registerItem("flying_leggings", p ->
            new FlyingArmor(ModArmorMaterial.FLYING, ArmorType.LEGGINGS, p));
    public static final DeferredItem<ModArmor> FLYING_BOOTS = Main.ITEMS.registerItem("flying_boots", p ->
            new FlyingArmor(ModArmorMaterial.FLYING, ArmorType.BOOTS, p));

    public static void initStatic()
    {
        // just to initialize static members
    }
}
