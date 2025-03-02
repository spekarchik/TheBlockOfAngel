package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.events.block_cleaner.BlockCleaner;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;

public class ModSword extends SwordItem implements IModTool
{
    private static final int[] dx = { 3, -3, 2, 2, -2, -2, 0, 0, 1, 1, -1, -1 };
    private static final int[] dz = { 0, 0, 1, -1, 1, -1, 3, -3, 2, -2, 2, -2 };
    private static final int CactusLifeTime = 1200;
    private static final int WebLifeTime = 1200;
    private static final int TimeThreshold = 600;
    protected final Utils utils = new Utils();

    public ModSword(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, properties.attributes(SwordItem.createAttributes(material, attackDamage, attackSpeed)));
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        var modifiedDamage = Mth.clamp(damage, 0, stack.getMaxDamage() - getCriticalDurability());
        stack.set(DataComponents.DAMAGE, modifiedDamage);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return !hasCriticalDamage(stack) && super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public int getCriticalDurability()
    {
        return 3;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        if (hasCriticalDamage(stack)) return 1F;
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility)
    {
        return !hasCriticalDamage(stack) && super.canPerformAction(stack, itemAbility);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (!hasCriticalDamage(stack))
            additionalActionOnHurtEnemy(stack, target, attacker);

        return super.hurtEnemy(stack, target, attacker);
    }

