package com.pekar.angelblock.blocks;

import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.cleaners.TrackedAnvil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/** An anvil projectile that is cleaned up shortly after landing. */
public class TransientAnvilBlock extends AnvilBlock
{
    private static final int TICKS_AFTER_LANDING = 3;

    public TransientAnvilBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState fallingState, BlockState replacedState, FallingBlockEntity fallingBlock)
    {
        if (!fallingBlock.isSilent())
            level.playSound(null, pos, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 1.0F, 1.0F);

        if (level instanceof ServerLevel serverLevel)
            Cleaner.add(new TrackedAnvil(this, pos.immutable(), serverLevel, TICKS_AFTER_LANDING));
    }
}
