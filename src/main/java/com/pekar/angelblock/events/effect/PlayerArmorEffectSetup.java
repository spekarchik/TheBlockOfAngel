package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.effect.base.ArmorEffectSetup;
import com.pekar.angelblock.events.effect.base.IArmorEffectSetup;
import com.pekar.angelblock.events.effect.base.IArmorEffectWithOptions;
import com.pekar.angelblock.events.effect.base.IPlayerArmorEffectSetup;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.world.entity.EquipmentSlot;

public class PlayerArmorEffectSetup<E extends IArmorEffectWithOptions<IPlayer>> extends ArmorEffectSetup<E, IPlayer> implements IPlayerArmorEffectSetup<E>
{
    public PlayerArmorEffectSetup(E effect)
    {
        super(effect);
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableOnHelmetWithDetector()
    {
        effect.setupAvailability((player, armor) -> player.isHelmetModifiedWithDetector(armor) && !player.getPlayerEntity().hasEffect(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT));
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableOnHelmetWithNightVision()
    {
        effect.setupAvailability((player, armor) -> player.isHelmetModifiedWithNightVision(armor) && !player.getPlayerEntity().hasEffect(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT));
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableOnBootsWithJumpBooster()
    {
        effect.setupAvailability(IPlayer::areBootsModifiedWithJumpBooster);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableOnBootsWithSeaPower()
    {
        effect.setupAvailability(IPlayer::areBootsModifiedWithSeaPower);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableOnChestPlateWithStrengthBooster()
    {
        effect.setupAvailability(IPlayer::isChestPlateModifiedWithStrengthBooster);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableOnChestPlateWithSlowFalling()
    {
        effect.setupAvailability(IPlayer::isChestPlateModifiedWithSlowFalling);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableOnLeggingsWithHealthRegenerator()
    {
        effect.setupAvailability(IPlayer::areLeggingsModifiedWithHealthRegenerator);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableOnFullArmorSet()
    {
        effect.setupAvailability(IPlayer::isFullArmorSetPutOn);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableOnAnyArmorElement()
    {
        effect.setupAvailability(IPlayer::isAnyArmorElementPutOn);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableIfSlotSet(EquipmentSlot slot)
    {
        effect.setupAvailability((player1, armor1) -> player1.isArmorElementPutOn(armor1, slot));
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer> availableIfSlotsSet(EquipmentSlot... slots)
    {
        effect.setupAvailability((player1, armor1) -> player1.isAllArmorElementsPutOn(armor1, slots));
        return this;
    }
}
