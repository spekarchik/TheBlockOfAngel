package com.pekar.angelblock.armor;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class ModArmor extends ArmorItem
{
    protected final String armorModelName;

    protected ModArmor(ArmorMaterial material, Type equipmentSlot, String armorModelName)
    {
        super(material, equipmentSlot, new Properties());
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

    public boolean isModifiedWithStrengthBooster()
    {
        return false;
    }

    public boolean isModifiedWithLevitation()
    {
        return false;
    }

    public boolean isModifiedWithSeaPower()
    {
        return false;
    }
}
