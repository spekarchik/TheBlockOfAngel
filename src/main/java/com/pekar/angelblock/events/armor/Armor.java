package com.pekar.angelblock.events.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.armor.ModArmor;
import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.events.player.IPlayer;
import com.pekar.angelblock.network.packets.CreeperDetectedPacket;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.utils.TriPredicate;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Pufferfish;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

abstract class Armor implements IArmor
{
    protected final IPlayer player;
    private final Set<EquipmentSlot> equipmentSlots = new HashSet<>();
    private final CreeperDetectedPacket creeperDetectedPacket = new CreeperDetectedPacket();
    private int creeperDetectedCounter = 0;
    private boolean needUpdateStatesAfterLogin = false;

    private static final double CREEPER_NOTIFY_DISTANCE = 17.0;
    private static final double CREEPER_AGRY_DISTANCE = 3.0;
    private static final int CREEPER_GLOWING_EFFECT_DURATION = 1200;
    protected static final float EXHAUSTION_INCREMENT = 0.5F;
    protected static final int UNDER_RAIN_REGENERATION_EFFECT_DURATION = 100;

    protected final TriPredicate<Block, BlockPos, Level> isIcePredicate = (block, pos, level) ->
    {
        var belowBlockState = level.getBlockState(pos.below());
        return block == Blocks.ICE && (belowBlockState.isAir() || Utils.instance.blocks.types.isLiquid(belowBlockState.getBlock()));
    };

    protected final TriPredicate<Block, BlockPos, Level> isCrackedBlockPredicate = (block, pos, level) ->
            block == BlockRegistry.CRACKED_ENDSTONE.get() || block == BlockRegistry.CRACKED_OBSIDIAN.get();

    protected final TriPredicate<Block, BlockPos, Level> isNetherrackPredicate = (block, pos, level) ->
            block == Blocks.NETHERRACK;

    protected final Consumer<ServerPlayer> playIceBreakSound = (player) ->
    {
        new PlaySoundPacket(SoundEvents.GLASS_BREAK).sendToPlayer(player);
        new PlaySoundPacket(SoundEvents.GENERIC_SPLASH).sendToPlayer(player);
    };

    protected final Consumer<ServerPlayer> playCrackedBlockBreakSound = (player) ->
    {
        new PlaySoundPacket(SoundEvents.TURTLE_EGG_BREAK, 2.0F).sendToPlayer(player);
    };

    protected Armor(IPlayer player)
    {
        this.player = player;

        equipmentSlots.add(EquipmentSlot.FEET);
        equipmentSlots.add(EquipmentSlot.LEGS);
        equipmentSlots.add(EquipmentSlot.CHEST);
        equipmentSlots.add(EquipmentSlot.HEAD);
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

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Armor)) return false;
        Armor armor = (Armor) obj;
        return getFamilyName().equals(armor.getFamilyName());
    }

    @Override
    public int hashCode()
    {
        return getFamilyName().hashCode();
    }

    protected boolean isFreezeDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.FREEZE);
    }

    protected boolean isFireDamage(DamageSource damageSource)
    {
        boolean isDamagedByInFire = damageSource.is(DamageTypes.IN_FIRE);
        boolean isDamagedByOnFire = damageSource.is(DamageTypes.ON_FIRE);
        return isDamagedByInFire || isDamagedByOnFire;
    }

    protected boolean isLavaDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.LAVA);
    }

    protected boolean isHotFloorDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.HOT_FLOOR);
    }

    protected boolean isFireOrLavaDamage(DamageSource damageSource)
    {
        return isFireDamage(damageSource) || isLavaDamage(damageSource);
    }

    protected boolean isFireOrLavaOrHotFloorDamage(DamageSource damageSource)
    {
        return isHotFloorDamage(damageSource) || isFireOrLavaDamage(damageSource);
    }

    protected boolean isThornOrMagicDamage(DamageSource damageSource)
    {
        boolean isCactus = damageSource.is(DamageTypes.CACTUS);
        boolean isSweetBerryBush = damageSource.is(DamageTypes.SWEET_BERRY_BUSH);
        boolean isPufferFish = damageSource.getEntity() instanceof Pufferfish;
        boolean isMagic = damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypes.INDIRECT_MAGIC);

        return isCactus || isSweetBerryBush || isMagic || isPufferFish;
    }

    protected boolean isLightningBoltDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.LIGHTNING_BOLT);
    }

    protected boolean isExplosionDamage(DamageSource damageSource)
    {
        return damageSource.is(DamageTypes.EXPLOSION);
    }

    protected boolean isBiting(Entity entity)
    {
        boolean isSilverfish = entity instanceof Silverfish;
        boolean isEndermite = entity instanceof Endermite;
        boolean isSpider = entity instanceof Spider;
        boolean isBee = entity instanceof Bee;
        return isSilverfish || isEndermite || isSpider || isBee;
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

        var entityPlayer = player.getEntity();
        var level = entityPlayer.level();

        if (level.isClientSide()) return;

        var monsters = level.getEntitiesOfClass(Creeper.class, entityPlayer.getBoundingBox().inflate(CREEPER_NOTIFY_DISTANCE));

        if (!monsters.isEmpty())
        {
            for (var entity : monsters)
            {
                if (detect && !entity.hasEffect(MobEffects.GLOWING))
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

    protected void breakBlockUnderPlayer(ServerPlayer player, boolean doRandomly, TriPredicate<Block, BlockPos, Level> blockUnderPlayer, BlockState stateToTransformTo, Consumer<ServerPlayer> runOnSucceeded, int chanceToAvoidBreaking)
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
        int diamithicArmorSlots = 0;
        for (var slot : player.getArmorSlots())
        {
            if (slot.getItem() instanceof ModArmor modArmor && modArmor.getArmorFamilyName().equals(getFamilyName()))
                diamithicArmorSlots++;
        }
        int rnd = randomSource.nextInt(chanceToAvoidBreaking);
        boolean shouldIceBeBroken = rnd <= diamithicArmorSlots * diamithicArmorSlots;
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
                        level.setBlock(currentPos, stateToTransformTo, 11);
                }
            }

        runOnSucceeded.accept(player);
    }

    // for tests
    protected void damageArmor(boolean damage)
    {
        for (var item : player.getEntity().getArmorSlots())
        {
            var damageValue = damage ? item.getMaxDamage() - 1 : 0;
            item.setDamageValue(damageValue);
        }
    }

    protected final boolean isVulnerable(DamageSource damageSource)
    {
        var vulnerabilities = TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Main.MODID, getVulnerabilitiesTagName()));
        return damageSource.is(vulnerabilities);
    }

    protected String getVulnerabilitiesTagName()
    {
        return getFamilyName() + "_armor_vulnerabilities";
    }
}
