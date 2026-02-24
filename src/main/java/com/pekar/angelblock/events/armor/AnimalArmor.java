package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.events.animal.IAnimal;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;

public abstract class AnimalArmor extends ArmorBase implements IAnimalArmor
{
    protected final IAnimal animal;

    public AnimalArmor(IAnimal animal)
    {
        this.animal = animal;
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
}
