package com.pekar.materialext.armor;

import com.pekar.materialext.MaterialExt;
import com.pekar.materialext.tab.MaterialExtTab;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ModArmor extends ArmorItem
{
    public static final Set<String> ArmorNames = new HashSet<>();

    protected ModArmor(ArmorMaterial material, EquipmentSlot equipmentSlot)
    {
        super(material, equipmentSlot, new Properties().tab(MaterialExtTab.MATERIAL_EXT_TAB));
    }

    protected static RegistryObject<ArmorItem> register(String name, Supplier<ArmorItem> sup)
    {
        ArmorNames.add(name);
        return MaterialExt.ITEMS.register(name, sup);
    }

//    public int getRepairAmount()
//    {
//        //getMaterial().getDurabilityForSlot()
//        return getArmorMaterial().getDurability(armorType) / 5;
//    }
}
