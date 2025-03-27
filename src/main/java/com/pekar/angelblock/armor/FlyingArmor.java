package com.pekar.angelblock.armor;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;

public class FlyingArmor extends ModArmor
{
    protected FlyingArmor(ModArmorMaterial material, ArmorType equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, properties.component(DataComponents.GLIDER, Unit.INSTANCE));
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return false;
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        return getModelName(wearer, EquipmentSlot.FEET)
            .equals(ArmorRegistry.FLYING_BOOTS.get().getArmorFamilyName());

    }

    private String getModelName(LivingEntity entity, EquipmentSlot slot)
    {
        var item = entity.getItemBySlot(slot).getItem();
        if (!(item instanceof ModArmor armorItem)) return "";
        return armorItem.getArmorFamilyName();
    }
}
