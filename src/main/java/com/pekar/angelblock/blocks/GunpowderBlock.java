package com.pekar.angelblock.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GunpowderBlock extends FallingBlock
{
    public GunpowderBlock()
    {
        super(BlockBehaviour.Properties.copy(Blocks.SAND).sound(SoundType.SNOW).strength(0.2F));
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face)
    {
        return 10000;
    }

    @Override
    public boolean isFireSource(BlockState state, LevelReader world, BlockPos pos, Direction side)
    {
        return true;
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion)
    {
        return false;
    }

    @Override
    public void fallOn(Level level, BlockState blockState, BlockPos pos, Entity entity, float p_152430_)
    {
        super.fallOn(level, blockState, pos, entity, p_152430_);
        fireAndExplode(level, entity);
    }

    private void fireAndExplode(Level level, Entity entity)
    {
        entity.displayFireAnimation();
        explodeBlock(level, new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ()));
    }

    private void explodeBlock(Level level, BlockPos pos)
    {
        level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                1.8f, /* fire */ true, Level.ExplosionInteraction.BLOCK);
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState blockState, BlockState blockState1, FallingBlockEntity fallingBlockEntity)
    {
        fireAndExplode(level, fallingBlockEntity);
    }

    @Override
    public void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction direction, @Nullable LivingEntity igniter)
    {
        explodeBlock(level, pos);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return 300; // 100%
    }

    @Override
    public boolean dropFromExplosion(Explosion p_49826_)
    {
        return false;
    }
}
