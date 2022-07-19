package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.tab.ModTab;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModArmor extends ArmorItem
{
    protected ModArmor(ArmorMaterial material, EquipmentSlot equipmentSlot)
    {
        super(material, equipmentSlot, new Properties().tab(ModTab.MOD_TAB));
    }

    protected static RegistryObject<ArmorItem> register(String name, Supplier<ArmorItem> sup)
    {
        return Main.ITEMS.register(name, sup);
    }
}
