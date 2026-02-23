package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.effect.base.IPermanentArmorEffect;
import com.pekar.angelblock.events.effect.base.ISwitchingArmorEffect;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class DiamithicArmor extends PlayerArmor
{
    private final IPermanentArmorEffect strengthEffect;
    private final IPermanentArmorEffect healthBoostEffect;
    private final IPermanentArmorEffect hasteEffect;
    private final IPermanentArmorEffect slownessEffect;

    private final ISwitchingArmorEffect nightVisionEffect;
    private final ISwitchingArmorEffect jumpBoostEffect;
    private final ISwitchingArmorEffect slowFallingEffect;
    private final ISwitchingArmorEffect glowingEffect;

    private static final int STRENGTH_EFFECT_AMPLIFIER_DEFAULT = 0;
    private static final int STRENGTH_EFFECT_AMPLIFIER_IMPROVED = 1;

    public DiamithicArmor(IPlayer player)
    {
        super(player);
        strengthEffect = new StrengthPermanentArmorEffect(player, this, STRENGTH_EFFECT_AMPLIFIER_DEFAULT);
        nightVisionEffect = new NightVisionSwitchingArmorEffect(player, this);
        nightVisionEffect.setup().availableOnHelmetWithDetector();
        healthBoostEffect = new HealthBoostPermanentArmorEffect(player, this, 2);
        hasteEffect = new HastePermanentArmorEffect(player, this);
        slownessEffect = new SlownessPermanentArmorEffect(player, this, 0);
        slownessEffect.setup().setupAvailability(this::isSlownessAvailable);
        glowingEffect = new GlowingSwitchingArmorEffect(player, this);
        glowingEffect.setup().availableOnChestPlateWithSlowFalling();

        jumpBoostEffect = new JumpBoostSwitchingArmorEffect(player, this, 2);
        jumpBoostEffect.setup().setupAvailability(this::isJumpBoostAvailable);
        slowFallingEffect = new SlowFallingSwitchingEffect(player, this);
        slowFallingEffect.setup().availableOnChestPlateWithSlowFalling();
    }

    @Override
    protected void updateAvailability()
    {
        slownessEffect.updateAvailability();

        strengthEffect.updateAvailability();
        hasteEffect.updateAvailability();
        slowFallingEffect.updateAvailability();
        glowingEffect.updateAvailability();
        healthBoostEffect.updateAvailability();
        jumpBoostEffect.updateAvailability();
        nightVisionEffect.updateAvailability();
    }

    @Override
    protected void updateEffectStates()
    {
        nightVisionEffect.updateSwitchState();
        jumpBoostEffect.updateSwitchState();
        slowFallingEffect.updateSwitchState();
        glowingEffect.updateSwitchState();
    }

    @Override
    protected void updateActivity(EquipmentSlot slot)
    {
        slownessEffect.updateActivity();
    }

    @Override
    public void updateActivityForHeadSlot()
    {
        nightVisionEffect.updateActivity();
    }

    @Override
    public void updateActivityForFeetSlot()
    {
        jumpBoostEffect.updateActivity();
    }

    @Override
    public void updateActivityForLegsSlot()
    {
        healthBoostEffect.updateActivity();
    }

    @Override
    public void updateActivityForChestSlot()
    {
        strengthEffect.updateActivity(getStrengthEffectAmplifier());
        slowFallingEffect.updateActivity();
        hasteEffect.updateActivity();
        glowingEffect.updateActivity();
    }

    @Override
    protected void onEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        var entityPlayer = player.getPlayerEntity();
        if (playerNeedsToRestoreHealth(entityPlayer, event.getSlot(), event.getFrom(), event.getTo()))
        {
            restorePlayerHealth(entityPlayer);
        }
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        event.setCanceled(player.isFullArmorSetPutOn(this) && isLightningBoltDamage(event.getSource()));
    }

    @Override
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
        var damageSource = event.getSource();
        if (isExplosionDamage(damageSource))
        {
            if (player.isChestPlateModifiedWithStrengthBooster(this))
                event.setNewDamage(event.getNewDamage() * 0.5f);
        }
        else if (isBiting(damageSource.getEntity()))
        {
            event.setNewDamage(event.getNewDamage() * 2f);
        }
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        slownessEffect.updateAvailability();
        slownessEffect.updateActivity();
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
        slownessEffect.updateAvailability();
        slownessEffect.updateActivity();

        if ((event.getEntity() instanceof ServerPlayer playerEntity) && !playerEntity.hasEffect(MobEffects.SLOW_FALLING))
        {
            breakBlockUnderPlayer(playerEntity, false, isIcePredicate, Blocks.WATER.defaultBlockState(), playIceBreakSound, 0);
            breakBlockUnderPlayer(playerEntity, false, isCrackedBlockPredicate, Blocks.AIR.defaultBlockState(), playCrackedBlockBreakSound, 0);
            breakBlockUnderPlayer(playerEntity, true, isNetherrackPredicate, Blocks.AIR.defaultBlockState(), playCrackedBlockBreakSound, 20);
        }
    }

    @Override
    public void onCreeperCheck()
    {
        slownessEffect.updateAvailability();
        slownessEffect.updateActivity();

        boolean isHelmetModifiedWithDetector = player.isHelmetModifiedWithDetector(this);
        detectCreepers(isHelmetModifiedWithDetector,false);

        if (player.getPlayerEntity() instanceof ServerPlayer playerEntity)
        {
            breakBlockUnderPlayer(playerEntity, true, isIcePredicate, Blocks.WATER.defaultBlockState(), playIceBreakSound, 32);
            breakBlockUnderPlayer(playerEntity, true, isCrackedBlockPredicate, Blocks.AIR.defaultBlockState(), playCrackedBlockBreakSound, 32);
        }
    }

    @Override
    public void onEffectAddedEvent(MobEffectEvent.Added event)
    {
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        if (pressedKeyDescription.equals(KeyRegistry.NIGHT_VISION.getName()))
        {
            nightVisionEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            jumpBoostEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.LEVITATION.getName()))
        {
            slowFallingEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
        {
            glowingEffect.trySwitch();
            // for tests
            //switchArmorDamage();
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
        if (jumpBoostEffect.isOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }
    }

    @Override
    public void onBeingInLava()
    {
        // none
    }

    @Override
    public void onBeingInWater()
    {
        slownessEffect.updateAvailability();
        slownessEffect.updateActivity();
    }

    @Override
    public void onBeingUnderRain()
    {
        // none
    }

    @Override
    public String getFamilyName()
    {
        return ArmorRegistry.DIAMITHIC_BOOTS.get().getArmorFamilyName();
    }

    @Override
    public int getPriority()
    {
        return 3;
    }

    private boolean isJumpBoostAvailable(IPlayer player, IArmor armor)
    {
        return availableOnBootsWithNoHeavyJump(player, armor) && player.areBootsModifiedWithJumpBooster(armor);
    }

    private int getStrengthEffectAmplifier()
    {
        return player.isChestPlateModifiedWithStrengthBooster(this)
                ? STRENGTH_EFFECT_AMPLIFIER_IMPROVED
                : STRENGTH_EFFECT_AMPLIFIER_DEFAULT;
    }

    private boolean isSlownessAvailable(IPlayer player, IArmor armor)
    {
        return player.isAnyArmorElementInclBrokenPutOn(armor) && !player.getPlayerEntity().isInWater();
    }
}
