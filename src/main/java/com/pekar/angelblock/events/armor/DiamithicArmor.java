package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class DiamithicArmor extends Armor
{
    private final IArmorEffect strengthEffect;
    private final IArmorEffect nightVisionEffect;
    private final IArmorEffect jumpBoostEffect;
    private final IArmorEffect slowFallingEffect;
    private final IArmorEffect healthBoostEffect;
    private final IArmorEffect hasteEffect;
    private final IArmorEffect slownessEffect;

    private static final int STRENGTH_EFFECT_AMPLIFIER_DEFAULT = 0;
    private static final int STRENGTH_EFFECT_AMPLIFIER_IMPROVED = 1;

    public DiamithicArmor(IPlayer player)
    {
        super(player);
        strengthEffect = new StrengthArmorEffect(player, this, STRENGTH_EFFECT_AMPLIFIER_DEFAULT);
        nightVisionEffect = new NightVisionArmorEffect(player, this);
        healthBoostEffect = new HealthBoostArmorEffect(player, this, 2);
        hasteEffect = new HasteArmorEffect(player, this);
        slownessEffect = new SlownessPermanentArmorEffect(player, this, 0).setupAvailability(this::isSlownessAvailable);

        jumpBoostEffect = new JumpBoostArmorEffect(player, this, 2);
        slowFallingEffect = new SlowFallingSwitchingEffect(player, this);
        slowFallingEffect.setupAvailability(this::isSlowFallingEffectAvailable);
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        nightVisionEffect.updateSwitchState();
        jumpBoostEffect.updateSwitchState();
        slowFallingEffect.updateSwitchState();
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        event.setCanceled(player.isFullArmorSetPutOn(this) && isLightningBoltDamage(event.getSource()));
    }

    @Override
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
        boolean isFullArmorSet = player.isFullArmorSetPutOn(this);
        var damageSource = event.getSource();
        if (isFullArmorSet && isExplosionDamage(damageSource) && player.isChestPlateModifiedWithStrengthBooster(this))
        {
            event.setNewDamage(event.getNewDamage() * 0.5f);
        }
        else if (isBiting(damageSource.getEntity()))
        {
            event.setNewDamage(event.getNewDamage() * 2f);
        }
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        nightVisionEffect.updateEffectAvailability();
        strengthEffect.updateEffectAvailability();
        jumpBoostEffect.updateEffectAvailability();
        slowFallingEffect.updateEffectAvailability();
        healthBoostEffect.updateEffectAvailability();
        hasteEffect.updateEffectAvailability();
        slownessEffect.updateEffectAvailability();

        updatePotionEffects();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        slownessEffect.updateEffectAvailability();
        slownessEffect.updateEffectActivity();
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
        slownessEffect.updateEffectAvailability();
        slownessEffect.updateEffectActivity();

        if (player.areBootsModifiedWithStrengthBooster(this))
        {
            event.setDamageMultiplier(0.3f);
        }

        if ((event.getEntity() instanceof ServerPlayer playerEntity) && !playerEntity.hasEffect(MobEffects.SLOW_FALLING))
        {
            breakBlockUnderPlayer(playerEntity, false, isIcePredicate, Blocks.WATER.defaultBlockState(), playIceBreakSound);
            breakBlockUnderPlayer(playerEntity, false, isCrackedBlockPredicate, Blocks.AIR.defaultBlockState(), playCrackedBlockBreakSound);
        }
    }

    @Override
    public void onCreeperCheck()
    {
        slownessEffect.updateEffectAvailability();
        slownessEffect.updateEffectActivity();

        boolean isHelmetModifiedWithDetector = player.isArmorModifiedWithDetector(this);
        detectCreepers(isHelmetModifiedWithDetector,false);

        if (player.getEntity() instanceof ServerPlayer playerEntity)
        {
            breakBlockUnderPlayer(playerEntity, true, isIcePredicate, Blocks.WATER.defaultBlockState(), playIceBreakSound);
            breakBlockUnderPlayer(playerEntity, true, isCrackedBlockPredicate, Blocks.AIR.defaultBlockState(), playCrackedBlockBreakSound);
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
            slowFallingEffect.trySwitch();

            if (jumpBoostEffect.isEffectAvailable() && slowFallingEffect.isEffectAvailable() && jumpBoostEffect.isEffectOn() != slowFallingEffect.isEffectOn())
            {
                slowFallingEffect.trySwitchOff();
                jumpBoostEffect.trySwitchOff();
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
        if (jumpBoostEffect.isEffectOn())
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
        slownessEffect.updateEffectAvailability();
        slownessEffect.updateEffectActivity();
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

    private void updatePotionEffects()
    {
        nightVisionEffect.updateEffectActivity();
        strengthEffect.updateEffectActivity(getStrengthEffectAmplifier());
        jumpBoostEffect.updateEffectActivity();
        slowFallingEffect.updateEffectActivity();
        healthBoostEffect.updateEffectActivity();
        hasteEffect.updateEffectActivity();
        slownessEffect.updateEffectActivity();
    }

    private int getStrengthEffectAmplifier()
    {
        return player.isChestPlateModifiedWithStrengthBooster(this)
                ? STRENGTH_EFFECT_AMPLIFIER_IMPROVED
                : STRENGTH_EFFECT_AMPLIFIER_DEFAULT;
    }

    private boolean isSlowFallingEffectAvailable(IPlayer player, IArmor armor)
    {
        return player.isArmorModifiedWithLevitation(armor);
    }

    private boolean isSlownessAvailable(IPlayer player, IArmor armor)
    {
        return !player.getEntity().isInWater();
    }
}
