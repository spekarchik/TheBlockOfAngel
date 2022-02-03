package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class FlyingArmor extends Armor
{
    private final SwitchingEffectSynchronizer jumpBoostEffect;
    private final IArmorEffect slowFallingEffect;

    public FlyingArmor(IPlayer player)
    {
        super(player);

        slowFallingEffect = new SlowFallingPermanentArmorEffect(player, this);

        var speedEffect = new SpeedSwitchingEffect(player, this, 1);
        var jumpBoostEffect = new JumpBoostArmorEffect(player, this, 24);
        this.jumpBoostEffect = new SwitchingEffectSynchronizer(jumpBoostEffect);
        this.jumpBoostEffect.addDependentEffect(speedEffect);
    }

    @Override
    public String getHelmetName()
    {
        return ArmorRegistry.FLYING_HELMET.get().getRegistryName().getPath();
    }

    @Override
    public String getChestPlateName()
    {
        return ArmorRegistry.FLYING_CHESTPLATE.get().getRegistryName().getPath();
    }

    @Override
    public String getLeggingsName()
    {
        return ArmorRegistry.FLYING_LEGGINGS.get().getRegistryName().getPath();
    }

    @Override
    public String getBootsName()
    {
        return ArmorRegistry.FLYING_BOOTS.get().getRegistryName().getPath();
    }

    @Override
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        jumpBoostEffect.updateSwitchState();
    }

    @Override
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
    }

    @Override
    public void onLivingAttackEvent(LivingAttackEvent event)
    {
    }

    @Override
    public void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        jumpBoostEffect.updateEffectAvailability();
        slowFallingEffect.updateEffectAvailability();

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
            jumpBoostEffect.trySwitch();
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
    }

    @Override
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
    }

    @Override
    public void onBeingInWater()
    {
    }

    @Override
    public void onCreeperCheck()
    {
    }

    private void updatePotionEffects()
    {
        jumpBoostEffect.updateEffectActivity();
        slowFallingEffect.updateEffectActivity();
    }
}
