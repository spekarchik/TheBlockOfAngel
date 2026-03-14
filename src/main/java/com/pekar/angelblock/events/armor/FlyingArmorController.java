package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.PlayerArmorType;
import com.pekar.angelblock.events.effect.JumpBoostSwitchingArmorEffect;
import com.pekar.angelblock.events.effect.SlowFallingSwitchingEffect;
import com.pekar.angelblock.events.effect.SpeedSwitchingEffect;
import com.pekar.angelblock.events.effect.SuperJumpSwitchingEffect;
import com.pekar.angelblock.events.effect.base.ISwitchingArmorEffect;
import com.pekar.angelblock.events.effect.base.ISwitchingEffectSynchronizer;
import com.pekar.angelblock.events.effect.base.SwitchingEffectSynchronizer;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class FlyingArmorController extends PlayerArmor
{
    private final ISwitchingEffectSynchronizer jumpBoostEffect;
    private final ISwitchingArmorEffect slowFallingEffect;
    private final ISwitchingArmorEffect speedEffect;
    private static final int JUMP_BOOST_AMPLIFIER = 24;
    private boolean isSlowFallingActivatedOnGround = true;

    public FlyingArmorController(IPlayer player)
    {
        super(player, PlayerArmorType.AERYTE);

        slowFallingEffect = new SlowFallingSwitchingEffect(player, this);
        slowFallingEffect.setup().availableOnFullArmorSet();

        speedEffect = new SpeedSwitchingEffect(player, this, 1);
        speedEffect.setup().showIcon().setupAvailability(this::isSpeedAvailable);
        var jumpBoostEffect = new JumpBoostSwitchingArmorEffect(player, this, JUMP_BOOST_AMPLIFIER);
        jumpBoostEffect.setup().setupAvailability(this::isJumpEffectAvailable).hideIcon();
        var superJumpEffect = new SuperJumpSwitchingEffect(player, this);
        superJumpEffect.setupAvailability(this::isJumpEffectAvailable);
        this.jumpBoostEffect = new SwitchingEffectSynchronizer(jumpBoostEffect);
        this.jumpBoostEffect.addDependentEffect(superJumpEffect);
    }

    @Override
    protected void onLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        isSlowFallingActivatedOnGround = player.getPlayerEntity().onGround();
    }

    @Override
    protected void updateAvailability()
    {
        jumpBoostEffect.updateAvailability();
        speedEffect.updateAvailability();
        slowFallingEffect.updateAvailability();
    }

    @Override
    protected void updateEffectStates()
    {
        jumpBoostEffect.updateSwitchState();
        speedEffect.updateSwitchState();

        if (!player.isNether())
        {
            slowFallingEffect.updateSwitchState();
        }
    }

    @Override
    protected void updateActivityForHeadSlot()
    {
    }

    @Override
    protected void updateActivityForFeetSlot()
    {
    }

    @Override
    protected void updateActivityForLegsSlot()
    {
    }

    @Override
    protected void updateActivityForChestSlot()
    {
    }

    @Override
    protected void updateActivity(EquipmentSlot slot)
    {
        slowFallingEffect.updateActivity();
        jumpBoostEffect.updateActivity();
        speedEffect.updateActivity();
    }

    @Override
    protected void onEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        slowFallingEffect.updateSwitchState();
    }

    @Override
    public int getPriority()
    {
        return 1;
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
        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            speedEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.SUPER_JUMP.getName()))
        {
            jumpBoostEffect.trySwitch();
        }

        if (pressedKeyDescription.equals(KeyRegistry.LEVITATION.getName()))
        {
            slowFallingEffect.trySwitch();

            if (slowFallingEffect.isOn())
                isSlowFallingActivatedOnGround = player.getPlayerEntity().onGround();
            else
                isSlowFallingActivatedOnGround = true;

            if (slowFallingEffect.isActive())
                player.getPlayerEntity().stopFallFlying();
        }

        // for tests
//        if (pressedKeyDescription.equals(KeyRegistry.GLOWING.getName()))
//        {
//            switchArmorDamage();
//        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
    }

    @Override
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        jumpBoostEffect.updateAvailability();
        speedEffect.updateAvailability();
        slowFallingEffect.updateAvailability();

        jumpBoostEffect.updateActivity();
        speedEffect.updateActivity();
        slowFallingEffect.updateActivity();
    }

    @Override
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        if (player.getPlayerEntity().isInWaterRainOrBubble() || jumpBoostEffect.isOn() || speedEffect.isOn())
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
    public void onHeavyTick()
    {
        updateSlowFallingEffect();
    }

    @Override
    public void onEffectAddedEvent(MobEffectEvent.Added event)
    {
    }

    private boolean isJumpEffectAvailable(IPlayer player, IPlayerArmor armor)
    {
        var playerEntity = player.getPlayerEntity();
        if (playerEntity.hasEffect(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT))
            return false;
        
        var boots = playerEntity.getItemBySlot(EquipmentSlot.FEET);

        int bootsDamage = boots.getDamageValue();
        int maxBootsDamageToJump = boots.getMaxDamage() / 2;

        return player.isFullArmorSetPutOn(this)
                && bootsDamage < maxBootsDamageToJump;
    }

    private void updateSlowFallingEffect()
    {
        if (!isSlowFallingActivatedOnGround && slowFallingEffect.isOn() && player.getPlayerEntity().onGround())
        {
            slowFallingEffect.trySwitchOff();
            isSlowFallingActivatedOnGround = true;
        }
    }

    private boolean isSpeedAvailable(IPlayer player, IPlayerArmor armor)
    {
        var boots = player.getPlayerEntity().getItemBySlot(EquipmentSlot.FEET);

        return player.isArmorElementPutOn(this, EquipmentSlot.FEET)
                && !player.isEffectActive(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT)
//                && !player.isEffectActive(PotionRegistry.ENERGY_CRYSTAL_EFFECT)
                && boots.getDamageValue() < boots.getMaxDamage() / 2;
    }
}
