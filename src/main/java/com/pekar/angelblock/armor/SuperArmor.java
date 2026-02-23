package com.pekar.angelblock.armor;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.Nullable;

public class SuperArmor extends ModHumanoidArmor
{
    protected SuperArmor(ModArmorMaterial material, ArmorType equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, properties);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.LEGS);
        var leggingsItem = itemStack.getItem();
        if (!(leggingsItem instanceof ModHumanoidArmor leggings)) return false;
        return leggings.getArmorFamilyName().equals(ArmorRegistry.SUPER_LEGGINGS.get().getArmorFamilyName());
    }

    @Override
    public boolean isGazeDisguise(ItemStack stack, Player player, @Nullable LivingEntity entity)
    {
        var itemStack = player.getItemBySlot(EquipmentSlot.HEAD);
        var helmetItem = itemStack.getItem();
        if (!(helmetItem instanceof ModHumanoidArmor helmet)) return false;
        return helmet.getArmorFamilyName().equals(ArmorRegistry.SUPER_HELMET.get().getArmorFamilyName());
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.FEET);
        var bootsItem = itemStack.getItem();
        if (!(bootsItem instanceof ModHumanoidArmor boots)) return false;
        return boots.getArmorFamilyName().equals(ArmorRegistry.SUPER_BOOTS.get().getArmorFamilyName()) && !wearer.hasEffect(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT);
    }
}
