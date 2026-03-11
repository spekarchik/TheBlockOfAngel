package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.armor.ModHumanoidArmor;
import com.pekar.angelblock.armor.PlayerArmorType;
import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.network.packets.CreeperDetectedPacket;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.utils.TriPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Set;
import java.util.function.BiConsumer;

abstract class PlayerArmor extends ArmorBase implements IPlayerArmor
{
    protected final IPlayer player;
    private final CreeperDetectedPacket creeperDetectedPacket = new CreeperDetectedPacket();
    private int creeperDetectedCounter = 0;
    private boolean needUpdateStatesAfterLogin = false;
    private final PlayerArmorType armorType;

    private final int MAX_ARMOR_TYPE_DEFENSE;

    private static final double CREEPER_NOTIFY_DISTANCE = 17.0;
    private static final double CREEPER_AGRY_DISTANCE = 3.0;
    private static final int CREEPER_GLOWING_EFFECT_DURATION = 20;
    protected static final float EXHAUSTION_INCREMENT = 0.2F;
    protected static final int UNDER_RAIN_REGENERATION_EFFECT_DURATION = 100;

    protected final TriPredicate<Block, BlockPos, Level> isIcePredicate = (block, pos, level) ->
    {
        var belowBlockState = level.getBlockState(pos.below());
        return block == Blocks.ICE && (belowBlockState.isAir() || utils.blocks.types.isLiquid(belowBlockState.getBlock()));
    };

    protected final TriPredicate<Block, BlockPos, Level> isCrackedBlockPredicate = (block, pos, level) ->
            block == BlockRegistry.CRACKED_ENDSTONE.get() || block == BlockRegistry.CRACKED_OBSIDIAN.get();

    protected final TriPredicate<Block, BlockPos, Level> isNetherrackPredicate = (block, pos, level) ->
            block == Blocks.NETHERRACK;

