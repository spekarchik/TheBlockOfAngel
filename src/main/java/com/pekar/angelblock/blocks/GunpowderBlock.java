package com.pekar.angelblock.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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

import java.util.List;

public class GunpowderBlock extends ModFallingBlock
{
    private static final MapCodec<GunpowderBlock> CODEC = simpleCodec(GunpowderBlock::new);

    public GunpowderBlock()
    {
        this(BlockBehaviour.Properties.ofFullCopy(Blocks.SAND).sound(SoundType.SNOW).strength(0.2F));
    }

    public GunpowderBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
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
    public void fallOn(Level level, BlockState blockState, BlockPos pos, Entity entity, float fallDistance)
    {
        super.fallOn(level, blockState, pos, entity, fallDistance);
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
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag)
    {
        components.add(getDisplayName().withStyle(ChatFormatting.DARK_GRAY));
    }

    private MutableComponent getDisplayName()
    {
        return Component.translatable(this.getDescriptionId() + ".desc");
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec()
    {
        return CODEC;
    }
}