    protected void additionalActionOnHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
    }

    protected final void setEffectAround(Player player, Level level, BlockPos pos)
    {
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        for (int dx = -4; dx <= 4; dx++)
            for (int dz = -4; dz <= 4; dz++)
            {
                if (Math.abs(dx) + Math.abs(dz) < 3) continue;
                BlockPos blockPos = new BlockPos(posX + dx, posY, posZ + dz);
                processBlock(player, level, blockPos);
            }
    }

    protected final void setEffectAhead(Player player, Level level, BlockPos pos)
    {
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();
        final BlockPos playerPos = player.blockPosition();
        final int playerPosX = playerPos.getX(), playerPosZ = playerPos.getZ();
        int a1, a2, k, n, b1, b2;
        final int effectLength = 9, effectWidth = 4;
        final int effectHalfWidth = effectWidth / 2;

        if (Math.abs(posX - playerPosX) < 2)
        {
            a1 = -effectHalfWidth; a2 = effectHalfWidth; k = 0; n = 0;

            if (posZ > playerPosZ)
            {
                // south
                b1 = 0; b2 = effectLength;
            }
            else
            {
                // north
                b1 = -effectLength; b2 = 0;
            }
        }
        else if (posX > playerPosX)
        {
            a1 = 0; a2 = effectLength;
            if (Math.abs(posZ - playerPosZ) < 2)
            {
                // east
                b1 = -effectHalfWidth; b2 = effectHalfWidth; k = 0; n = 0;
            }
            else if (posZ > playerPosZ)
            {
                // south-east
                b1 = 0; b2 = effectLength; k = -1; n = 0;
            }
            else
            {
                // north-east
                b1 = -effectLength; b2 = 0; k = 0; n = 1;
            }
        }
        else
        {
            a1 = -effectLength; a2 = 0;
            if (Math.abs(posZ - playerPosZ) < 2)
            {
                // west
                b1 = -effectHalfWidth; b2 = effectHalfWidth; k = 0; n = 0;
            }
            else if (posZ > playerPosZ)
            {
                // south-west
                b1 = 0; b2 = effectLength; n = 0; k = 1;
            }
            else
            {
                // north-west
                b1 = -effectLength; b2 = 0; n = -1; k = 0;
            }
        }

        for (int dx = a1; dx <= a2; dx++)
            for (int dz = b1 + dx * n; dz <= b2 + dx * k; dz++)
            {
                BlockPos blockPos = new BlockPos(posX + dx, posY, posZ + dz);
                processBlock(player, level, blockPos);
            }
    }

    protected void processBlock(Player player, Level level, BlockPos pos)
    {
        // nothing by default
    }

    protected final void trySetFire(Level level, BlockPos pos)
    {
        if (!level.isEmptyBlock(pos.above()))
        {
            return;
        }

        if (!level.isClientSide)
        {
            level.setBlock(pos.above(), Blocks.FIRE.defaultBlockState(), 11);
        }
    }

    protected final void setWeb(Player player, Level level, BlockPos pos)
    {
        if (level.isClientSide) return;
        if (!level.isEmptyBlock(pos)) return;

        level.setBlock(pos, Blocks.COBWEB.defaultBlockState(), 11);
        int increment = Utils.random.nextInt(TimeThreshold);
        BlockCleaner.add(player, pos, WebLifeTime + increment, false, true);
    }

    protected void explode(Player player, Level level, BlockPos pos)
    {
        level.explode(player, pos.getX(), pos.getY() + 0.5, pos.getZ(), 0.8f, false /*fire*/, Level.ExplosionInteraction.NONE /*influences on explosion particles?*/);
        level.explode(player, pos.getX() + 0.8, pos.getY() + 0.2, pos.getZ() + 0.8, 0.8f, Level.ExplosionInteraction.NONE);
        level.explode(player, pos.getX() + 0.8, pos.getY() + 0.7, pos.getZ() - 0.8, 0.8f, Level.ExplosionInteraction.NONE);
        level.explode(player, pos.getX() - 0.8, pos.getY() + 0.4, pos.getZ() + 0.8, 0.8f, Level.ExplosionInteraction.NONE);
        level.explode(player, pos.getX() - 0.8, pos.getY() + 1.5, pos.getZ() - 0.8, 0.8f, Level.ExplosionInteraction.NONE);
    }

    protected final void plantCacti(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing)
    {
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        for (int i = 0; i < dx.length; i++)
        {
            BlockPos blockPos = new BlockPos(posX + dx[i], posY, posZ + dz[i]);
            tryPlantCactus(player, level, blockPos, hand, facing);
        }
    }

    protected final boolean canUseToolEffect(Player player)
    {
        // We need it to prevent to fire a house when you are holding a sword with fire effect and a torch and trying to set the torch on a wall
        var mainHandItemStack = player.getMainHandItem();
        if (hasCriticalDamage(mainHandItemStack)) return false;

        var offHandItemStack = player.getOffhandItem();
        return offHandItemStack.isEmpty() || !(offHandItemStack.getItem() instanceof BlockItem);
    }

    private boolean tryPlantCactus(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing)
    {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block != Blocks.SAND || !level.isEmptyBlock(pos.above())
                || !level.isEmptyBlock(pos.above(2)) || !level.isEmptyBlock(pos.above(3)))
        {
            return false;
        }

        if (!plantCactus(player, level, pos, hand, facing)) return false;
        setBlock(level, pos.above(2), Blocks.CACTUS.defaultBlockState());
        setBlock(level, pos.above(3), Blocks.CACTUS.defaultBlockState());

        int increment = Utils.random.nextInt(TimeThreshold);

        BlockCleaner.add(player, pos.above(3), CactusLifeTime + increment, false, false);
        BlockCleaner.add(player, pos.above(2), CactusLifeTime + increment + 1, false, false);
        BlockCleaner.add(player, pos.above(), CactusLifeTime + increment + 2, false, false);

        return true;
    }

    private void setBlock(Level level, BlockPos pos, BlockState state)
    {
        if (!level.isClientSide)
        {
            level.setBlock(pos, state, 11);
        }
    }

    private boolean plantCactus(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockState state = level.getBlockState(pos);
        if (facing == Direction.UP
                && level.isEmptyBlock(pos.above())
                && !state.getBlock().canSustainPlant(state, level, pos, Direction.UP, Blocks.CACTUS.defaultBlockState()).isFalse() // Blocks.CACTUS.defaultBlockState() -> age == 0
                )
        {
            level.setBlock(pos.above(), Blocks.CACTUS.defaultBlockState(), 11);

            if (player instanceof ServerPlayer serverPlayer)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos.above(), itemstack);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean isWeapon()
    {
        return true;
    }

    @Override
    public TieredItem getTool()
    {
        return this;
    }

    public boolean hasExplosionMode()
    {
        return false;
    }

    public boolean hasFireMode()
    {
        return false;
    }

    public boolean hasWebMode()
    {
        return false;
    }
}
