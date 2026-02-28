package com.pekar.angelblock.events.animal;

import com.pekar.angelblock.armor.AnimalArmorType;
import com.pekar.angelblock.armor.ModAnimalArmor;
import com.pekar.angelblock.events.armor.IAnimalArmor;
import com.pekar.angelblock.events.armor.LymoniteHorseArmorController;
import com.pekar.angelblock.events.armor.RendeliteWolfArmorController;
import com.pekar.angelblock.events.mob.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;

import java.util.EnumMap;
import java.util.Map;

public class ModAnimal extends Mob implements IAnimal
{
    private final Animal entity;
    private final Map<AnimalArmorType, IAnimalArmor> armorInUse = new EnumMap<>(AnimalArmorType.class);

    public ModAnimal(Animal entity)
    {
        this.entity = entity;
    }

    public void updateArmorUsed()
    {
        armorInUse.clear();

        var itemStack = getAnimalEntity().getBodyArmorItem();
        if (itemStack.isEmpty() || !(itemStack.getItem() instanceof ModAnimalArmor modArmor)) return;
        var armorType = modArmor.getArmorType();
        armorInUse.put(armorType, armorType.createBehavior(this));
    }

    @Override
    public Iterable<IAnimalArmor> getArmorTypesUsed()
    {
        return armorInUse.values();
    }

    @Override
    public Animal getAnimalEntity()
    {
        return entity;
    }

    @Override
    public LivingEntity getEntity()
    {
        return entity;
    }

    @Override
    public IAnimalArmor getArmor()
    {
        var armor = entity.getBodyArmorItem();
        if (armor.isEmpty() || !(armor.getItem() instanceof ModAnimalArmor modArmor))
            return null;

        return armorInUse.get(modArmor.getArmorType());
    }
}
