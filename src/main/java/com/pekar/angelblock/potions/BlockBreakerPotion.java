package com.pekar.angelblock.potions;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.explosions.ExplosionNoHurtEntityDamageCalculator;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockBreakerPotion extends ThrowableItemProjectile
{
    private final Utils utils = new Utils();
    private final LivingEntity shooter;
    private static final ExplosionDamageCalculator explosionCalculator = new ExplosionNoHurtEntityDamageCalculator();

    public BlockBreakerPotion(EntityType<? extends BlockBreakerPotion> type, Level level)
    {
        super(type, level);
        this.shooter = null;
    }

    public BlockBreakerPotion(Level level, LivingEntity shooter, ItemStack itemStack)
    {
        super(PotionRegistry.BLOCK_BREAKER_POTION.get(), shooter, level, itemStack);
        this.shooter = shooter;
    }

    public BlockBreakerPotion(double x, double y, double z, Level level, ItemStack item)
    {
        super(PotionRegistry.BLOCK_BREAKER_POTION.get(), x, y, z, level, item);
        this.shooter = null;
    }

    @Override
    protected Item getDefaultItem()
    {
        return ItemRegistry.BLOCK_BREAKER_POTION_ITEM.get();
    }

    @Override
    protected void onHit(HitResult result)
    {
        super.onHit(result);
        if (shooter == null) return;

        var level = shooter.level();
        if (!(level instanceof ServerLevel serverLevel)) return;

        var location = result.getLocation();
        BlockPos pos = new BlockPos((int) location.x, (int) location.y, (int) location.z);

        var targetEntities = level.getEntities((Entity)null, new AABB(pos).inflate(3),
                e -> e instanceof LivingEntity).stream().map(x -> (LivingEntity)x).toList();

        level.explode(this, null, explosionCalculator, pos.getX(), pos.getY(), pos.getZ(), 0.2F, false, Level.ExplosionInteraction.NONE);
        applyKnockback(level, location, 1.0F);

        for (var target : targetEntities)
        {
            if (!(target instanceof Player))
                destroyDiamondArmor(serverLevel, target);
        }

        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result)
    {
        super.onHitBlock(result);

        if (shooter == null) return;

        BlockPos position = result.getBlockPos();
        int X = position.getX(), Y = position.getY(), Z = position.getZ();

        for (int x = X - 1; x <= X + 1; x++)
            for (int y = Y - 1; y <= Y + 1; y++)
                for (int z = Z - 1; z <= Z + 1; z++)
                {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    applyPotionToBlock(shooter, currentPos);
                }
    }

    private void applyKnockback(Level level, Vec3 explosionPos, float strength)
    {
        float radius = strength * 2.0F; // Радиус толчка

        AABB area = new AABB(
                explosionPos.x - radius, explosionPos.y - radius, explosionPos.z - radius,
                explosionPos.x + radius, explosionPos.y + radius, explosionPos.z + radius
        );

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity entity : entities)
        {
            double distance = entity.position().distanceTo(explosionPos);

            if (distance > radius) continue; // Вне зоны толчка

            double dx = entity.getX() - explosionPos.x;
            double dz = entity.getZ() - explosionPos.z;
            double scale = 1.0 - (distance / radius); // Чем ближе к эпицентру, тем сильнее толкает
            double power = strength * scale * 0.5; // Настроить силу

            entity.knockback(power, dx, dz);
        }
    }

    private void destroyDiamondArmor(ServerLevel serverLevel, LivingEntity entity)
    {
        for (var slot : EquipmentSlot.values())
        {
            if (!slot.isArmor()) continue;

            var itemStack = entity.getItemBySlot(slot);
            if (!itemStack.isEmpty() && itemStack.is(ItemTags.TRIMMABLE_ARMOR))
            {
                if (itemStack.is(ItemRegistry.DIAMOND_ARMOR_TAG))
                {
                    entity.setItemSlot(slot, ItemStack.EMPTY);
                    int itemCount = switch (slot)
                    {
                        case CHEST -> 8;
                        case HEAD -> 5;
                        case FEET -> 4;
                        case LEGS -> 7;
                        default -> 0;
                    };

                    var dropsNumber = random.nextIntBetweenInclusive(itemCount / 3, itemCount);
                    entity.spawnAtLocation(serverLevel, new ItemStack(ItemRegistry.DIAMOND_POWDER.get(), dropsNumber), 1.0F);
                }
            }
        }
    }

    private void applyPotionToBlock(@NotNull Entity source, BlockPos pos)
    {
        var level = source.level();
        Block block = level.getBlockState(pos).getBlock();

        if (block == Blocks.DIAMOND_BLOCK)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_DIAMOND_POWDER_BLOCK.get().defaultBlockState(), Block.UPDATE_NONE);
            level.destroyBlock(pos, true, source, 1);
        }
        else if (block == Blocks.COBBLESTONE)
        {
            level.setBlock(pos, Blocks.GRAVEL.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
        else if (block == Blocks.STONE)
        {
            level.setBlock(pos, Blocks.SAND.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
//        else if (block == Blocks.GRAVEL)
//        {
//            level.setBlock(pos, BlockRegistry.DESTROYING_RAW_IRON.get().defaultBlockState(), 0);
//            level.destroyBlock(pos, true, source, 1);
//        }
        else if (block == Blocks.LAVA && level.getFluidState(pos).getAmount() == FluidState.AMOUNT_FULL/*&& utils.blocks.conditions.isUnderAirBlock(level, pos)*/)
        {
            level.setBlock(pos, Blocks.END_STONE.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
        else if (block == Blocks.WATER && utils.blocks.conditions.isUnderAirBlock(level, pos) /*&& utils.blocks.conditions.isAboveWaterBlock(level, pos)*/)
        {
            level.setBlock(pos, Blocks.ICE.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
        else if (block == Blocks.TUFF)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_SALTPETER.get().defaultBlockState(), Block.UPDATE_NONE);
            level.destroyBlock(pos, true, source, 1);
        }
//        else if (block == Blocks.CRYING_OBSIDIAN)
//        {
//            level.setBlock(pos, BlockRegistry.DESTROYING_DIAMOND_BLOCK.get().defaultBlockState(), 0);
//            level.destroyBlock(pos, true, source, 1);
//        }
        else if (block == Blocks.GLASS)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_PRISMARINE_SHARD_BLOCK.get().defaultBlockState(), Block.UPDATE_NONE);
            level.destroyBlock(pos, true, source, 1);
        }
//        else if (block == Blocks.GLOWSTONE)
//        {
//            level.setBlock(pos, BlockRegistry.DESTROYING_BLAZE_POWDER.get().defaultBlockState(), 0);
//            level.destroyBlock(pos, true, source, 1);
//        }
        else if (block == Blocks.GRANITE)
        {
            level.setBlock(pos, Blocks.RED_SAND.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
        else if (block == Blocks.GRASS_BLOCK || block == Blocks.FARMLAND)
        {
            level.setBlock(pos, Blocks.DIRT_PATH.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            level.setBlock(pos, Blocks.DIRT.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
        else if (block == Blocks.ICE || block == Blocks.SNOW_BLOCK)
        {
            level.setBlock(pos, Blocks.POWDER_SNOW.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
        else if (block == Blocks.PACKED_ICE || block == Blocks.BLUE_ICE)
        {
            level.setBlock(pos, Blocks.SNOW.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
        else if (block == Blocks.SAND && utils.blocks.conditions.isNearWaterHorizontal(level, pos))
        {
            level.setBlock(pos, Blocks.CLAY.defaultBlockState(), Block.UPDATE_NONE);
            level.destroyBlock(pos, true, source, 1);
            level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
        else if (block == Blocks.PRISMARINE)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_PRISMARINE_CRYSTALS.get().defaultBlockState(), Block.UPDATE_NONE);
            level.destroyBlock(pos, true, source, 1);
        }
        else
        {
            var woolBlock = utils.blocks.types.getDestroyingWoolBlock(block);
            if (woolBlock != null)
            {
                level.setBlock(pos, woolBlock.defaultBlockState(), Block.UPDATE_NONE);
                level.destroyBlock(pos, true, source, 1);
            }
        }
    }
}
