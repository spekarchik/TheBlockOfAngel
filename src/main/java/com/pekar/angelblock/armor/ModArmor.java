package com.pekar.angelblock.armor;

import com.pekar.angelblock.tab.ModTab;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class ModArmor extends ArmorItem
{
    protected final String armorModelName;

    protected ModArmor(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, new Properties().tab(ModTab.MOD_TAB));
        this.armorModelName = armorModelName;
    }

    public String getArmorModelName()
    {
        return armorModelName;
    }

    public boolean isModifiedWithDetector()
    {
        return false;
    }

    public boolean isModifiedWithHealthRegenerator()
    {
        return false;
    }
}
