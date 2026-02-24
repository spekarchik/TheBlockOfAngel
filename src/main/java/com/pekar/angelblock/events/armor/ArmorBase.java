package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.fish.Pufferfish;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.spider.Spider;

abstract class ArmorBase implements IArmorFamily
{
    protected Utils utils = new Utils();

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ArmorBase armor))
        {
            return false;
        }
        return getFamilyName().equals(armor.getFamilyName());
    }

    @Override
    public int hashCode()
    {
        return getFamilyName().hashCode();
    }

    protected boolean isFreezeDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.FREEZE);
    }

    protected boolean isFireDamage(DamageSource damageSource)
    {
        boolean isDamagedByInFire = damageSource.is(DamageTypes.IN_FIRE);
        boolean isDamagedByOnFire = damageSource.is(DamageTypes.ON_FIRE);
        return isDamagedByInFire || isDamagedByOnFire;
    }

    protected boolean isStandingInSoulFire()
    {
        return false; // no ways to distinguish fire types for now
    }

    protected boolean isLavaDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.LAVA);
    }

    protected boolean isHotFloorDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.HOT_FLOOR);
    }

    protected boolean isFireOrLavaDamage(DamageSource damageSource)
    {
        return isFireDamage(damageSource) || isLavaDamage(damageSource);
    }

    protected boolean isFireOrLavaOrHotFloorDamage(DamageSource damageSource)
    {
        return isHotFloorDamage(damageSource) || isFireOrLavaDamage(damageSource);
    }

    protected boolean isThornOrMagicDamage(DamageSource damageSource)
    {
        boolean isCactus = damageSource.is(DamageTypes.CACTUS);
        boolean isSweetBerryBush = damageSource.is(DamageTypes.SWEET_BERRY_BUSH);
        boolean isPufferFish = damageSource.getEntity() instanceof Pufferfish;
        boolean isMagic = damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypes.INDIRECT_MAGIC);

        return isCactus || isSweetBerryBush || isMagic || isPufferFish;
    }

    protected boolean isLightningBoltDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.LIGHTNING_BOLT);
    }

    protected boolean isExplosionDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.EXPLOSION);
    }

    protected boolean isBiting(Entity entity)
    {
        boolean isSilverfish = entity instanceof Silverfish;
        boolean isEndermite = entity instanceof Endermite;
        boolean isSpider = entity instanceof Spider;
        boolean isBee = entity instanceof Bee;
        return isSilverfish || isEndermite || isSpider || isBee;
    }
}
