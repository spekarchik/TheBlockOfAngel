package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.registries.RegistryObject;

public class ArmorRegistry
{
    public static final RegistryObject<ModArmor> RENDELITHIC_HELMET = Main.ITEMS.register("rendelithic_helmet", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, EquipmentSlot.HEAD, "rendelithic"));
    public static final RegistryObject<ModArmor> RENDELITHIC_CHESTPLATE = Main.ITEMS.register("rendelithic_chestplate", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, EquipmentSlot.CHEST, "rendelithic"));
    public static final RegistryObject<ModArmor> RENDELITHIC_LEGGINGS = Main.ITEMS.register("rendelithic_leggings", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, EquipmentSlot.LEGS, "rendelithic"));
    public static final RegistryObject<ModArmor> RENDELITHIC_BOOTS = Main.ITEMS.register("rendelithic_boots", () ->
            new RendelithicArmor(ModArmorMaterial.RENDELITHIC, EquipmentSlot.FEET, "rendelithic"));

    public static final RegistryObject<ModArmor> LAPIS_HELMET = Main.ITEMS.register("lapis_helmet", () ->
            new ModArmor(ModArmorMaterial.LAPIS, EquipmentSlot.HEAD, "lapis"));
    public static final RegistryObject<ModArmor> LAPIS_CHESTPLATE = Main.ITEMS.register("lapis_chestplate", () ->
            new ModArmor(ModArmorMaterial.LAPIS, EquipmentSlot.CHEST, "lapis"));
    public static final RegistryObject<ModArmor> LAPIS_LEGGINGS = Main.ITEMS.register("lapis_leggings", () ->
            new ModArmor(ModArmorMaterial.LAPIS, EquipmentSlot.LEGS, "lapis"));
    public static final RegistryObject<ModArmor> LAPIS_BOOTS = Main.ITEMS.register("lapis_boots", () ->
            new ModArmor(ModArmorMaterial.LAPIS, EquipmentSlot.FEET, "lapis"));

    public static final RegistryObject<ModArmor> LIMONITE_HELMET = Main.ITEMS.register("limonite_helmet", () ->
            new ModArmor(ModArmorMaterial.LIMONITE, EquipmentSlot.HEAD, "limonite"));
    public static final RegistryObject<ModArmor> LIMONITE_HELMET_WITH_DETECTOR = Main.ITEMS.register("limonite_helmet_with_detector", () ->
            new LimoniteArmorWithDetector(ModArmorMaterial.LIMONITE, EquipmentSlot.HEAD, "limonite"));
    public static final RegistryObject<ModArmor> LIMONITE_CHESTPLATE = Main.ITEMS.register("limonite_chestplate", () ->
            new ModArmor(ModArmorMaterial.LIMONITE, EquipmentSlot.CHEST, "limonite"));
    public static final RegistryObject<ModArmor> LIMONITE_LEGGINGS = Main.ITEMS.register("limonite_leggings", () ->
            new ModArmor(ModArmorMaterial.LIMONITE, EquipmentSlot.LEGS, "limonite"));
    public static final RegistryObject<ModArmor> LIMONITE_BOOTS = Main.ITEMS.register("limonite_boots", () ->
            new ModArmor(ModArmorMaterial.LIMONITE, EquipmentSlot.FEET, "limonite"));

    public static final RegistryObject<ModArmor> DIAMITHIC_HELMET = Main.ITEMS.register("diamithic_helmet", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, EquipmentSlot.HEAD, "diamithic"));
    public static final RegistryObject<ModArmor> DIAMITHIC_HELMET_WITH_DETECTOR = Main.ITEMS.register("diamithic_helmet_with_detector", () ->
            new DiamithicArmorWithDetector(ModArmorMaterial.DIAMITHIC, EquipmentSlot.HEAD, "diamithic"));
    public static final RegistryObject<ModArmor> DIAMITHIC_CHESTPLATE = Main.ITEMS.register("diamithic_chestplate", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, EquipmentSlot.CHEST, "diamithic"));
    public static final RegistryObject<ModArmor> DIAMITHIC_LEGGINGS = Main.ITEMS.register("diamithic_leggings", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, EquipmentSlot.LEGS, "diamithic"));
    public static final RegistryObject<ModArmor> DIAMITHIC_BOOTS = Main.ITEMS.register("diamithic_boots", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, EquipmentSlot.FEET, "diamithic"));

    public static final RegistryObject<ModArmor> SUPER_HELMET = Main.ITEMS.register("super_helmet", () ->
            new SuperArmor(ModArmorMaterial.SUPER, EquipmentSlot.HEAD, "super"));
    public static final RegistryObject<ModArmor> SUPER_HELMET_WITH_DETECTOR = Main.ITEMS.register("super_helmet_with_detector", () ->
            new SuperArmorWithDetector(ModArmorMaterial.SUPER, EquipmentSlot.HEAD, "super"));
    public static final RegistryObject<ModArmor> SUPER_CHESTPLATE = Main.ITEMS.register("super_chestplate", () ->
            new SuperArmor(ModArmorMaterial.SUPER, EquipmentSlot.CHEST, "super"));
    public static final RegistryObject<ModArmor> SUPER_CHESTPLATE_FLYING = Main.ITEMS.register("super_chestplate_flying", () ->
            new SuperArmorFlying(ModArmorMaterial.SUPER, EquipmentSlot.CHEST, "super"));
    public static final RegistryObject<ModArmor> SUPER_LEGGINGS = Main.ITEMS.register("super_leggings", () ->
            new SuperArmor(ModArmorMaterial.SUPER, EquipmentSlot.LEGS, "super"));
    public static final RegistryObject<ModArmor> SUPER_BOOTS = Main.ITEMS.register("super_boots", () ->
            new SuperArmor(ModArmorMaterial.SUPER, EquipmentSlot.FEET, "super"));

    public static final RegistryObject<ModArmor> FLYING_HELMET = Main.ITEMS.register("flying_helmet", () ->
            new FlyingArmor(ModArmorMaterial.FLYING, EquipmentSlot.HEAD, "flying"));
    public static final RegistryObject<ModArmor> FLYING_CHESTPLATE = Main.ITEMS.register("flying_chestplate", () ->
            new FlyingArmor(ModArmorMaterial.FLYING, EquipmentSlot.CHEST, "flying"));
    public static final RegistryObject<ModArmor> FLYING_LEGGINGS = Main.ITEMS.register("flying_leggings", () ->
            new FlyingArmor(ModArmorMaterial.FLYING, EquipmentSlot.LEGS, "flying"));
    public static final RegistryObject<ModArmor> FLYING_BOOTS = Main.ITEMS.register("flying_boots", () ->
            new FlyingArmor(ModArmorMaterial.FLYING, EquipmentSlot.FEET, "flying"));

    public static void initStatic()
    {
        // just to initialize static members
    }
}
