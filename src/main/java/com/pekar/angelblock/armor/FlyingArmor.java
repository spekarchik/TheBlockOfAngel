package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.equipment.ArmorType;

public class FlyingArmor extends ModArmor
{
    protected FlyingArmor(ModArmorMaterial material, ArmorType equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, properties);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment)
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
