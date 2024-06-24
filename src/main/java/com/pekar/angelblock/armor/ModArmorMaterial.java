package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.Utils;
import com.pekar.angelblock.items.ItemRegistry;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static net.minecraft.world.item.ArmorItem.Type.*;

public class ModArmorMaterial
{
    private final Holder<ArmorMaterial> material;
    private final String armorModelName;
    private final int durabilityMultiplier;

    private static final Holder<ArmorMaterial> Rendelithic = register(
            "rendelithic",
            createArmorTypeMap(3, 6, 8, 3),
            17,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            0F,
            0F,
            () -> Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get())); // durabilityMultiplier: 21

    protected static final Holder<ArmorMaterial> RENDELITHIC2 = Holder.direct(new ModArmorMaterial("angelblock:rendelithic_armor2",
            21, new int[] { 3, 6, 8, 3 }, 17, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            0F, 0F, () -> Ingredient.of(ItemRegistry.RENDELITHIC_INGOT.get())));

    protected static final Holder<ArmorMaterial> LIMONITE = Holder.direct(new ModArmorMaterial("angelblock:limonite_armor",
            28, new int[] { 3, 5, 8, 3 }, 30, SoundEvents.ARMOR_EQUIP_LEATHER.get(),
            1F, 0F, () -> Ingredient.of(ItemRegistry.LIMONITE_INGOT.get())));

    protected static final Holder<ArmorMaterial> LIMONITE2 = Holder.direct(new ModArmorMaterial("angelblock:limonite_armor2",
            28, new int[] { 3, 5, 8, 3 }, 30, SoundEvents.ARMOR_EQUIP_LEATHER.get(),
            1F, 0F, () -> Ingredient.of(ItemRegistry.LIMONITE_INGOT.get())));

    protected static final Holder<ArmorMaterial> DIAMITHIC = Holder.direct(new ModArmorMaterial("angelblock:diamithic_armor",
            41, new int[] { 3, 6, 9, 3 }, 14, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            3F, 0.1F, () -> Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get())));

    protected static final Holder<ArmorMaterial> DIAMITHIC2 = Holder.direct(new ModArmorMaterial("angelblock:diamithic_armor2",
            41, new int[] { 3, 6, 9, 3 }, 14, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            3F, 0.1F, () -> Ingredient.of(ItemRegistry.DIAMITHIC_INGOT.get())));

    protected static final Holder<ArmorMaterial> LAPIS = Holder.direct(new ModArmorMaterial("angelblock:lapis_armor",
            37, new int[] { 3, 6, 8, 3 }, 12, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            2F, 0F, () -> Ingredient.of(ItemRegistry.LAPIS_INGOT.get())));

    protected static final Holder<ArmorMaterial> LAPIS2 = Holder.direct(new ModArmorMaterial("angelblock:lapis_armor2",
            37, new int[] { 3, 6, 8, 3 }, 12, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            2F, 0F, () -> Ingredient.of(ItemRegistry.LAPIS_INGOT.get())));

    protected static final Holder<ArmorMaterial> SUPER = Holder.direct(new ModArmorMaterial("angelblock:super_armor",
            43, new int[] { 3, 6, 9, 3 }, 25, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            4F, 0.2F, () -> Ingredient.of(ItemRegistry.SUPER_INGOT.get())));

    protected static final Holder<ArmorMaterial> SUPER2 = Holder.direct(new ModArmorMaterial("angelblock:super_armor2",
            43, new int[] { 3, 6, 9, 3 }, 25, SoundEvents.ARMOR_EQUIP_DIAMOND.get(),
            4F, 0.2F, () -> Ingredient.of(ItemRegistry.SUPER_INGOT.get())));

    protected static final Holder<ArmorMaterial> FLYING = Holder.direct(new ModArmorMaterial("angelblock:flying_armor",
            3, new int[] { 1, 1, 1, 1 }, 30, SoundEvents.ARMOR_EQUIP_ELYTRA.get(),
            0F, 0F, () -> Ingredient.of(Items.PHANTOM_MEMBRANE)));


    protected static final ModArmorMaterial RENDELITHIC = new ModArmorMaterial(Rendelithic, "rendelithic", 21);


    public ModArmorMaterial(Holder<ArmorMaterial> material, String armorModelName, int durabilityMultiplier)
    {
        this.material = material;
        this.armorModelName = armorModelName;
        this.durabilityMultiplier = durabilityMultiplier;
    }

    public Holder<ArmorMaterial> getMaterial()
    {
        return material;
    }

    public String getArmorModelName()
    {
        return armorModelName;
    }

    public int getDurabilityMultiplier()
    {
        return durabilityMultiplier;
    }

    // copied from ArmorMaterials and modified
    private static Holder<ArmorMaterial> register(
            String materialName,
            EnumMap<ArmorItem.Type, Integer> map,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> ingredient)
    {
        var armorLayers = getNonDyeableArmorLayers(materialName);
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

        for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, map.get(armoritem$type));
        }

        return Registry.registerForHolder(
                BuiltInRegistries.f_315942_, //was in 1.20.6: BuiltInRegistries.ARMOR_MATERIAL
                Utils.createResourceLocation(materialName),
                new ArmorMaterial(enummap, enchantmentValue, equipSound, ingredient, armorLayers, toughness, knockbackResistance)
        );
    }

    private static EnumMap<ArmorItem.Type, Integer> createArmorTypeMap(int bootsResistance, int leggingsResistance, int chestplateResistance, int helmetResistance)
    {
        return Util.make(new EnumMap<>(ArmorItem.Type.class), armorTypeMap -> {
            armorTypeMap.put(ArmorItem.Type.BOOTS, 3);
            armorTypeMap.put(ArmorItem.Type.LEGGINGS, 6);
            armorTypeMap.put(ArmorItem.Type.CHESTPLATE, 8);
            armorTypeMap.put(ArmorItem.Type.HELMET, 3);
            armorTypeMap.put(ArmorItem.Type.BODY, 11);
        });
    }

    private static List<ArmorMaterial.Layer> getNonDyeableArmorLayers(String materialName)
    {
        var fullMaterialName = Main.MODID + ":" + materialName;
        var resourceLocation = Utils.createResourceLocation(fullMaterialName);
        return List.of(new ArmorMaterial.Layer(resourceLocation));
    }
}
