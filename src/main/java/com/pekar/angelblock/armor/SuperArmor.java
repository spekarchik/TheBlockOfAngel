package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SuperArmor extends ModArmor
{
    protected SuperArmor(ModArmorMaterial material, Type equipmentSlot)
    {
        super(material, equipmentSlot);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.LEGS);
        var leggingsItem = itemStack.getItem();
        if (!(leggingsItem instanceof ModArmor leggings)) return false;
        return leggings.getArmorFamilyName().equals(ArmorRegistry.SUPER_LEGGINGS.get().getArmorFamilyName());
    }

    @Override
    public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity)
    {
        var itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
        var helmetItem = itemStack.getItem();
        if (!(helmetItem instanceof ModArmor helmet)) return false;
        return helmet.getArmorFamilyName().equals(ArmorRegistry.SUPER_HELMET.get().getArmorMaterial());
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.FEET);
        var bootsItem = itemStack.getItem();
        if (!(bootsItem instanceof ModArmor boots)) return false;
        return boots.getArmorFamilyName().equals(ArmorRegistry.SUPER_BOOTS.get().getArmorMaterial());
    }
}
