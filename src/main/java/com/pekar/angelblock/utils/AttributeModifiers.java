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
            double correctionSum = 0.0;

            for (var stack : entity.getArmorSlots())
            {
                if (stack.isEmpty()) continue;
                boolean isModArmor = stack.getItem() instanceof ModArmor;

                double maxDamage = stack.getMaxDamage();
                if (maxDamage <= 0) continue;

                double damage = isModArmor ? stack.getDamageValue() : 0.0;

                if (!(stack.getItem() instanceof ModArmor modArmor)) continue;

                double defense = modArmor.getDefense();
                double durability = maxDamage - damage;

                double durabilityPercent = durability / maxDamage;
                double correction = defense * (durabilityPercent - 1.0);
                correctionSum += correction;
            }

            var armorModifierId = ResourceLocation.fromNamespaceAndPath(Main.MODID, getArmorAttributeMofifierId(entity));
            armorAttribute.removeModifier(armorModifierId);

            if (Math.abs(correctionSum) > 1e-12)
                armorAttribute.addTransientModifier(new AttributeModifier(armorModifierId, correctionSum, AttributeModifier.Operation.ADD_VALUE));
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
