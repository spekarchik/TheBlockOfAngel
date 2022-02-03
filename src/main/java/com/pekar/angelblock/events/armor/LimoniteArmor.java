package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.network.packets.CreeperDetectedPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class LimoniteArmor extends Armor
{
    private final IArmorEffect nightVisionEffect;
    private final IArmorEffect glowingEffect;
    private final IArmorEffect luckEffect;
    private final IArmorEffect healthBoostEffect;
    private final IArmorEffect regenerationEffect;
    private final IArmorEffect slownessEffect;
    private final IArmorEffect jumpNegativeEffect;
    private final SwitchingEffectSynchronizer jumpEffect;
    private final CreeperDetectedPacket creeperDetectedPacket = new CreeperDetectedPacket();
    private int creeperDetectedCounter;

    private static final int REGENERATION_EFFECT_DURATION = 100;
    private static final int MONSTER_SLOWDOWNED_EFFECT_DURATION = 100;
    private static final int REGENERATION_NEGATIVE_EFFECT_DURATION = 1200;
    private static final int CREEPER_GLOWING_EFFECT_DURATION = 1200;
    private static final int ATTACKING_MONSTER_GLOWING_EFFECT_DURATION = 1200;
    private static final double CREEPER_NOTIFY_DISTANCE = 17.0;

    public LimoniteArmor(IPlayer player)
    {
        super(player);

        nightVisionEffect = new NightVisionArmorEffect(player, this);
        glowingEffect = new GlowingArmorEffect(player, this);
        luckEffect = new LuckArmorEffect(player, this);
        healthBoostEffect = new HealthBoostArmorEffect(player, this, 1);
        regenerationEffect = new RegenerationArmorEffect(player, this, 0, REGENERATION_EFFECT_DURATION);
        slownessEffect = new SlownessArmorEffect(player, this, 0, REGENERATION_NEGATIVE_EFFECT_DURATION).availableOnAnyArmorElement();
        jumpNegativeEffect = new JumpNegativeArmorEffect(player, this, -6, REGENERATION_NEGATIVE_EFFECT_DURATION).availableOnFullArmorSet();

        var jumpEffect = new JumpBoostArmorEffect(player, this, 16);
        var speedEffect = new SpeedSwitchingEffect(player, this, 0);
        var levitationEffect = new LevitationSwitchingEffect(player, this, 250);
        levitationEffect.availableOnBootsAndLeggings();

        this.jumpEffect = new SwitchingEffectSynchronizer(jumpEffect);
        this.jumpEffect.addDependentEffect(speedEffect);
        this.jumpEffect.addDependentEffect(levitationEffect);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        nightVisionEffect.updateSwitchState();
        glowingEffect.updateSwitchState();

        if (!slownessEffect.isActive())
        {
            jumpEffect.updateSwitchState();
        }
    }

    @Override
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
        var damageSource = event.getSource();
        var attacker = damageSource.getEntity();
        boolean isSilverfish = attacker instanceof Silverfish;
        boolean isEndermite = attacker instanceof Endermite;
        boolean isSpider = attacker instanceof Spider;
        boolean isBee = attacker instanceof Bee;

        if (isSilverfish || isEndermite || isSpider || isBee)
        {
            if (player.isFullArmorSetPutOn(getArmorElementNames()))
            {
                float damageAmount = event.getAmount();
                event.setAmount(damageAmount * 0.2F);
            }
        }
        else if (damageSource.isFire() || damageSource.isExplosion())
        {
            event.setAmount(event.getAmount() * 2F);
        }
    }

    @Override
    public void onLivingAttackEvent(LivingAttackEvent event)
    {
        boolean isFullArmorSet = player.isFullArmorSetPutOn(getArmorElementNames());

        DamageSource damageSource = event.getSource();

        if (isFreezeDamage(damageSource))
        {
            boolean areBootsWorn = player.isArmorElementPutOn(getBootsName());
            event.setCanceled(areBootsWorn);
        }
        else
        {
            event.setCanceled(isFullArmorSet && hasEffectImmunity(damageSource));
        }

        if (!isFullArmorSet) return;
        if (!(damageSource.getEntity() instanceof LivingEntity)) return;

        LivingEntity entityAttackedBy = (LivingEntity) damageSource.getEntity();

        if (entityAttackedBy != null)
        {
            boolean isZombie = entityAttackedBy instanceof Zombie;
            boolean isSkeleton = entityAttackedBy instanceof Skeleton;
            boolean isWitch = entityAttackedBy instanceof Witch;
            boolean isIllager = entityAttackedBy instanceof AbstractIllager;

            if (isZombie || isSkeleton || isIllager || isWitch)
            {
                float distance = player.getEntity().distanceTo(entityAttackedBy);
                if (!isWitch && distance > 2f)
                {
                    entityAttackedBy.setSecondsOnFire(5);
                }
                else
                {
                    entityAttackedBy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, MONSTER_SLOWDOWNED_EFFECT_DURATION, 2));
                }
            }

            var effect = new MobEffectInstance(MobEffects.GLOWING, ATTACKING_MONSTER_GLOWING_EFFECT_DURATION, 0, false, false, false);
            entityAttackedBy.addEffect(effect);
        }
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        jumpEffect.updateEffectAvailability();
        nightVisionEffect.updateEffectAvailability();
        luckEffect.updateEffectAvailability();
        glowingEffect.updateEffectAvailability();
        healthBoostEffect.updateEffectAvailability();
        regenerationEffect.updateEffectAvailability();
        slownessEffect.updateEffectAvailability();
        jumpNegativeEffect.updateEffectAvailability();

        updatePotionEffects();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        // none
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
        if (jumpEffect.isEffectOn() && jumpEffect.isActive())
        {
            event.setDamageMultiplier(0);
        }
    }

    @Override
    public void onCreeperCheck()
    {
        boolean isFullArmorSet = player.isFullArmorSetPutOn(getArmorElementNames());
        if (!isFullArmorSet) return;

        Player entityPlayer = player.getEntity();
        var level = entityPlayer.level;
        if (level.isClientSide()) return;

        var monsters = level.getEntities((Entity)null, entityPlayer.getBoundingBox().inflate(CREEPER_NOTIFY_DISTANCE),
                entity -> entity instanceof Creeper);

        for (Entity monster : monsters)
        {
            var entity = (LivingEntity) monster;
            if (!entity.hasEffect(MobEffects.GLOWING))
            {
                var potionEffect = new MobEffectInstance(MobEffects.GLOWING, CREEPER_GLOWING_EFFECT_DURATION, 0 /*amplifier*/, false /*ambient*/, false /*visible*/, false /*showIcon*/);
                entity.addEffect(potionEffect);
            }

            if (++creeperDetectedCounter > 3)
            {
                creeperDetectedPacket.sendToPlayer((ServerPlayer) entityPlayer);
                creeperDetectedCounter = 0;
            }

            return;
        }
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        if (pressedKeyDescription.equals(KeyRegistry.REGENERATION.getName()))
        {
            if (regenerationEffect.isEffectAvailable())
            {
                jumpEffect.trySwitchOff();
                regenerationEffect.trySwitch();
                slownessEffect.trySwitch();
                jumpNegativeEffect.trySwitch();
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.NIGHT_VISION.getName()))
        {
            nightVisionEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
        {
            glowingEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            if (!slownessEffect.isActive())
            {
                jumpEffect.trySwitch();
            }
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        // none
    }

    @Override
    public void onBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event)
    {
        if (jumpEffect.isEffectOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.5f);
        }
    }

    @Override
    public void onBeingInWater()
    {
        // none
    }

    @Override
    public String getHelmetName()
    {
        return ArmorRegistry.LIMONITE_HELMET.get().getRegistryName().getPath();
    }

    @Override
    public String getChestPlateName()
    {
        return ArmorRegistry.LIMONITE_CHESTPLATE.get().getRegistryName().getPath();
    }

    @Override
    public String getLeggingsName()
    {
        return ArmorRegistry.LIMONITE_LEGGINGS.get().getRegistryName().getPath();
    }

    @Override
    public String getBootsName()
    {
        return ArmorRegistry.LIMONITE_BOOTS.get().getRegistryName().getPath();
    }

    private void updatePotionEffects()
    {
        nightVisionEffect.updateEffectActivity();
        luckEffect.updateEffectActivity();
        glowingEffect.updateEffectActivity();
        healthBoostEffect.updateEffectActivity();
        regenerationEffect.updateEffectActivity();
        slownessEffect.updateEffectActivity();
        jumpNegativeEffect.updateEffectActivity();

        if (!slownessEffect.isActive())
        {
            jumpEffect.updateEffectActivity();
        }
        else
        {
            jumpEffect.updateDependentEffectsActivity();
        }
    }

    private boolean isFreezeDamage(DamageSource damageSource)
    {
        return damageSource == DamageSource.FREEZE;
    }

    private boolean hasEffectImmunity(DamageSource damageSource)
    {
        boolean isCactus = damageSource == DamageSource.CACTUS;
        boolean isSweetBerryBush = damageSource == DamageSource.SWEET_BERRY_BUSH;
        boolean isLightning = damageSource == DamageSource.LIGHTNING_BOLT;

        return isCactus || isSweetBerryBush || isLightning || damageSource.isMagic();
    }
}
