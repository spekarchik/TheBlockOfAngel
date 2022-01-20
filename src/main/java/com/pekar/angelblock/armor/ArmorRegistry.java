package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.registries.RegistryObject;

public class ArmorRegistry
{
    public static final RegistryObject<ArmorItem> RENDELITHIC_HELMET = ModArmor.register("rendelithic_helmet", () ->
            new ModArmor(ModArmorMaterial.RENDELITHIC, EquipmentSlot.HEAD));
    public static final RegistryObject<ArmorItem> RENDELITHIC_CHESTPLATE = ModArmor.register("rendelithic_chestplate", () ->
            new ModArmor(ModArmorMaterial.RENDELITHIC, EquipmentSlot.CHEST));
    public static final RegistryObject<ArmorItem> RENDELITHIC_LIGGINGS = ModArmor.register("rendelithic_leggings", () ->
            new ModArmor(ModArmorMaterial.RENDELITHIC, EquipmentSlot.LEGS));
    public static final RegistryObject<ArmorItem> RENDELITHIC_BOOTS = ModArmor.register("rendelithic_boots", () ->
            new ModArmor(ModArmorMaterial.RENDELITHIC, EquipmentSlot.FEET));

    public static final RegistryObject<ArmorItem> LAPIS_HELMET = ModArmor.register("lapis_helmet", () ->
            new ModArmor(ModArmorMaterial.LAPIS, EquipmentSlot.HEAD));
    public static final RegistryObject<ArmorItem> LAPIS_CHESTPLATE = ModArmor.register("lapis_chestplate", () ->
            new ModArmor(ModArmorMaterial.LAPIS, EquipmentSlot.CHEST));
    public static final RegistryObject<ArmorItem> LAPIS_LIGGINGS = ModArmor.register("lapis_leggings", () ->
            new ModArmor(ModArmorMaterial.LAPIS, EquipmentSlot.LEGS));
    public static final RegistryObject<ArmorItem> LAPIS_BOOTS = ModArmor.register("lapis_boots", () ->
            new ModArmor(ModArmorMaterial.LAPIS, EquipmentSlot.FEET));

    public static final RegistryObject<ArmorItem> LIMONITE_HELMET = ModArmor.register("limonite_helmet", () ->
            new ModArmor(ModArmorMaterial.LIMONITE, EquipmentSlot.HEAD));
    public static final RegistryObject<ArmorItem> LIMONITE_CHESTPLATE = ModArmor.register("limonite_chestplate", () ->
            new ModArmor(ModArmorMaterial.LIMONITE, EquipmentSlot.CHEST));
    public static final RegistryObject<ArmorItem> LIMONITE_LIGGINGS = ModArmor.register("limonite_leggings", () ->
            new ModArmor(ModArmorMaterial.LIMONITE, EquipmentSlot.LEGS));
    public static final RegistryObject<ArmorItem> LIMONITE_BOOTS = ModArmor.register("limonite_boots", () ->
            new ModArmor(ModArmorMaterial.LIMONITE, EquipmentSlot.FEET));

    public static final RegistryObject<ArmorItem> DIAMITHIC_HELMET = ModArmor.register("diamithic_helmet", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, EquipmentSlot.HEAD));
    public static final RegistryObject<ArmorItem> DIAMITHIC_CHESTPLATE = ModArmor.register("diamithic_chestplate", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, EquipmentSlot.CHEST));
    public static final RegistryObject<ArmorItem> DIAMITHIC_LIGGINGS = ModArmor.register("diamithic_leggings", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, EquipmentSlot.LEGS));
    public static final RegistryObject<ArmorItem> DIAMITHIC_BOOTS = ModArmor.register("diamithic_boots", () ->
            new ModArmor(ModArmorMaterial.DIAMITHIC, EquipmentSlot.FEET));

    public static final RegistryObject<ArmorItem> SUPER_HELMET = ModArmor.register("super_helmet", () ->
            new ModArmor(ModArmorMaterial.SUPER, EquipmentSlot.HEAD));
    public static final RegistryObject<ArmorItem> SUPER_CHESTPLATE = ModArmor.register("super_chestplate", () ->
            new ModArmor(ModArmorMaterial.SUPER, EquipmentSlot.CHEST));
    public static final RegistryObject<ArmorItem> SUPER_LIGGINGS = ModArmor.register("super_leggings", () ->
            new ModArmor(ModArmorMaterial.SUPER, EquipmentSlot.LEGS));
    public static final RegistryObject<ArmorItem> SUPER_BOOTS = ModArmor.register("super_boots", () ->
            new ModArmor(ModArmorMaterial.SUPER, EquipmentSlot.FEET));

    public static void initStatic()
    {
        // just to initialize static members
    }
}
