package com.pekar.angelblock.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.pekar.angelblock.Main.MODID;
import static com.pekar.angelblock.utils.Resources.createResourceLocation;

public class Player
{

    public final PlayerConditions conditions = new PlayerConditions();

    Player()
    {

    }

    public Set<ArmorItem.Type> getArmorTypes()
    {
        return Set.of(ArmorItem.Type.HELMET, ArmorItem.Type.CHESTPLATE, ArmorItem.Type.LEGGINGS, ArmorItem.Type.BOOTS);
    }

    public List<ItemStack> getArmorInSlots(LivingEntity entity)
    {
        return Arrays.asList(
                entity.getItemBySlot(EquipmentSlot.HEAD),
                entity.getItemBySlot(EquipmentSlot.CHEST),
                entity.getItemBySlot(EquipmentSlot.LEGS),
                entity.getItemBySlot(EquipmentSlot.FEET)
        );
    }

    public List<EquipmentSlot> getArmorSlots()
    {
        return Arrays.asList(
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        );
    }

    public void awardAdvancement(ServerPlayer serverPlayer, ServerLevel serverLevel, String name, String criterion)
    {
        var advancements = serverPlayer.getAdvancements();
        var advancement = serverLevel.getServer().getAdvancements().get(createResourceLocation(MODID, name));
        if (advancement != null)
        {
            advancements.award(advancement, criterion);
        }
    }

    public Direction getDirection(LivingEntity entityLiving, BlockPos pos)
    {
        BlockPos entityPos = entityLiving.blockPosition();
        int entityX = entityPos.getX(), entityY = entityPos.getY(), entityZ = entityPos.getZ();

        if (pos.getY() > entityY + 2)
        {
            return Direction.UP;
        }
        else if (entityY - pos.getY() > 1)
        {
            return Direction.DOWN;
        }
        else if (pos.getY() < entityY)
        {
            if (Math.abs(pos.getX() - entityX) < 2 && Math.abs(pos.getZ() - entityZ) < 2)
            {
                return Direction.DOWN;
            }
        }

        return entityLiving.getDirection();
    }

    public Direction getDirectionForShovel(LivingEntity entityLiving, BlockPos pos)
    {
        BlockPos entityPos = entityLiving.blockPosition();
        int entityY = entityPos.getY();

        if (pos.getY() > entityY + 2)
        {
            return Direction.UP;
        }
        else if (entityY >= pos.getY())
        {
            return Direction.DOWN;
        }

        return entityLiving.getDirection();
    }

    public boolean destroyBlockByMainHandTool(Level level, BlockPos pos, LivingEntity entityLiving, BlockState blockState, Block block)
    {
        if (level.isClientSide()) return false;

        //                Registry<Enchantment> enchantmentRegistry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
//                Holder<Enchantment> fortuneHolder = enchantmentRegistry.getHolderOrThrow(Enchantments.FORTUNE);
//                int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(fortuneHolder, mainHandItemStack);
//                int exp = blockState.getExpDrop(level, pos, null, entityLiving, mainHandItemStack);
//                exp = applyFortuneBonus(exp, fortuneLevel, 1.7);
//                block.popExperience((ServerLevel) level, pos, exp);
        final int recursionLeft = 512;
        //return level.destroyBlock(pos, true, entityLiving, recursionLeft);
        return destroyBlock(pos, true, entityLiving, level, entityLiving.getMainHandItem(), blockState, recursionLeft);
    }

    // See Level.destroyBlock()
    private boolean destroyBlock(BlockPos pos, boolean dropBlock, @NotNull Entity entity, Level level, ItemStack tool, BlockState blockState, int recursionLeft)
    {
        BlockState blockstate = level.getBlockState(pos);

        if (blockstate.isAir())
        {
            return false;
        }
        else
        {
            FluidState fluidstate = level.getFluidState(pos);
            if (!(blockstate.getBlock() instanceof BaseFireBlock))
            {
                level.levelEvent(2001, pos, Block.getId(blockstate));
            }

            BlockEntity blockEntity = blockstate.hasBlockEntity() ? level.getBlockEntity(pos) : null;
            if (dropBlock)
            {
                Block.dropResources(blockstate, level, pos, blockEntity, entity, tool);
            }

            boolean flag = level.setBlock(pos, fluidstate.createLegacyBlock(), 3, recursionLeft);
            if (flag)
            {
                if (entity instanceof ServerPlayer)
                {
                    var server = level.getServer();
                    if (server != null && !hasSilkTouch(tool, (ServerLevel) level))
                    {
                        int exp = blockState.getExpDrop(level, pos, blockEntity, entity, tool);
                        if (exp > 0)
                        {
                            blockState.getBlock().popExperience((ServerLevel) level, pos, exp);
                        }
                    }
                }

                level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(entity, blockstate));
            }

            return flag;
        }
    }

    public static boolean hasSilkTouch(ItemStack stack, ServerLevel level)
    {
        return level.getServer().registryAccess()
                .lookup(Registries.ENCHANTMENT)
                .flatMap(lookup -> lookup.get(Enchantments.SILK_TOUCH))
                .map(holder -> EnchantmentHelper.getItemEnchantmentLevel(holder, stack) > 0)
                .orElse(false); // если чара нет — вернуть false
    }

    public void causePlayerExhaustion(net.minecraft.world.entity.player.Player player, int multiplier)
    {
        final float SATURATION_FACTOR = 0.5F;
        final float EXHAUSTION = 0.5F;

        if (player != null && !player.level().isClientSide())
        {
            var foodData = player.getFoodData();

            if (foodData.getSaturationLevel() > 0)
            {
                float sat = foodData.getSaturationLevel();
                foodData.setSaturation(sat * (float)Math.pow(SATURATION_FACTOR, multiplier));
            }

            player.causeFoodExhaustion(EXHAUSTION * multiplier);
        }
    }
}
