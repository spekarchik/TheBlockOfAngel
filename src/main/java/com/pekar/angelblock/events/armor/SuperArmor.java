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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SuperArmor extends Armor
{
    private final IArmorEffect nightVisionEffect;
    private final SwitchingEffectSynchronizer jumpEffect;
    private final IArmorEffect glowingEffect;
    private final IArmorEffect luckEffect;
    private final IArmorEffect regenerationEffect;
    private final IArmorEffect healthBoostEffect;
    private final IArmorEffect slownessEffect;
    private final IArmorEffect jumpNegativeEffect;
    private final IArmorEffect levitationEffect;
    private final SwitchingEffectSynchronizer superJumpEffect;
    private final CreeperDetectedPacket creeperDetectedPacket = new CreeperDetectedPacket();
    private int creeperDetectedCounter;

    private static final int REGENERATION_EFFECT_DURATION = 140;
    private static final int CREEPER_GLOWING_EFFECT_DURATION = 1200;
    private static final double CREEPER_NOTIFY_DISTANCE = 17.0;
    private static final int MONSTER_SLOWDOWNED_EFFECT_DURATION = 100;
    private static final int ATTACKING_MONSTER_GLOWING_EFFECT_DURATION = 1200;

    public SuperArmor(IPlayer player)
    {
        super(player);
        nightVisionEffect = new NightVisionArmorEffect(player, this);
        glowingEffect = new GlowingArmorEffect(player, this);

        luckEffect = new LuckArmorEffect(player, this);
        regenerationEffect = new RegenerationArmorEffect(player, this, 1, REGENERATION_EFFECT_DURATION);
        slownessEffect = new SlownessArmorEffect(player, this, 4, REGENERATION_EFFECT_DURATION).availableOnAnyArmorElement();
        healthBoostEffect = new HealthBoostArmorEffect(player, this, 2);
        jumpNegativeEffect = new JumpNegativeArmorEffect(player, this, -6, REGENERATION_EFFECT_DURATION).availableOnFullArmorSet();
        levitationEffect = new LevitationSwitchingEffect(player, this, 3).availableOnFullArmorSet();

        var superJumpEffect = new SuperJumpSwitchingEffect(player, this);
        superJumpEffect.setupAvailability(this::isSuperJumpEffectAvailable);
        var dolphinsGraceEffect = new DolphinsGraceEffect(player, this);
        this.superJumpEffect = new SwitchingEffectSynchronizer(superJumpEffect);
        this.superJumpEffect.addDependentEffect(dolphinsGraceEffect);

        var jumpEffect = new JumpBoostArmorEffect(player, this, 5);
        var speedEffect = new SpeedSwitchingEffect(player, this, 1);
        var strengthEffect = new StrengthSwitchingEffect(player, this, 1);
        var waterBreathingEffect = new WaterBreathSwitchingEffect(player, this);
        var hasteEffect = new HasteSwitchingEffect(player, this, 1);

        this.jumpEffect = new SwitchingEffectSynchronizer(jumpEffect);
        this.jumpEffect.addDependentEffect(speedEffect);
        this.jumpEffect.addDependentInvertedEffect(strengthEffect);
        this.jumpEffect.addDependentInvertedEffect(waterBreathingEffect);
        this.jumpEffect.addDependentInvertedEffect(hasteEffect);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        nightVisionEffect.updateSwitchState();
        glowingEffect.updateSwitchState();

        if (!slownessEffect.isActive())
        {
            jumpEffect.updateSwitchState();
            levitationEffect.updateSwitchState();
            superJumpEffect.updateSwitchState();
        }
    }

    @Override
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
        DamageSource damageSource = event.getSource();

        if (isFireDamage(damageSource))
        {
            float realDamage = getRealDamage(event.getAmount());
            event.setAmount(realDamage);
            event.setCanceled(realDamage <= 0);
        }
        else
        {
            boolean isFullArmorSet = player.isFullArmorSetPutOn(getArmorElementNames());
            if (isFullArmorSet)
            {
                if (damageSource.isExplosion())
                {
                    event.setAmount(event.getAmount() * 0.5f);
                }
                else
                {
                    var attacker = damageSource.getEntity();
                    boolean isSilverfish = attacker instanceof Silverfish;
                    boolean isEndermite = attacker instanceof Endermite;
                    boolean isSpider = attacker instanceof Spider;
                    boolean isBee = attacker instanceof Bee;

                    if (isSilverfish || isEndermite || isSpider || isBee)
                    {
                        float damageAmount = event.getAmount();
                        event.setAmount(damageAmount * 0.2F);
                    }
                }
            }
        }
    }

    @Override
    public void onLivingAttackEvent(LivingAttackEvent event)
    {
        DamageSource damageSource = event.getSource();
        boolean isFullArmorSet = player.isFullArmorSetPutOn(getArmorElementNames());

        if (isFireDamage(damageSource))
        {
            float realDamage = getRealDamage(event.getAmount());
            event.setCanceled(realDamage <= 0);
        }
        else if (isFreezeDamage(damageSource))
        {
            boolean areBootsWorn = player.isArmorElementPutOn(ArmorRegistry.SUPER_BOOTS.get().getRegistryName().getPath());
            event.setCanceled(areBootsWorn);
        }
        else if (isFullArmorSet)
        {
            if (damageSource == DamageSource.WITHER)
            {
                event.setCanceled(true);
                player.getEntity().removeEffect(MobEffects.WITHER);
            }
            else
            {
                event.setCanceled(hasEffectImmunity(damageSource));
            }
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
        regenerationEffect.updateEffectAvailability();
        healthBoostEffect.updateEffectAvailability();
        slownessEffect.updateEffectAvailability();
        jumpNegativeEffect.updateEffectAvailability();
        levitationEffect.updateEffectAvailability();
        superJumpEffect.updateEffectAvailability();

        updatePotionEffects();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        if (!player.isArmorElementPutOn(getLeggingsName())) return;
        if (jumpEffect.isActive() || slownessEffect.isActive()) return;

        if (levitationEffect.isEffectOn() && levitationEffect.isActive())
        {
            player.setEffect(MobEffects.JUMP, 30, 6);
        }
        else if (superJumpEffect.isEffectOn() && superJumpEffect.isActive())
        {
            player.setEffect(MobEffects.JUMP, 20, 30);
        }
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
        if (levitationEffect.isEffectOn() && levitationEffect.isActive())
        {
            event.setDamageMultiplier(0);
        }
        else if (superJumpEffect.isEffectOn() && superJumpEffect.isActive())
        {
            event.setDamageMultiplier(0.3f);
        }
        else if (jumpEffect.isEffectOn() && jumpEffect.isActive())
        {
            event.setDamageMultiplier(0.3f);
        }
        else if (player.isArmorElementPutOn(getBootsName()))
        {
            event.setDamageMultiplier(0.6f);
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

            if (++creeperDetectedCounter > 2)
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
        if (pressedKeyDescription.equals(KeyRegistry.NIGHT_VISION.getName()))
        {
            nightVisionEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
        {
            glowingEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.REGENERATION.getName()))
        {
            if (regenerationEffect.isEffectAvailable())
            {
                regenerationEffect.trySwitch();
                slownessEffect.trySwitch();
                jumpEffect.trySwitchOff();
                levitationEffect.trySwitchOff();
                jumpNegativeEffect.trySwitch();
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            if (!slownessEffect.isActive() && jumpEffect.isEffectAvailable())
            {
                jumpEffect.trySwitch();
                levitationEffect.updateEffectActivity(getLevitationAmplifier());
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.LEVITATION.getName()))
        {
            if (!slownessEffect.isActive())
            {
                levitationEffect.trySwitch(getLevitationAmplifier());
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.SUPER_JUMP.getName()))
        {
            superJumpEffect.trySwitch();
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        // none, until we don't need to change effect amplifiers between dimensions
    }

    @Override
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        // none
    }

    @Override
    public void onBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event)
    {
        if (jumpEffect.isEffectOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }
    }

    @Override
    public void onBeingInWater()
    {
        // none
    }

    @Override
    public void onBeingUnderRain()
    {
        // none
    }

    @Override
    public String getHelmetName()
    {
        return ArmorRegistry.SUPER_HELMET.get().getRegistryName().getPath();
    }

    @Override
    public String getChestPlateName()
    {
        return ArmorRegistry.SUPER_CHESTPLATE.get().getRegistryName().getPath();
    }

    @Override
    public String getLeggingsName()
    {
        return ArmorRegistry.SUPER_LEGGINGS.get().getRegistryName().getPath();
    }

    @Override
    public String getBootsName()
    {
        return ArmorRegistry.SUPER_BOOTS.get().getRegistryName().getPath();
    }

    private float getRealDamage(float initialDamageAmount)
    {
        float helmetProtection = player.isArmorElementPutOn(getHelmetName()) ? initialDamageAmount * 0.2f : 0;
        float bootsProtection = player.isArmorElementPutOn(getBootsName()) ? initialDamageAmount * 0.2f : 0;
        float chestplateProtection = player.isArmorElementPutOn(getChestPlateName()) ? initialDamageAmount * 0.35f : 0;
        float leggingsProtection = player.isArmorElementPutOn(getLeggingsName()) ? initialDamageAmount * 0.3f : 0;
        float realDamage = initialDamageAmount - helmetProtection - bootsProtection - chestplateProtection - leggingsProtection;
        return realDamage > 0 ? realDamage : 0;
    }

    private void updatePotionEffects()
    {
        nightVisionEffect.updateEffectActivity();
        luckEffect.updateEffectActivity();
        glowingEffect.updateEffectActivity();
        regenerationEffect.updateEffectActivity();
        healthBoostEffect.updateEffectActivity();
        slownessEffect.updateEffectActivity();
        jumpNegativeEffect.updateEffectActivity();
        superJumpEffect.updateEffectActivity();

        levitationEffect.updateEffectActivity(getLevitationAmplifier());

        if (!slownessEffect.isActive())
        {
            jumpEffect.updateEffectActivity();
        }
        else
        {
            jumpEffect.updateDependentEffectsActivity();
        }
    }

    private boolean isFireDamage(DamageSource damageSource)
    {
        return damageSource.isFire();
    }

    private boolean isFreezeDamage(DamageSource damageSource)
    {
        return damageSource == DamageSource.FREEZE;
    }

    private boolean hasEffectImmunity(DamageSource damageSource)
    {
        boolean isCactus = damageSource == DamageSource.CACTUS;
        boolean isSweetBerryBush = damageSource == DamageSource.SWEET_BERRY_BUSH;
        boolean isDragonBreath = damageSource == DamageSource.DRAGON_BREATH;
        boolean isLightning = damageSource == DamageSource.LIGHTNING_BOLT;

        return isCactus || isSweetBerryBush || isDragonBreath || isLightning || damageSource.isMagic();
    }

    private int getLevitationAmplifier()
    {
        return jumpEffect.isEffectOn() && jumpEffect.isActive() ? 3 : 250;
    }

    private boolean isSuperJumpEffectAvailable(IPlayer player, IArmor armor)
    {
        var boots = player.getEntity().getItemBySlot(EquipmentSlot.FEET);
        var leggings = player.getEntity().getItemBySlot(EquipmentSlot.LEGS);

        int bootsDamage = boots.getDamageValue();
        int leggingsDamage = leggings.getDamageValue();
        int maxBootsDamageToJump = boots.getMaxDamage() / 2;
        int maxLeggingsDamageToJump = leggings.getMaxDamage() / 2;

        return player.isFullArmorSetPutOn(armor.getArmorElementNames())
                && bootsDamage < maxBootsDamageToJump && leggingsDamage < maxLeggingsDamageToJump;
    }
}
