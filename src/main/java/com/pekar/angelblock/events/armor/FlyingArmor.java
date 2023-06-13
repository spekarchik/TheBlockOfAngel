package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class FlyingArmor extends Armor
{
    private final SwitchingEffectSynchronizer jumpBoostEffect;
    private final IArmorEffect levitationSwitchingEffect;

    public FlyingArmor(IPlayer player)
    {
        super(player);

        levitationSwitchingEffect = new LevitationSwitchingEffect(player, this, 250).setupAvailability(this::isLevitationSwitchingEffectAvailable);

        var speedEffect = new SpeedSwitchingEffect(player, this, 1);
        speedEffect.setupAvailability(this::isJumpEffectAvailable);
        var jumpBoostEffect = new JumpBoostArmorEffect(player, this, 24);
        jumpBoostEffect.setupAvailability(this::isJumpEffectAvailable);
        this.jumpBoostEffect = new SwitchingEffectSynchronizer(jumpBoostEffect);
        this.jumpBoostEffect.addDependentEffect(speedEffect);
    }

    @Override
    public String getModelName()
    {
        return ArmorRegistry.FLYING_BOOTS.get().getArmorModelName();
    }

    @Override
    public int getPriority()
    {
        return 1;
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        jumpBoostEffect.updateSwitchState();

        if (!player.isNether())
        {
            levitationSwitchingEffect.updateSwitchState();
        }
    }

    @Override
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
        if (event.getSource().is(DamageTypes.FALL))
        {
            event.setAmount(event.getAmount() * 0.1F);
        }
    }

    @Override
    public void onLivingAttackEvent(LivingAttackEvent event)
    {
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        jumpBoostEffect.updateEffectAvailability();
        levitationSwitchingEffect.updateEffectAvailability();

        levitationSwitchingEffect.updateSwitchState();
        updatePotionEffects();
    }

    @Override
    public void onLivingJumpEvent(LivingEvent.LivingJumpEvent event)
    {
    }

    @Override
    public void onLivingFallEvent(LivingFallEvent event)
    {
    }

    @Override
    public void onKeyInputEvent(String pressedKeyDescription)
    {
        if (pressedKeyDescription.equals(KeyRegistry.JUMP_BOOST.getName()))
        {
            if (!player.isNether())
            {
                jumpBoostEffect.trySwitch();
            }
        }

        if (pressedKeyDescription.equals(KeyRegistry.LEVITATION.getName()))
        {
            if (!player.isNether())
            {
                levitationSwitchingEffect.trySwitch();
            }
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
    }

    @Override
    public void onPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        jumpBoostEffect.updateEffectAvailability();
        levitationSwitchingEffect.updateEffectAvailability();

        updatePotionEffects();
    }

    @Override
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        if (player.getEntity().isInWaterRainOrBubble() || jumpBoostEffect.isEffectOn())
        {
            event.setNewSpeed(event.getOriginalSpeed() * 0.2f);
        }
    }

    @Override
    public void onBeingInWater()
    {
    }

    @Override
    public void onBeingUnderRain()
    {
    }

    @Override
    public void onCreeperCheck()
    {
    }

    private void updatePotionEffects()
    {
        jumpBoostEffect.updateEffectActivity();
        levitationSwitchingEffect.updateEffectActivity();
    }

    private boolean isLevitationSwitchingEffectAvailable(IPlayer player, IArmor armor)
    {
        return !player.isNether() && player.isFullArmorSetPutOn(this);
    }

    private boolean isJumpEffectAvailable(IPlayer player, IArmor armor)
    {
        var boots = player.getEntity().getItemBySlot(EquipmentSlot.FEET);
        var leggings = player.getEntity().getItemBySlot(EquipmentSlot.LEGS);

        int bootsDamage = boots.getDamageValue();
        int leggingsDamage = leggings.getDamageValue();
        int maxBootsDamageToJump = boots.getMaxDamage() / 2;
        int maxLeggingsDamageToJump = leggings.getMaxDamage() / 2;

        return !player.isNether() && player.isFullArmorSetPutOn(this)
                && bootsDamage < maxBootsDamageToJump && leggingsDamage < maxLeggingsDamageToJump;
    }
}
