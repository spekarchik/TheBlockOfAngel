package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ArmorRegistry;
import com.pekar.angelblock.events.effect.*;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.keybinds.KeyRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class FlyingArmor extends Armor
{
    private final SwitchingEffectSynchronizer jumpBoostEffect;
    private final IArmorEffect slowFallingEffect;
    private final IArmorEffect levitationEffect;

    public FlyingArmor(IPlayer player)
    {
        super(player);

        slowFallingEffect = new SlowFallingSwitchingEffect(player, this);
        levitationEffect = new LevitationTemporaryEffect(player, this, 1, 200);

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
        levitationEffect.updateSwitchState();
        slowFallingEffect.updateSwitchState();
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
        levitationEffect.updateEffectAvailability();

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
            if (player.isOverworld())
            {
                jumpBoostEffect.trySwitch();
            }
            else if (player.isEnd())
            {
                levitationEffect.trySwitchOn();
            }
        }
    }

    @Override
    public void onEntityTravelToDimensionEvent(EntityTravelToDimensionEvent event)
    {
        var destDimension = event.getDimension();
        if (isNether(destDimension))
        {
            slowFallingEffect.trySwitchOff();
            jumpBoostEffect.trySwitchOff();
            levitationEffect.trySwitchOff();
        }
        else
        {
            slowFallingEffect.trySwitchOn();
        }
    }

    @Override
    public void onBreakSpeed(PlayerEvent.BreakSpeed event)
    {
        event.setNewSpeed(0);
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
        levitationEffect.updateEffectActivity();
    }

    private boolean isOverworld(ResourceKey<Level> dimension)
    {
        return dimension.location().equals(Level.OVERWORLD.location());
    }

    private boolean isNether(ResourceKey<Level> dimension)
    {
        return dimension.location().equals(Level.NETHER.location());
    }

    private boolean isEnd(ResourceKey<Level> dimension)
    {
        return dimension.location().equals(Level.END.location());
    }
}
