package com.pekar.angelblock.tools;

import com.pekar.angelblock.events.block_cleaner.BlockCleaner;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;

public class ModSword extends SwordItem implements IModTool
{
    private static final int[] dx = { 3, -3, 2, 2, -2, -2, 0, 0, 1, 1, -1, -1 };
    private static final int[] dz = { 0, 0, 1, -1, 1, -1, 3, -3, 2, -2, 2, -2 };
    private static final int CactusLifeTime = 1200;
    private static final int WebLifeTime = 1200;
    private static final int TimeThreshold = 600;

    public ModSword(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
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
        BlockCleaner.add(player, pos, WebLifeTime + increment, false);
    }

    protected void explode(Player player, Level level, BlockPos pos)
    {
        level.explode(player, pos.getX(), pos.getY() + 0.5, pos.getZ(), 0.8f, false /*fire*/, Explosion.BlockInteraction.NONE /*influences on explosion particles?*/);
        level.explode(player, pos.getX() + 0.8, pos.getY() + 0.2, pos.getZ() + 0.8, 0.8f, Explosion.BlockInteraction.NONE);
        level.explode(player, pos.getX() + 0.8, pos.getY() + 0.7, pos.getZ() - 0.8, 0.8f, Explosion.BlockInteraction.NONE);
        level.explode(player, pos.getX() - 0.8, pos.getY() + 0.4, pos.getZ() + 0.8, 0.8f, Explosion.BlockInteraction.NONE);
        level.explode(player, pos.getX() - 0.8, pos.getY() + 1.5, pos.getZ() - 0.8, 0.8f, Explosion.BlockInteraction.NONE);
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
        ItemStack itemstack = player.getItemInHand(InteractionHand.OFF_HAND);
        return itemstack.isEmpty() || !(itemstack.getItem() instanceof BlockItem);
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

        if (plantCactus(player, level, pos, hand, facing) != InteractionResult.SUCCESS) return false;
        setBlock(level, pos.above(2), Blocks.CACTUS.defaultBlockState());
        setBlock(level, pos.above(3), Blocks.CACTUS.defaultBlockState());

        int increment = Utils.random.nextInt(TimeThreshold);

        BlockCleaner.add(player, pos.above(3), CactusLifeTime + increment, false);
        BlockCleaner.add(player, pos.above(2), CactusLifeTime + increment + 1, false);
        BlockCleaner.add(player, pos.above(), CactusLifeTime + increment + 2, false);

        return true;
    }

    private void setBlock(Level level, BlockPos pos, BlockState state)
    {
        if (!level.isClientSide)
        {
            level.setBlock(pos, state, 11);
        }
    }

    private InteractionResult plantCactus(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockState state = level.getBlockState(pos);
        if (facing == Direction.UP && state.getBlock().canSustainPlant(state, level, pos, Direction.UP, (IPlantable) Blocks.CACTUS) && level.isEmptyBlock(pos.above()))
        {
            level.setBlock(pos.above(), Blocks.CACTUS.defaultBlockState(), 11);

            if (player instanceof ServerPlayer serverPlayer)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos.above(), itemstack);
            }

            return InteractionResult.SUCCESS;
        }
        else
        {
            return InteractionResult.FAIL;
        }
    }

    @Override
    public boolean isEnhancedTool()
    {
        return false;
    }

    @Override
    public boolean isEnhancedWeapon()
    {
        return false;
    }

    @Override
    public boolean isEnhancedRod()
    {
        return false;
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

    protected void setBlock(Player player, BlockPos pos, Block block)
    {
        player.level.setBlock(pos, block.defaultBlockState(), 11);
        new PlaySoundPacket(SoundType.BLOCK_CHANGED).sendToPlayer((ServerPlayer) player);
    }
}
