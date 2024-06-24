package com.pekar.angelblock.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class SuperArmor extends ModArmor
{
    protected SuperArmor(Holder<ArmorMaterial> material, Type equipmentSlot, String armorItemName, int durabilityMultiplier)
    {
        super(material, equipmentSlot, armorItemName, durabilityMultiplier);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.LEGS);
        var leggingsItem = itemStack.getItem();
        if (!(leggingsItem instanceof ModArmor leggings)) return false;
        return leggings.getArmorModelName().equals(ArmorRegistry.SUPER_LEGGINGS.get().getArmorModelName());
    }

    @Override
    public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity)
    {
        var itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
        var helmetItem = itemStack.getItem();
        if (!(helmetItem instanceof ModArmor helmet)) return false;
        return helmet.getArmorModelName().equals(ArmorRegistry.SUPER_HELMET.get().getArmorModelName());
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.FEET);
        var bootsItem = itemStack.getItem();
        if (!(bootsItem instanceof ModArmor boots)) return false;
        return boots.getArmorModelName().equals(ArmorRegistry.SUPER_BOOTS.get().getArmorModelName());
    }
}
