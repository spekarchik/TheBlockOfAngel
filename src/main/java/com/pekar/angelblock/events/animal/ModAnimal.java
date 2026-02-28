package com.pekar.angelblock.events.animal;

import com.pekar.angelblock.armor.AnimalArmorType;
import com.pekar.angelblock.armor.ModAnimalArmor;
import com.pekar.angelblock.events.armor.IAnimalArmor;
import com.pekar.angelblock.events.mob.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class ModAnimal extends Mob implements IAnimal
{
    private final Animal entity;
    private final Map<AnimalArmorType, IAnimalArmor> armorControllers = new EnumMap<>(AnimalArmorType.class);
    private final EnumSet<AnimalArmorType> armorInUse = EnumSet.noneOf(AnimalArmorType.class);

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
        if (armorInUse.contains(armorType)) return;
        var armorController = armorControllers.get(armorType);
        if (armorController == null)
        {
            armorController = armorType.createController(this);
            armorControllers.put(armorType, armorController);
        }
        armorInUse.add(armorType);
    }

    @Override
    public Iterable<IAnimalArmor> getArmorTypesUsed()
    {
        return armorInUse.stream().map(armorControllers::get).toList();
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

        return armorControllers.get(modArmor.getArmorType());
    }
}