    protected final BiConsumer<ServerPlayer, BlockPos> playIceBreakSound = (player, pos) ->
    {
        player.level().playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS);
        player.level().playSound(null, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS);
    };

    protected final BiConsumer<ServerPlayer, BlockPos> playCrackedBlockBreakSound = (player, pos) ->
    {
        player.level().playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 1F, 2F);
    };

    protected PlayerArmor(IPlayer player, PlayerArmorType armorType)
    {
        this.player = player;
        this.armorType = armorType;

        var materialDefense = armorType.getMaterial().getMaterial().defense();
        int maxDefense = 0;
        for (var armorItemType : utils.player.getArmorTypes())
        {
            int armorTypeDefense = materialDefense.get(armorItemType);
            maxDefense += armorTypeDefense;
        }

        MAX_ARMOR_TYPE_DEFENSE = maxDefense;
    }

    protected abstract void updateAvailability();
    protected abstract void updateEffectStates();
    protected abstract void updateActivityForHeadSlot();
    protected abstract void updateActivityForFeetSlot();
    protected abstract void updateActivityForLegsSlot();
    protected abstract void updateActivityForChestSlot();
    protected void updateActivityForHandSlots() {}
    protected abstract void updateActivity(EquipmentSlot slot);

    protected void onEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        // can be overriden
    }

    protected void onLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        // can be overriden
    }

    @Override
    public final PlayerArmorType getArmorType()
    {
        return armorType;
    }

    @Override
    public final void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        needUpdateStatesAfterLogin = true;
        onLogin(event);
    }

    @Override
    public final void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event)
    {
        updateAvailability();

        if (needUpdateStatesAfterLogin)
        {
            updateEffectStates();
            needUpdateStatesAfterLogin = false;
        }

        switch (event.getSlot())
        {
            case CHEST ->
                    updateActivityForChestSlot();

            case LEGS ->
                    updateActivityForLegsSlot();

            case FEET ->
                    updateActivityForFeetSlot();

            case HEAD ->
                    updateActivityForHeadSlot();

            case MAINHAND, OFFHAND ->
            {
                updateActivityForHeadSlot();
                updateActivityForChestSlot();
                updateActivityForLegsSlot();
                updateActivityForFeetSlot();

                updateActivityForHandSlots();
            }
        }

        updateActivity(event.getSlot());
        onEquipmentChangeEvent(event);
    }

    protected boolean isSlowMovementAffected(LivingEntity entity)
    {
        boolean isZombie = entity instanceof Zombie;
        boolean isSkeleton = entity instanceof Skeleton;
        boolean isWitch = entity instanceof Witch;
        boolean isIllager = entity instanceof AbstractIllager;

        return isZombie || isSkeleton || isIllager || isWitch;
    }

    protected void detectCreepers(boolean detect, boolean makeNeutral)
    {
        if (!detect && !makeNeutral) return;

        var entityPlayer = player.getPlayerEntity();
        var level = entityPlayer.level();

        if (level.isClientSide()) return;

        var monsters = level.getEntitiesOfClass(Creeper.class, entityPlayer.getBoundingBox().inflate(CREEPER_NOTIFY_DISTANCE));

        if (!monsters.isEmpty())
        {
            for (var entity : monsters)
            {
                if (detect && (!entity.hasEffect(MobEffects.GLOWING) || entity.getEffect(MobEffects.GLOWING).getDuration() < CREEPER_GLOWING_EFFECT_DURATION / 2))
                {
                    var potionEffect = new MobEffectInstance(MobEffects.GLOWING, CREEPER_GLOWING_EFFECT_DURATION, 0 /*amplifier*/, false /*ambient*/, false /*visible*/, false /*showIcon*/);
                    entity.addEffect(potionEffect);
                }
                if (makeNeutral && entity.distanceTo(entityPlayer) >= CREEPER_AGRY_DISTANCE && entity.getTarget() == entityPlayer)
                {
                    entity.setTarget(null);
                }
            }

            if (detect)
            {
                if (creeperDetectedCounter > 3)
                    creeperDetectedCounter = 0;
                else if (creeperDetectedCounter++ == 0)
                    creeperDetectedPacket.sendToPlayer((ServerPlayer) entityPlayer);
            }
        }
        else
        {
            if (detect)
                creeperDetectedCounter = 0;
        }
    }

    protected void breakBlockUnderPlayer(ServerPlayer player, boolean doRandomly, TriPredicate<Block, BlockPos, Level> blockUnderPlayer, BlockState stateToTransformTo, BiConsumer<ServerPlayer, BlockPos> runOnSucceeded, int chanceToAvoidBreaking)
    {
        var level = player.level();
        if (level.isClientSide()) return;
        var posBelow = BlockPos.containing(player.getX(), player.getY() - 0.5, player.getZ());

        var block1 = level.getBlockState(posBelow).getBlock();
        if (!blockUnderPlayer.test(block1, posBelow, level))
        {
            return;
        }

        var randomSource = RandomSource.create();
        int heavyArmorSlots = 0;
        for (var slot : utils.player.getArmorInSlots(player))
        {
            if (slot.getItem() instanceof ModHumanoidArmor modArmor && modArmor.getArmorType() == getArmorType())
                heavyArmorSlots++;
        }
        int rnd = randomSource.nextInt(chanceToAvoidBreaking);
        boolean shouldIceBeBroken = rnd <= heavyArmorSlots * heavyArmorSlots;
        if (doRandomly && !shouldIceBeBroken) return;

        for (int x = posBelow.getX() - 1; x <= posBelow.getX() + 1; x++)
            for (int z = posBelow.getZ() - 1; z <= posBelow.getZ() + 1; z++)
            {
                var currentPos = new BlockPos(x, posBelow.getY(), z);
                block1 = level.getBlockState(currentPos).getBlock();
                if (blockUnderPlayer.test(block1, currentPos, level))
                {
                    if (stateToTransformTo.isAir())
                        level.destroyBlock(currentPos, true, player);
                    else
                        level.setBlock(currentPos, stateToTransformTo, Block.UPDATE_ALL_IMMEDIATE);
                }
            }

        runOnSucceeded.accept(player, posBelow);
    }

    protected void restorePlayerHealth(Player entityPlayer)
    {
        var maxHealthAttr = entityPlayer.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttr != null)
        {
            var maxBaseHealth = maxHealthAttr.getBaseValue();
            if (entityPlayer.getHealth() >= maxBaseHealth)
            {
                entityPlayer.setHealth(entityPlayer.getMaxHealth());
            }
        }
    }

    protected boolean playerNeedsToRestoreHealth(Player entityPlayer, EquipmentSlot slotChanged, ItemStack itemFrom, ItemStack itemTo)
    {
        if (entityPlayer.isCreative()) return false;

        if (itemFrom.getItem().equals(itemTo.getItem())) return false;

        if (!player.isArmorElementPutOn(this, EquipmentSlot.LEGS) || !player.areLeggingsModifiedWithHealthRegenerator(this))
            return false;

        if (slotChanged == EquipmentSlot.LEGS) return true;

        return itemFrom.is(Items.MILK_BUCKET) && itemTo.is(Items.BUCKET);
    }

    @Override
    public void onArmorHurtEvent(ArmorHurtEvent event)
    {
        utils.attributeModifiers.updateArmorAttributeModifier(player.getPlayerEntity());

        for (var slot : utils.player.getArmorSlots())
        {
            var stack = player.getPlayerEntity().getItemBySlot(slot);
            if (stack.isEmpty() || !(stack.getItem() instanceof ModHumanoidArmor modArmor) || modArmor.getArmorType() != getArmorType())
            {
                continue;
            }

            var maxDamage = stack.getMaxDamage();
            var durability = maxDamage - stack.getDamageValue();
            float amount = event.getNewDamage(slot);

            if (amount >= durability)
            {
                event.setNewDamage(slot, durability - 1);
            }
        }
    }

    protected void setAdjustedArmorDamage(ArmorHurtEvent event, float damageMultiplier)
    {
        for (var entry : event.getArmorMap().entrySet())
        {
            var armor = entry.getValue().armorItemStack;
            if (armor.isEmpty()) continue;
            if (!(armor.getItem() instanceof ModHumanoidArmor modArmor)) continue;
            if (modArmor.getArmorType() != getArmorType()) continue;

            var adjustedArmorDamage = event.getNewDamage(entry.getKey()) * damageMultiplier;
            event.setNewDamage(entry.getKey(), adjustedArmorDamage);
        }
    }

    // for tests
    protected void damageArmor(boolean damage)
    {
        for (var item : utils.player.getArmorInSlots(player.getPlayerEntity()))
        {
            var damageValue = damage ? item.getMaxDamage() - 1 : 0;
            item.setDamageValue(damageValue);
        }
    }

    protected void damageMainHandItem()
    {
        var itemStack = player.getPlayerEntity().getMainHandItem();
        var maxDamage = itemStack.getMaxDamage();
        var newDamage = maxDamage * 2 / 3 - 2;
        itemStack.setDamageValue(newDamage);
    }

    protected void switchArmorDamage()
    {
        boolean isDamaged = false;
        for (var slot : utils.player.getArmorInSlots(player.getPlayerEntity()))
        {
            if (!slot.isEmpty() && slot.getItem() instanceof ModHumanoidArmor modArmor)
            {
                if (modArmor.isDamaged(slot)) isDamaged = true;
            }
        }

        for (var slot : utils.player.getArmorInSlots(player.getPlayerEntity()))
        {
            if (!slot.isEmpty() && slot.getItem() instanceof ModHumanoidArmor modArmor)
            {
                damageArmor(!isDamaged);
            }
        }
    }
    protected boolean availableOnBootsWithNoHeavyJump(IPlayer player, IPlayerArmor armor)
    {
        var playerEntity = player.getPlayerEntity();
        if (playerEntity.hasEffect(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT))
            return false;

        return player.isArmorElementPutOn(armor, EquipmentSlot.FEET);
    }

    protected float getArmorTypeDefenseRatio()
    {
        float armorTypeDefense = 0;
        var playerEntity = player.getPlayerEntity();

        for (var armorType : utils.player.getArmorTypes())
        {
            var armorItem = playerEntity.getItemBySlot(armorType.getSlot());
            if (armorItem.isEmpty()) continue;

            if (!(armorItem.getItem() instanceof ModHumanoidArmor modArmor)) continue;
            if (modArmor.isBroken(armorItem)) continue;
            if (modArmor.getArmorType() != getArmorType()) continue;

            float durabilityPercent = modArmor.getDurabilityPercent(armorItem);

            armorTypeDefense += getArmorType().getMaterial().getMaterial().defense().get(armorType) * durabilityPercent;
        }

        return armorTypeDefense / playerEntity.getArmorValue();
    }

    protected final float getFullArmorSetDefense()
    {
        return MAX_ARMOR_TYPE_DEFENSE;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof PlayerArmor armor))
        {
            return false;
        }
        return getArmorType() == armor.getArmorType();
    }

    @Override
    public int hashCode()
    {
        return getArmorType().hashCode();
    }
}
