package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterial
{
    private final Holder<ArmorMaterial> material;
    private final String materialName;
    private final String armorModelName;
    private final int durabilityMultiplier;

    protected static final ModArmorMaterial RENDELITHIC = new ModArmorMaterial("rendelithic", "rendelithic_armor",
            createArmorTypeMap(3, 6, 8, 3, 7),
            17, 0F, 0F, 21, SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get()));
    protected static final ModArmorMaterial RENDELITHIC2 = new ModArmorMaterial("rendelithic", "rendelithic_armor2",
            createArmorTypeMap(3, 6, 8, 3, 7),
            17, 0F, 0F, 21, SoundEvents.ARMOR_EQUIP_GOLD, () -> Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get()));
    protected static final ModArmorMaterial LIMONITE = new ModArmorMaterial("limonite", "limonite_armor",
            createArmorTypeMap(3, 5, 8, 3, 3),
            30, 1F, 0F, 28, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ItemRegistry.LIMONITE_INGOT.get()));
    protected static final ModArmorMaterial LIMONITE2 = new ModArmorMaterial("limonite", "limonite_armor2",
            createArmorTypeMap(3, 5, 8, 3, 3),
            30, 1F, 0F, 28, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ItemRegistry.LIMONITE_INGOT.get()));
    protected static final ModArmorMaterial DIAMITHIC = new ModArmorMaterial("diamithic", "diamithic_armor",
            createArmorTypeMap(3, 6, 9, 3, 11),
            14, 3F, 0.1F, 41, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get()));
    protected static final ModArmorMaterial DIAMITHIC2 = new ModArmorMaterial("diamithic", "diamithic_armor2",
            createArmorTypeMap(3, 6, 9, 3, 11),
            14, 3F, 0.1F, 41, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get()));
    protected static final ModArmorMaterial LAPIS = new ModArmorMaterial("lapis", "lapis_armor",
            createArmorTypeMap(3, 6, 8, 3, 11),
            12, 2F, 0F,37, SoundEvents.ARMOR_EQUIP_DIAMOND, () -> Ingredient.of(ItemRegistry.LAPIS_INGOT.get()));
    protected static final ModArmorMaterial LAPIS2 = new ModArmorMaterial("lapis", "lapis_armor2",
            createArmorTypeMap(3, 6, 8, 3, 11),
            12, 2F, 0F,37, SoundEvents.ARMOR_EQUIP_DIAMOND, () -> Ingredient.of(ItemRegistry.LAPIS_INGOT.get()));
    protected static final ModArmorMaterial SUPER = new ModArmorMaterial("super", "super_armor",
            createArmorTypeMap(3, 6, 9, 3, 15),
            25, 4F, 0.2F, 43, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(ItemRegistry.SUPER_INGOT.get()));
    protected static final ModArmorMaterial SUPER2 = new ModArmorMaterial("super", "super_armor2",
            createArmorTypeMap(3, 6, 9, 3, 15),
            25, 4F, 0.2F, 43, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(ItemRegistry.SUPER_INGOT.get()));
    protected static final ModArmorMaterial FLYING = new ModArmorMaterial("flying", "flying_armor",
            createArmorTypeMap(1, 1, 1, 1, 1),
            30, 0F, 0F, 3, SoundEvents.ARMOR_EQUIP_ELYTRA, () -> Ingredient.of(Items.PHANTOM_MEMBRANE));


    public ModArmorMaterial(String materialName, String armorModelName, EnumMap<ArmorItem.Type, Integer> armorResistanceMap,
                            int enchantmentValue, float toughness, float knockbackResistance, int durabilityMultiplier,
                            Holder<SoundEvent> equipmentSound, Supplier<Ingredient> repairIngredient)
    {
        this.material = register(armorModelName, armorResistanceMap, enchantmentValue, equipmentSound, toughness, knockbackResistance, repairIngredient);
        this.materialName = materialName;
        this.armorModelName = armorModelName;
        this.durabilityMultiplier = durabilityMultiplier;
    }

    public Holder<ArmorMaterial> getMaterial()
    {
        return material;
    }

    public String getMaterialName()
    {
        return materialName;
    }

    public String getArmorModelName()
    {
        return armorModelName;
    }

    public int getDurabilityMultiplier()
    {
        return durabilityMultiplier;
    }

    public String getFullArmorModelName()
    {
        return getFullArmorModelName(getArmorModelName());
    }

    private static String getFullArmorModelName(String armorModelName)
    {
        return Main.MODID + ":" + armorModelName;
    }

    // copied from ArmorMaterials and modified
    private static Holder<ArmorMaterial> register(
            String armorName,
            EnumMap<ArmorItem.Type, Integer> map,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient)
    {
        var armorLayers = getNonDyeableArmorLayers(armorName);
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

        for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, map.get(armoritem$type));
        }

        return Registry.registerForHolder(
                BuiltInRegistries.ARMOR_MATERIAL,
                Utils.instance.resources.createResourceLocation(Main.MODID, armorName),
                new ArmorMaterial(enummap, enchantmentValue, equipSound, repairIngredient, armorLayers, toughness, knockbackResistance)
        );
    }

    private static EnumMap<ArmorItem.Type, Integer> createArmorTypeMap(int bootsResistance, int leggingsResistance, int chestplateResistance, int helmetResistance, int bodyResistance)
    {
        return Util.make(new EnumMap<>(ArmorItem.Type.class), armorTypeMap -> {
            armorTypeMap.put(ArmorItem.Type.BOOTS, bootsResistance);
            armorTypeMap.put(ArmorItem.Type.LEGGINGS, leggingsResistance);
            armorTypeMap.put(ArmorItem.Type.CHESTPLATE, chestplateResistance);
            armorTypeMap.put(ArmorItem.Type.HELMET, helmetResistance);
            armorTypeMap.put(ArmorItem.Type.BODY, bodyResistance);
        });
    }

    private static List<ArmorMaterial.Layer> getNonDyeableArmorLayers(String armorName)
    {
        var resourceLocation = Utils.instance.resources.createResourceLocation(Main.MODID, armorName);
        return List.of(new ArmorMaterial.Layer(resourceLocation));
    }
}
