package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;

// Durability:
// LEATHER: 5
// GOLDEN: 7
// CHAINMAIL: 15
// IRON: 15
// DIAMOND: 33
// NETHERITE: 37

public class ModArmorMaterial
{
    private final ArmorMaterial material;
    private final String materialName;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorType, Integer> armorResistanceMap;
    private final int enchantmentValue;
    private final float toughness;
    private final float knockbackResistance;
    private final Holder<SoundEvent> equipmentSound;
    private final TagKey<Item> repairIngredient;
    private final boolean isFireResistant;

    public static final String RENDELITHIC_MATERIAL_NAME = "rendelithic";
    public static final String LIMONITE_MATERIAL_NAME = "limonite";
    public static final String DIAMITHIC_MATERIAL_NAME = "diamithic";
    public static final String LAPIS_MATERIAL_NAME = "lapis";
    public static final String SUPER_MATERIAL_NAME = "super";
    public static final String FLYING_MATERIAL_NAME = "flying";

    // ArmorMaterials

    protected static final ModArmorMaterial RENDELITHIC = new ModArmorMaterial(RENDELITHIC_MATERIAL_NAME, "rendelithic_armor",
            createArmorTypeMap(3, 7, 9, 3, 11),
            25, 0F, 0F, 11, SoundEvents.ARMOR_EQUIP_GOLD, ItemRegistry.RENDELITHIC_INGOT_TAG, true);
    protected static final ModArmorMaterial LIMONITE = new ModArmorMaterial(LIMONITE_MATERIAL_NAME, "limonite_armor",
            createArmorTypeMap(2, 5, 7, 3, 9),
            30, 1F, 0F, 23, SoundEvents.ARMOR_EQUIP_LEATHER, ItemRegistry.LIMONITE_INGOT_TAG, false);
    protected static final ModArmorMaterial LAPIS = new ModArmorMaterial(LAPIS_MATERIAL_NAME, "lapis_armor",
            createArmorTypeMap(3, 6, 8, 3, 11),
            10, 0F, 0F,40, SoundEvents.ARMOR_EQUIP_DIAMOND, ItemRegistry.LAPIS_INGOT_TAG, false);
    protected static final ModArmorMaterial DIAMITHIC = new ModArmorMaterial(DIAMITHIC_MATERIAL_NAME, "diamithic_armor",
            createArmorTypeMap(3, 7, 9, 3, 19),
            14, 3F, 0.2F, 45, SoundEvents.ARMOR_EQUIP_NETHERITE, ItemRegistry.DIAMITHIC_INGOT_TAG, true);
    protected static final ModArmorMaterial SUPER = new ModArmorMaterial(SUPER_MATERIAL_NAME, "super_armor",
            createArmorTypeMap(5, 9, 11, 5, 19),
            1, 4F, 0.2F, 43, SoundEvents.ARMOR_EQUIP_NETHERITE, ItemRegistry.SUPER_INGOT_TAG, true);
    protected static final ModArmorMaterial FLYING = new ModArmorMaterial(FLYING_MATERIAL_NAME, "flying_armor",
            createArmorTypeMap(1, 2, 3, 1, 2),
            1, 0F, 0F, 3, SoundEvents.ARMOR_EQUIP_ELYTRA, ItemRegistry.FLYING_INGOT_TAG, false);

    // other armor models (other textures)
    protected static final ModArmorMaterial RENDELITHIC2 = copyOf(RENDELITHIC, "rendelithic_armor2");
    protected static final ModArmorMaterial LIMONITE2 = copyOf(LIMONITE, "limonite_armor2");
    protected static final ModArmorMaterial DIAMITHIC2 = copyOf(DIAMITHIC, "diamithic_armor2");
    protected static final ModArmorMaterial LAPIS2 = copyOf(LAPIS, "lapis_armor2");
    protected static final ModArmorMaterial SUPER2 = copyOf(SUPER, "super_armor2");


    public ModArmorMaterial(String materialName, String armorModelName, EnumMap<ArmorType, Integer> armorResistanceMap,
                            int enchantmentValue, float toughness, float knockbackResistance, int durabilityMultiplier,
                            Holder<SoundEvent> equipmentSound, TagKey<Item> repairIngredient, boolean isFireResistant)
    {
        this.material = createVanillaMaterial(armorModelName, durabilityMultiplier, armorResistanceMap, enchantmentValue, equipmentSound, toughness, knockbackResistance, repairIngredient);
        this.materialName = materialName;
        this.armorResistanceMap = armorResistanceMap;
        this.enchantmentValue = enchantmentValue;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.durabilityMultiplier = durabilityMultiplier;
        this.equipmentSound = equipmentSound;
        this.repairIngredient = repairIngredient;
        this.isFireResistant = isFireResistant;
    }

    public ArmorMaterial getMaterial()
    {
        return material;
    }

    public String getMaterialName()
    {
        return materialName;
    }

    public int getDurabilityMultiplier()
    {
        return durabilityMultiplier;
    }

    public boolean isFireResistant()
    {
        return isFireResistant;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ModArmorMaterial another)) return false;
        return materialName.equals(another.materialName);
    }

    @Override
    public int hashCode()
    {
        return materialName.hashCode();
    }

    // copied from ArmorMaterials 1.21.1 and modified
    private static ArmorMaterial createVanillaMaterial(
            String armorName,
            int durability,
            EnumMap<ArmorType, Integer> defence,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            TagKey<Item> repairIngredient)
    {
        var modelId = Utils.instance.resources.createResourceLocation(Main.MODID, armorName);
        return new ArmorMaterial(durability, defence, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient, modelId);
    }

    private static EnumMap<ArmorType, Integer> createArmorTypeMap(int bootsResistance, int leggingsResistance, int chestplateResistance, int helmetResistance, int bodyResistance)
    {
        return Util.make(new EnumMap<>(ArmorType.class), armorTypeMap -> {
            armorTypeMap.put(ArmorType.BOOTS, bootsResistance);
            armorTypeMap.put(ArmorType.LEGGINGS, leggingsResistance);
            armorTypeMap.put(ArmorType.CHESTPLATE, chestplateResistance);
            armorTypeMap.put(ArmorType.HELMET, helmetResistance);
            armorTypeMap.put(ArmorType.BODY, bodyResistance);
        });
    }

    private static ModArmorMaterial copyOf(ModArmorMaterial armorMaterial, String armorModelName)
    {
        return new ModArmorMaterial(
                armorMaterial.materialName,
                armorModelName,
                armorMaterial.armorResistanceMap,
                armorMaterial.enchantmentValue,
                armorMaterial.toughness,
                armorMaterial.knockbackResistance,
                armorMaterial.durabilityMultiplier,
                armorMaterial.equipmentSound,
                armorMaterial.repairIngredient,
                armorMaterial.isFireResistant);
    }
}
