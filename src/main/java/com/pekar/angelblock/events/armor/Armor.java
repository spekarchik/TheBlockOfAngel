package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.events.effect.IArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.network.packets.CreeperDetectedPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

abstract class Armor implements IArmor
{
    protected final IPlayer player;
    private final Set<EquipmentSlot> equipmentSlots = new HashSet<>();
    private final CreeperDetectedPacket creeperDetectedPacket = new CreeperDetectedPacket();
    private int creeperDetectedCounter = 0;

    private static final double CREEPER_NOTIFY_DISTANCE = 17.0;
    private static final double CREEPER_AGRY_DISTANCE = 3.0;
    private static final int CREEPER_GLOWING_EFFECT_DURATION = 1200;

    protected Armor(IPlayer player)
    {
        this.player = player;

        equipmentSlots.add(EquipmentSlot.FEET);
        equipmentSlots.add(EquipmentSlot.LEGS);
        equipmentSlots.add(EquipmentSlot.CHEST);
        equipmentSlots.add(EquipmentSlot.HEAD);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Armor)) return false;
        Armor armor = (Armor) obj;
        return getFamilyName().equals(armor.getFamilyName());
    }

    @Override
    public int hashCode()
    {
        return getFamilyName().hashCode();
    }

    protected void synchronizeEffect(IArmorEffect basicEffect, IArmorEffect dependentEffect)
    {
        if (basicEffect.isEffectOn() != dependentEffect.isEffectOn())
        {
            dependentEffect.invertSwitchState();
        }
    }

    protected void synchronizeEffectInversely(IArmorEffect basicEffect, IArmorEffect dependentEffect)
    {
        if (basicEffect.isEffectOn() == dependentEffect.isEffectOn())
        {
            dependentEffect.invertSwitchState();
        }
    }

    protected boolean isFireDamage(DamageSource damageSource)
    {
        boolean isDamagedByInFire = damageSource.is(DamageTypes.IN_FIRE);
        boolean isDamagedByOnFire = damageSource.is(DamageTypes.ON_FIRE);
        boolean isDamagedByLava = damageSource.is(DamageTypes.LAVA);
        return isDamagedByInFire || isDamagedByLava || isDamagedByOnFire;
    }

    protected boolean isFreezeDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.FREEZE);
    }

    protected boolean isFireOrHotFloorDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.HOT_FLOOR) || isFireDamage(damageSource);
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

    protected boolean isSlowMovementAffected(LivingEntity entity)
    {
        boolean isZombie = entity instanceof Zombie;
        boolean isSkeleton = entity instanceof Skeleton;
        boolean isWitch = entity instanceof Witch;
        boolean isIllager = entity instanceof AbstractIllager;

        return isZombie || isSkeleton || isIllager || isWitch;
    }

    protected void detectCreepers(boolean detect, boolean makeNeutral)
    {
        if (!detect && !makeNeutral) return;

        var entityPlayer = player.getEntity();
        var level = entityPlayer.level();

        if (level.isClientSide()) return;

        var monsters = level.getEntities((Entity)null, entityPlayer.getBoundingBox().inflate(CREEPER_NOTIFY_DISTANCE),
                        entity -> entity instanceof Creeper)
                .stream().map(e -> (Mob)e).toList();

        if (!monsters.isEmpty())
        {
            for (var entity : monsters)
            {
                if (detect && !entity.hasEffect(MobEffects.GLOWING))
                {
                    var potionEffect = new MobEffectInstance(MobEffects.GLOWING, CREEPER_GLOWING_EFFECT_DURATION, 0 /*amplifier*/, false /*ambient*/, false /*visible*/, false /*showIcon*/);
                    entity.addEffect(potionEffect);
                }
                if (makeNeutral && entity.distanceTo(entityPlayer) >= CREEPER_AGRY_DISTANCE && entity.getTarget() == entityPlayer)
                {
                    entity.setTarget(null);
                }
            }

            if (detect)
            {
                if (creeperDetectedCounter > 3)
                    creeperDetectedCounter = 0;
                else if (creeperDetectedCounter++ == 0)
                    creeperDetectedPacket.sendToPlayer((ServerPlayer) entityPlayer);
            }
        }
        else
        {
            if (detect)
                creeperDetectedCounter = 0;
        }
    }
}
