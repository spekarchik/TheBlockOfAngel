package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.tab.ModTab;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ModArmor extends ArmorItem
{
    public static final Set<String> ArmorNames = new HashSet<>();

    protected ModArmor(ArmorMaterial material, EquipmentSlot equipmentSlot)
    {
        super(material, equipmentSlot, new Properties().tab(ModTab.MOD_TAB));
    }

    protected static RegistryObject<ArmorItem> register(String name, Supplier<ArmorItem> sup)
    {
        ArmorNames.add(name);
        return Main.ITEMS.register(name, sup);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        if (!(stack.getItem() instanceof ArmorItem)) return false;

        var armorItem = (ArmorItem) stack.getItem();
        return armorItem.getMaterial() == ArmorMaterials.GOLD
                || armorItem.getRegistryName().getPath().equals(ArmorRegistry.RENDELITHIC_LEGGINGS.get().getRegistryName().getPath());
    }
}
