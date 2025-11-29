package com.pekar.angelblock.utils;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.armor.ModArmor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AttributeModifiers
{
    public void updateArmorAttributeModifier(LivingEntity entity)
    {
        if (entity == null) return;

        var armorAttribute = entity.getAttribute(Attributes.ARMOR);

        if (armorAttribute != null)
        {
            int damageSum = 0, maxDamageSum = 0;

            for (var slot : Utils.instance.player.getArmorInSlots(entity))
            {
                if (slot.isEmpty()) continue;
                boolean isModArmor = slot.getItem() instanceof ModArmor;
                damageSum += isModArmor ? slot.getDamageValue() : 0;
                maxDamageSum += slot.getMaxDamage();
            }

            var durabilitySum = maxDamageSum - damageSum;

            var armorModifierId = ResourceLocation.fromNamespaceAndPath(Main.MODID, getArmorAttributeMofifierId(entity));
            armorAttribute.removeModifier(armorModifierId);
            double durabilityPercent = (double) (durabilitySum) / (double) maxDamageSum;
            double correction = armorAttribute.getValue() * (durabilityPercent - 1.0);
            armorAttribute.addTransientModifier(new AttributeModifier(armorModifierId, correction, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    public void removeArmorAttributeModifier(LivingEntity entity)
    {
        if (entity == null) return;

        var armorAttribute = entity.getAttribute(Attributes.ARMOR);

        if (armorAttribute != null)
        {
            var armorModifierId = ResourceLocation.fromNamespaceAndPath(Main.MODID, getArmorAttributeMofifierId(entity));
            armorAttribute.removeModifier(armorModifierId);
        }
    }

    private String getArmorAttributeMofifierId(LivingEntity entity)
    {
        return entity.getStringUUID();
    }
}
