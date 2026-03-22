package com.pekar.angelblock.events.effect;

import com.pekar.angelblock.events.armor.IPlayerArmor;
import com.pekar.angelblock.events.effect.base.ArmorEffectSetup;
import com.pekar.angelblock.events.effect.base.IArmorEffectSetup;
import com.pekar.angelblock.events.effect.base.IArmorEffectWithOptions;
import com.pekar.angelblock.events.effect.base.IPlayerArmorEffectSetup;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

public class PlayerArmorEffectSetup<E extends IArmorEffectWithOptions<IPlayer, IPlayerArmor>> extends ArmorEffectSetup<E, IPlayer, IPlayerArmor> implements IPlayerArmorEffectSetup<E>
{
    public PlayerArmorEffectSetup(E effect)
    {
        super(effect);
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnHelmetWithDetector()
    {
        effect.setupAvailability((player, armor) -> player.isHelmetModifiedWithDetector(armor) && !player.getPlayerEntity().hasEffect(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT));
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnHelmetWithNightVision()
    {
        effect.setupAvailability((player, armor) -> player.isHelmetModifiedWithNightVision(armor) && !player.getPlayerEntity().hasEffect(PotionRegistry.ELDER_GUARDIAN_EYE_EFFECT));
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnBootsWithJumpBooster()
    {
        effect.setupAvailability(IPlayer::areBootsModifiedWithJumpBooster);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnBootsWithSeaPower()
    {
        effect.setupAvailability(this::areBootsModifiedWithSeaPowerAndNoHeavyJump);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnChestPlateWithStrengthBooster()
    {
        effect.setupAvailability(IPlayer::isChestPlateModifiedWithStrengthBooster);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnChestPlateWithSlowFalling()
    {
        effect.setupAvailability(IPlayer::isChestPlateModifiedWithSlowFalling);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnLeggingsWithHealthRegenerator()
    {
        effect.setupAvailability(IPlayer::areLeggingsModifiedWithHealthRegenerator);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnFullArmorSet()
    {
        effect.setupAvailability(IPlayer::isFullArmorSetPutOn);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableOnAnyArmorElement()
    {
        effect.setupAvailability(IPlayer::isAnyArmorElementPutOn);
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableIfSlotSet(EquipmentSlot slot)
    {
        effect.setupAvailability((player1, armor1) -> player1.isArmorElementPutOn(armor1, slot));
        return this;
    }

    @Override
    public IArmorEffectSetup<E, IPlayer, IPlayerArmor> availableIfSlotsSet(EquipmentSlot... slots)
    {
        effect.setupAvailability((player1, armor1) -> player1.isAllArmorElementsPutOn(armor1, slots));
        return this;
    }

    private boolean areBootsModifiedWithSeaPowerAndNoHeavyJump(IPlayer player, IPlayerArmor playerArmor)
    {
        return player.areBootsModifiedWithSeaPower(playerArmor)
                && !player.isEffectActive(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT)
                && !(player.hasArmorEffect(MobEffects.SLOWNESS));
    }
}
