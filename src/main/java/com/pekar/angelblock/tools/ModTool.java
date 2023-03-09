package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ModTool extends HoeItem implements IModTool
{
    protected final Utils utils = new Utils();

    public ModTool(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }

    protected boolean updateNeighbors(Level level, BlockPos pos)
    {
        BlockState waterBlockState = level.getBlockState(pos);
        if (waterBlockState.getBlock() instanceof LiquidBlock liquidBlock)
        {
            liquidBlock.neighborChanged(waterBlockState, level, pos, liquidBlock, pos, false /* ignored */);
            return true;
        }

        return false;
    }

    protected void damageItemIfSurvival(Player player, Level level, BlockPos pos, BlockState blockState)
    {
        if (level.isClientSide()) return;

        if (blockState.getDestroySpeed(level, pos) != 0.0F)
        {
            damageItem(1, player);
        }
    }

    protected final boolean isFarmTypeBlock(Level level, BlockPos pos)
    {
        var block = level.getBlockState(pos).getBlock();
        return block == Blocks.FARMLAND || canBeFarmland(block) || block instanceof SandBlock
                || block == Blocks.GRAVEL || level.isWaterAt(pos);
    }

    protected boolean canBeFarmland(Block block)
    {
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT_PATH || block == Blocks.DIRT;
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

    protected void setBlock(Player player, BlockPos pos, Block block)
    {
        player.level.setBlock(pos, block.defaultBlockState(), 11);
        new PlaySoundPacket(SoundType.BLOCK_CHANGED).sendToPlayer((ServerPlayer) player);
    }
}
