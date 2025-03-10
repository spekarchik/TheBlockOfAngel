package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class FlyingArmor extends Armor
{
    private final ISwitchingEffectSynchronizer jumpBoostEffect;
    private final ISwitchingArmorEffect slowFallingEffect;
    private static final int JUMP_BOOST_AMPLIFIER = 24;
    private boolean isSlowFallingActivatedOnGround = true;

    public FlyingArmor(IPlayer player)
    {
        super(player);

        slowFallingEffect = new SlowFallingSwitchingEffect(player, this).availableIfSlotSet(EquipmentSlot.CHEST);

        var speedEffect = new SpeedSwitchingEffect(player, this, 1);
        speedEffect.setupAvailability(this::isJumpEffectAvailable);
        var jumpBoostEffect = new JumpBoostSwitchingArmorEffect(player, this, JUMP_BOOST_AMPLIFIER);
        jumpBoostEffect.setupAvailability(this::isJumpEffectAvailable);
        var superJumpEffect = new SuperJumpSwitchingEffect(player, this);
        superJumpEffect.setupAvailability(this::isJumpEffectAvailable);
        this.jumpBoostEffect = new SwitchingEffectSynchronizer(jumpBoostEffect);
        this.jumpBoostEffect.addDependentEffect(speedEffect);
        this.jumpBoostEffect.addDependentEffect(superJumpEffect);
    }

    @Override
    public String getFamilyName()
    {
        return ArmorRegistry.FLYING_BOOTS.get().getArmorFamilyName();
    }

    @Override
    public int getPriority()
    {
        return 1;
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        isSlowFallingActivatedOnGround = player.getEntity().onGround();

        jumpBoostEffect.updateSwitchState();

        if (!player.isNether())
        {
            slowFallingEffect.updateSwitchState();
        }
    }

    @Override
    public void onLivingHurtEvent(LivingIncomingDamageEvent event)
    {
        if (isFreezeDamage(event.getSource()))
        {
            boolean areBootsWorn = player.isArmorElementPutOn(this, EquipmentSlot.FEET);
            event.setCanceled(areBootsWorn);
        }
    }

    @Override
    public void onLivingDamageEvent(LivingDamageEvent.Pre event)
    {
        // none
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        jumpBoostEffect.updateAvailability();
        slowFallingEffect.updateAvailability();

        slowFallingEffect.updateSwitchState();
        updatePotionEffects();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
        updateSlowFallingEffect();
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
        if (player.isArmorElementPutOn(this, EquipmentSlot.FEET))
        {
            event.setDamageMultiplier(0.1F);
        }
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()) || pressedKeyDescription.equals(KeyRegistry.SUPER_JUMP.getName()))
        {
            jumpBoostEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.LEVITATION.getName()))
        {
            slowFallingEffect.trySwitch();

            if (slowFallingEffect.isOn())
                isSlowFallingActivatedOnGround = player.getEntity().onGround();
            else
                isSlowFallingActivatedOnGround = true;

            if (slowFallingEffect.isActive())
                player.getEntity().stopFallFlying();
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
    }

    @Override
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        jumpBoostEffect.updateAvailability();
        slowFallingEffect.updateAvailability();

        updatePotionEffects();
    }

    @Override
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        if (player.getEntity().isInWaterRainOrBubble() || jumpBoostEffect.isOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }
    }

    @Override
    public void onBeingInLava()
    {
        if (slowFallingEffect.isOn())
            slowFallingEffect.trySwitchOff();
    }

    @Override
    public void onBeingInWater()
    {
        if (slowFallingEffect.isOn())
            slowFallingEffect.trySwitchOff();
    }

    @Override
    public void onBeingUnderRain()
    {
    }

    @Override
    public void onCreeperCheck()
    {
        updateSlowFallingEffect();
    }

    @Override
    public void onEffectAddedEvent(MobEffectEvent.Added event)
    {
    }

    private void updatePotionEffects()
    {
        jumpBoostEffect.updateActivity();
        slowFallingEffect.updateActivity();
    }

    private boolean isJumpEffectAvailable(IPlayer player, IArmor armor)
    {
        var boots = player.getEntity().getItemBySlot(EquipmentSlot.FEET);

        int bootsDamage = boots.getDamageValue();
        int maxBootsDamageToJump = boots.getMaxDamage() / 2;

        return player.isArmorElementPutOn(this, EquipmentSlot.FEET)
                && bootsDamage < maxBootsDamageToJump;
    }

    private void updateSlowFallingEffect()
    {
        if (!isSlowFallingActivatedOnGround && slowFallingEffect.isOn() && player.getEntity().onGround())
        {
            slowFallingEffect.trySwitchOff();
            isSlowFallingActivatedOnGround = true;
        }
    }
}
