package com.pekar.angelblock.events.animal;

import com.pekar.angelblock.armor.ModArmor;
import com.pekar.angelblock.events.armor.IAnimalArmor;
import com.pekar.angelblock.events.armor.LymoniteHorseArmor;
import com.pekar.angelblock.events.armor.RendeliteWolfArmor;
import com.pekar.angelblock.events.mob.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ModAnimal extends Mob implements IAnimal
{
    private final Animal entity;
    private final IAnimalArmor rendeliteArmorModel = new RendeliteWolfArmor(this);
    private final IAnimalArmor lymoniteArmorModel = new LymoniteHorseArmor(this);
    private final Set<IAnimalArmor> armorInUse = ConcurrentHashMap.newKeySet();

    public ModAnimal(Animal entity)
    {
        this.entity = entity;
    }

    public void updateArmorUsed()
    {
        armorInUse.clear();

        var armor = getArmor();
        if (armor != null)
        {
            armorInUse.add(armor);
        }
    }

    @Override
    public Iterable<IAnimalArmor> getArmorTypesUsed()
    {
        return armorInUse;
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
        if (armor.isEmpty() || !(armor.getItem() instanceof ModArmor modArmor))
            return null;

        return getArmorModel(modArmor);
    }

    private IAnimalArmor getArmorModel(ModArmor modArmor)
    {
        var modelName = modArmor.getArmorFamilyName();

        if (modelName.equals(rendeliteArmorModel.getFamilyName()))
        {
            return rendeliteArmorModel;
        }
        else if (modelName.equals(lymoniteArmorModel.getFamilyName()))
        {
            return lymoniteArmorModel;
        }
        else
        {
            return null;
        }
    }
}
