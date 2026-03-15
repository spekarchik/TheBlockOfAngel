package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.AnimalArmorType;
import com.pekar.angelblock.events.animal.IAnimal;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;

abstract class AnimalArmor extends ArmorBase implements IAnimalArmor
{
    protected final IAnimal animal;
    private final AnimalArmorType armorType;

    protected AnimalArmor(IAnimal animal, AnimalArmorType armorType)
    {
        this.animal = animal;
        this.armorType = armorType;
    }

    @Override
    public final AnimalArmorType getArmorType()
    {
        return armorType;
    }

    @Override
    public void onArmorHurtEvent(ArmorHurtEvent event)
    {
        var stack = event.getArmorItemStack(EquipmentSlot.BODY);
        var maxDamage = stack.getMaxDamage();
        var durability = maxDamage - stack.getDamageValue();
        float amount = event.getNewDamage(EquipmentSlot.BODY);

        if (amount >= durability)
        {
            event.setNewDamage(EquipmentSlot.BODY, durability - 1);
        }
    }

    @Override
    protected float getArmorTypeDefenseRatio()
    {
        return 1F;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof AnimalArmor armor))
        {
            return false;
        }
        return getArmorType() == armor.getArmorType();
    }

    @Override
    public int hashCode()
    {
        return getArmorType().hashCode();
    }
}
