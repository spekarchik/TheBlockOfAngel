package com.pekar.angelblock.armor;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.equipment.ArmorType;

public class SuperArmorFlying extends SuperArmor
{
    protected SuperArmorFlying(ModArmorMaterial material, ArmorType equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, properties.component(DataComponents.GLIDER, Unit.INSTANCE));
        withElytra();
    }

    private String getModelName(LivingEntity entity, EquipmentSlot slot)
    {
        var item = entity.getItemBySlot(slot).getItem();
        if (!(item instanceof ModArmor armorItem)) return "";
        return armorItem.getArmorFamilyName();
    }
}
