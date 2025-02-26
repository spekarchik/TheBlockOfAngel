package com.pekar.angelblock.potions;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.explosions.ExplosionNoHurtEntityDamageCalculator;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockBreakerPotion extends ThrownPotion
{
    private final Utils utils = new Utils();
    private final LivingEntity shooter;
    private static final ExplosionDamageCalculator explosionCalculator = new ExplosionNoHurtEntityDamageCalculator();

    public BlockBreakerPotion(EntityType<? extends ThrownPotion> type, Level level)
    {
        super(type, level);
        this.shooter = null;
    }

    public BlockBreakerPotion(Level level, LivingEntity shooter)
    {
        super(level, shooter);
        this.shooter = shooter;
    }

    @Override
    protected void onHit(HitResult result)
    {
        if (shooter == null) return;

        var location = result.getLocation();
        BlockPos pos = new BlockPos((int) location.x, (int) location.y, (int) location.z);

        var targetEntities = shooter.level().getEntities((Entity)null, new AABB(pos).inflate(3),
                e -> e instanceof LivingEntity).stream().map(x -> (LivingEntity)x).toList();

        shooter.level().explode(this, null, explosionCalculator, pos.getX(), pos.getY(), pos.getZ(), 2.0F, false, Level.ExplosionInteraction.NONE);
        applyKnockback(shooter.level(), location, 1.0F);

        for (var target : targetEntities)
        {
            if (!(target instanceof Player))
                destroyDiamondArmor(target);
        }

        super.onHit(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result)
    {
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

    private void destroyDiamondArmor(LivingEntity entity)
    {
        for (var slot : EquipmentSlot.values())
        {
            if (!slot.isArmor()) continue;

            var itemStack = entity.getItemBySlot(slot);
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof ArmorItem armorItem)
            {
                if (armorItem.getMaterial().equals(ArmorMaterials.DIAMOND))
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

                    entity.spawnAtLocation(new ItemStack(ItemRegistry.DIAMOND_POWDER.get(), itemCount), 1.0F);
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
            level.setBlock(pos, BlockRegistry.DESTROYING_DIAMOND_POWDER_BLOCK.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
        else if (block == Blocks.COBBLESTONE)
        {
            level.setBlock(pos, Blocks.GRAVEL.defaultBlockState(), 11);
        }
        else if (block == Blocks.STONE)
        {
            level.setBlock(pos, Blocks.SAND.defaultBlockState(), 11);
        }
        else if (block == Blocks.WHITE_WOOL)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_WHITE_WOOL_BY_POTION.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
//        else if (block == Blocks.GRAVEL)
//        {
//            level.setBlock(pos, BlockRegistry.DESTROYING_RAW_IRON.get().defaultBlockState(), 0);
//            level.destroyBlock(pos, true, source, 1);
//        }
        else if (block == Blocks.LAVA)
        {
            level.setBlock(pos, Blocks.END_STONE.defaultBlockState(), 11);
        }
        else if (block == Blocks.WATER && utils.blocks.conditions.isAboveWaterBlock(level, pos))
        {
            level.setBlock(pos, Blocks.ICE.defaultBlockState(), 11);
        }
        else if (block == Blocks.TUFF)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_GUNPOWDER.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
//        else if (block == Blocks.CRYING_OBSIDIAN)
//        {
//            level.setBlock(pos, BlockRegistry.DESTROYING_DIAMOND_BLOCK.get().defaultBlockState(), 0);
//            level.destroyBlock(pos, true, source, 1);
//        }
        else if (block == Blocks.GLASS)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_PRISMARINE_SHARD_BLOCK.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
//        else if (block == Blocks.GLOWSTONE)
//        {
//            level.setBlock(pos, BlockRegistry.DESTROYING_BLAZE_POWDER.get().defaultBlockState(), 0);
//            level.destroyBlock(pos, true, source, 1);
//        }
        else if (block == Blocks.GRANITE)
        {
            level.setBlock(pos, Blocks.RED_SAND.defaultBlockState(), 11);
        }
        else if (block == Blocks.ICE || block == Blocks.SNOW_BLOCK)
        {
            level.setBlock(pos, Blocks.POWDER_SNOW.defaultBlockState(), 11);
        }
        else if (block == Blocks.PACKED_ICE || block == Blocks.BLUE_ICE)
        {
            level.setBlock(pos, Blocks.SNOW.defaultBlockState(), 11);
        }
        else if (block == Blocks.SAND && utils.blocks.conditions.isNearWaterHorizontal(level, pos))
        {
            level.setBlock(pos, Blocks.CLAY.defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
        else if (block == Blocks.PRISMARINE)
        {
            level.setBlock(pos, BlockRegistry.DESTROYING_PRISMARINE_CRYSTALS.get().defaultBlockState(), 0);
            level.destroyBlock(pos, true, source, 1);
        }
    }
}
