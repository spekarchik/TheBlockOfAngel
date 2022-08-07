package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class SuperArmor extends ModArmor
{
    protected SuperArmor(ArmorMaterial material, EquipmentSlot equipmentSlot, String armorItemName)
    {
        super(material, equipmentSlot, armorItemName);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        var leggings = wearer.getItemBySlot(EquipmentSlot.LEGS).getItem();
        return leggings.getName(null).equals(ArmorRegistry.SUPER_LEGGINGS.get().getName(null));
    }

    @Override
    public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity)
    {
        var helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
        return helmet.getName(null).equals(ArmorRegistry.SUPER_HELMET.get().getName(null));
    }
}
