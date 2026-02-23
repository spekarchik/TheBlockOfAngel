package com.pekar.angelblock.armor;

import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LimoniteArmor extends ModHumanoidArmor
{
    protected LimoniteArmor(ModArmorMaterial material, Type equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, properties);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        var itemStack = wearer.getItemBySlot(EquipmentSlot.FEET);
        var bootsItem = itemStack.getItem();
        if (!(bootsItem instanceof ModHumanoidArmor boots)) return false;
        return boots.getArmorFamilyName().equals(ArmorRegistry.LIMONITE_BOOTS.get().getArmorFamilyName()) && !wearer.hasEffect(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT);
    }
}
