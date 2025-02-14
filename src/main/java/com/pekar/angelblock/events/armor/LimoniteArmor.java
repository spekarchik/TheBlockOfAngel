package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.network.packets.CreeperDetectedPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

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

    private static final int REGENERATION_EFFECT_DURATION = 200;
    private static final int MONSTER_SLOWDOWNED_EFFECT_DURATION = 100;
    private static final int REGENERATION_NEGATIVE_EFFECT_DURATION = 1200;
    private static final int CREEPER_GLOWING_EFFECT_DURATION = 1200;
    private static final int ATTACKING_MONSTER_GLOWING_EFFECT_DURATION = 1200;
    private static final double CREEPER_NOTIFY_DISTANCE = 17.0;
    private static final int JUMP_EFFECT_AMPLIFIER_DEFAULT = 2;
    private static final int JUMP_EFFECT_AMPLIFIER_BOOSTED = 6;

    public LimoniteArmor(IPlayer player)
    {
        super(player);

        nightVisionEffect = new NightVisionArmorEffect(player, this);
        glowingEffect = new GlowingArmorEffect(player, this);
        luckEffect = new LuckArmorEffect(player, this).setupAvailability(this::isLuckEffectAvailable);
        healthBoostEffect = new HealthBoostArmorEffect(player, this, 1);
        regenerationEffect = new RegenerationArmorEffect(player, this, 0, REGENERATION_EFFECT_DURATION);
        slownessEffect = new SlownessArmorEffect(player, this, 1, REGENERATION_NEGATIVE_EFFECT_DURATION).availableOnAnyArmorElement();
        jumpNegativeEffect = new JumpNegativeArmorEffect(player, this, -2, REGENERATION_NEGATIVE_EFFECT_DURATION).availableOnFullArmorSet();

        var jumpEffect = new JumpBoostArmorEffect(player, this, JUMP_EFFECT_AMPLIFIER_DEFAULT);
        jumpEffect.availableIfSlotsSet(EquipmentSlot.FEET, EquipmentSlot.LEGS);
        var speedEffect = new SpeedSwitchingEffect(player, this, 0);
        var slowFallingEffect = new SlowFallingSwitchingEffect(player, this);

        this.jumpEffect = new SwitchingEffectSynchronizer(jumpEffect);
        this.jumpEffect.addDependentEffect(speedEffect);
        this.jumpEffect.addDependentEffect(slowFallingEffect);
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
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        var damageSource = event.getSource();
        var attacker = damageSource.getEntity();

        if (isBiting(attacker))
        {
            if (player.isFullArmorSetPutOn(this))
            {
                float damageAmount = event.getAmount();
                event.setAmount(damageAmount * 0.2F);
            }
        }
        else if (isFireOrHotFloorDamage(damageSource) || damageSource.is(DamageTypes.EXPLOSION))
        {
            event.setAmount(event.getAmount() * 2F);
        }

        if (isFreezeDamage(damageSource))
        {
            boolean areBootsWorn = player.isArmorElementPutOn(this, EquipmentSlot.FEET);
            event.setCanceled(areBootsWorn);
        }
        else
        {
            event.setCanceled(isThornOrMagicDamage(damageSource) && player.isArmorModifiedWithHealthRegenerator(this));
        }

        boolean isFullArmorSet = player.isFullArmorSetPutOn(this);

        if (!isFullArmorSet) return;
        if (!(damageSource.getEntity() instanceof LivingEntity entityAttackedBy)) return;

        if (isSlowMovementAffected(entityAttackedBy))
        {
            boolean isWitch = entityAttackedBy instanceof Witch;
            float distance = player.getEntity().distanceTo(entityAttackedBy);
            if (!isWitch && distance > 2f)
            {
                entityAttackedBy.setRemainingFireTicks(5 * Utils.TICKS_PER_SECOND); // TODO: Test if an attacker is firing after attacking you
                //entityAttackedBy.setSecondsOnFire(5);
            }
            else
            {
                entityAttackedBy.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, MONSTER_SLOWDOWNED_EFFECT_DURATION, 2));
            }
        }

        var effect = new MobEffectInstance(MobEffects.GLOWING, ATTACKING_MONSTER_GLOWING_EFFECT_DURATION, 0, false, false, false);
        entityAttackedBy.addEffect(effect);
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
        boolean isHelmetModifiedWithDetector = player.isArmorModifiedWithDetector(this);
        if (!isHelmetModifiedWithDetector) return;

        Player entityPlayer = player.getEntity();
        var level = entityPlayer.level();
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
            if (regenerationEffect.isEffectAvailable() && player.getEntity().getHealth() < player.getEntity().getMaxHealth())
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
                jumpEffect.trySwitch(getJumpEffectAmplifier());
            }
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        // none
    }

    @Override
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        // none
    }

    @Override
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
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
        if (!player.isFullArmorSetPutOn(this)) return;

        if (player.getEntity().getHealth() < player.getEntity().getMaxHealth())
        {
            regenerationEffect.trySwitch();
        }
    }

    @Override
    public String getModelName()
    {
        return ArmorRegistry.LIMONITE_BOOTS.get().getMaterialName();
    }

    @Override
    public int getPriority()
    {
        return 4;
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
            jumpEffect.updateEffectActivity(getJumpEffectAmplifier());
        }
        else
        {
            jumpEffect.updateDependentEffectsActivity();
        }
    }

    private int getJumpEffectAmplifier()
    {
        return player.areBootsModifiedWithStrengthBooster(this) ? JUMP_EFFECT_AMPLIFIER_BOOSTED : JUMP_EFFECT_AMPLIFIER_DEFAULT;
    }

    private boolean isLuckEffectAvailable(IPlayer player, IArmor armor)
    {
        return player.isFullArmorSetPutOn(armor) && player.isChestPlateModifiedWithSeaPower(armor);
    }
}
