package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class FlyingArmor extends Armor
{
    private final ISwitchingEffectSynchronizer jumpBoostEffect;
    private final ISwitchingArmorEffect slowFallingEffect;
    private final ISwitchingArmorEffect speedEffect;
    private static final int JUMP_BOOST_AMPLIFIER = 24;
    private boolean isSlowFallingActivatedOnGround = true;

    public FlyingArmor(IPlayer player)
    {
        super(player);

        slowFallingEffect = new SlowFallingSwitchingEffect(player, this).availableIfSlotSet(EquipmentSlot.CHEST).asArmorEffect();

        speedEffect = new SpeedSwitchingEffect(player, this, 1).showIcon().setupAvailability(this::isSpeedAvailable).asArmorEffect();
        var jumpBoostEffect = new JumpBoostSwitchingArmorEffect(player, this, JUMP_BOOST_AMPLIFIER);
        jumpBoostEffect.setupAvailability(this::isJumpEffectAvailable).hideIcon();
        var superJumpEffect = new SuperJumpSwitchingEffect(player, this);
        superJumpEffect.setupAvailability(this::isJumpEffectAvailable);
        this.jumpBoostEffect = new SwitchingEffectSynchronizer(jumpBoostEffect);
        this.jumpBoostEffect.addDependentEffect(superJumpEffect);
    }

    @Override
    protected void onLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        isSlowFallingActivatedOnGround = player.getEntity().onGround();
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
        slowFallingEffect.updateActivity();
    }

    @Override
    protected void updateActivity(EquipmentSlot slot)
    {
        jumpBoostEffect.updateActivity();
        speedEffect.updateActivity();
    }

    @Override
    protected void onEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        slowFallingEffect.updateSwitchState();

        if (event.getTo().is(Items.FIREWORK_ROCKET))
            player.getEntity().stopFallFlying();
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
                isSlowFallingActivatedOnGround = player.getEntity().onGround();
            else
                isSlowFallingActivatedOnGround = true;

            if (slowFallingEffect.isActive())
                player.getEntity().stopFallFlying();
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
        if (player.getEntity().isInWaterRainOrBubble() || jumpBoostEffect.isOn() || speedEffect.isOn())
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

        var playerEntity = player.getEntity();
        if (playerEntity.isFallFlying() && player.isArmorElementPutOn(this, EquipmentSlot.CHEST) && !canFly())
        {
            playerEntity.stopFallFlying();
        }
    }

    @Override
    public void onEffectAddedEvent(MobEffectEvent.Added event)
    {
    }

    private boolean isJumpEffectAvailable(IPlayer player, IArmor armor)
    {
        var boots = player.getEntity().getItemBySlot(EquipmentSlot.FEET);

        int bootsDamage = boots.getDamageValue();
        int maxBootsDamageToJump = boots.getMaxDamage() / 2;

        return player.isArmorElementPutOn(this, EquipmentSlot.FEET)
                && bootsDamage < maxBootsDamageToJump;
    }

    private boolean canFly()
    {
        if (!player.isFullArmorSetPutOn(this)) return false;

        var playerEntity = player.getEntity();
        if (playerEntity.hasEffect(MobEffects.SLOW_FALLING)) return false;
        if (Utils.instance.dimension.isNether(playerEntity.level().dimension()) || playerEntity.isInWaterRainOrBubble()) return false;

        var mainHandItemStack = playerEntity.getMainHandItem();
        if (mainHandItemStack.is(Items.FIREWORK_ROCKET)) return false;
        var offHandItemStack = playerEntity.getOffhandItem();
        if (offHandItemStack.is(Items.FIREWORK_ROCKET)) return false;

        var chestplate = playerEntity.getItemBySlot(EquipmentSlot.CHEST);
        int maxDamageToFly = chestplate.getMaxDamage() / 2;
        int chestDamage = chestplate.getDamageValue();

        return chestDamage < maxDamageToFly;
    }

    private void updateSlowFallingEffect()
    {
        if (!isSlowFallingActivatedOnGround && slowFallingEffect.isOn() && player.getEntity().onGround())
        {
            slowFallingEffect.trySwitchOff();
            isSlowFallingActivatedOnGround = true;
        }
    }

    private boolean isSpeedAvailable(IPlayer player, IArmor armor)
    {
        var boots = player.getEntity().getItemBySlot(EquipmentSlot.FEET);

        return player.isArmorElementPutOn(this, EquipmentSlot.FEET)
                && !player.isEffectActive(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT)
//                && !player.isEffectActive(PotionRegistry.ENERGY_CRYSTAL_EFFECT)
                && boots.getDamageValue() < boots.getMaxDamage() / 2;
    }
}
