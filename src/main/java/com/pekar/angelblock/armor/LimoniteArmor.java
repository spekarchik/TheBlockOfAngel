package com.pekar.angelblock.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LimoniteArmor extends ModHumanoidArmor
{
    protected LimoniteArmor(ModArmorMaterial material, Type equipmentSlot, Properties properties)
    {
        super(material, equipmentSlot, PlayerArmorType.LYMONITE, properties);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        if (isPlayerHeavy(wearer)) return false;

        var itemStack = wearer.getItemBySlot(EquipmentSlot.FEET);
        var bootsItem = itemStack.getItem();
        if (!(bootsItem instanceof ModHumanoidArmor boots)) return false;

        return boots.getArmorType() == PlayerArmorType.LYMONITE;
    }
}
