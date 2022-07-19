package com.pekar.angelblock.armor;

import com.pekar.angelblock.tab.ModTab;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class ModArmor extends ArmorItem
{
    protected final String armorItemName;

    protected ModArmor(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, new Properties().tab(ModTab.MOD_TAB));
        this.armorItemName = armorItemName;
    }

    public String getArmorItemName()
    {
        return armorItemName;
    }
}
