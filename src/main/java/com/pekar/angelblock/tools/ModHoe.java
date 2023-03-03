package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ModHoe extends HoeItem implements IModTool
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();

    public static ModHoe createPrimary(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        return new ModHoe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModHoe(Tier material, int attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, attackDamage, attackSpeed, properties);
        this.materialProperties = materialProperties;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;

        var result = super.useOn(context);
        if (result == InteractionResult.FAIL) return result;

        if (level.isClientSide) return result;
        if (!canUseToolEffect(player)) return result;

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        BlockPos upPos = pos.above();

        if (level.isWaterAt(upPos) || ((level.isEmptyBlock(upPos))
                && ((isFarmTypeBlock(level, upPos.north()) && isFarmTypeBlock(level, upPos.south()))
                || (isFarmTypeBlock(level, upPos.east()) && isFarmTypeBlock(level, upPos.west())))))
        {
            level.setBlock(upPos, Blocks.WATER.defaultBlockState(), 11);
            new PlaySoundPacket(SoundType.WATER_PLACED).sendToPlayer((ServerPlayer) player);

            damageItemIfSurvival(player, level, pos, blockState); // pos, not upPos

            if (!updateNeighbors(level, upPos))
            {
                return InteractionResult.FAIL;
            }

            return InteractionResult.CONSUME;
        }
        else
        {
            if (level.isEmptyBlock(upPos) && context.getClickedFace() == Direction.UP)
                changePodzolToDirt(player, level, pos);

            return isEnhancedTool() ? InteractionResult.CONSUME_PARTIAL : InteractionResult.CONSUME;
        }
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

    protected final boolean canUseToolEffect(Player player)
    {
        ItemStack itemstack = player.getItemInHand(InteractionHand.OFF_HAND);
        return itemstack.isEmpty() || !(itemstack.getItem() instanceof BlockItem);
    }

    protected void damageItemIfSurvival(Player player, Level level, BlockPos pos, BlockState blockState)
    {
        if (blockState.getDestroySpeed(level, pos) != 0.0F)
        {
            damageItem(1, player);
        }
    }

    protected boolean canBeFarmland(Block block)
    {
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT_PATH || block == Blocks.DIRT;
    }

    protected final boolean isFarmTypeBlock(Level level, BlockPos pos)
    {
        var block = level.getBlockState(pos).getBlock();
        return block == Blocks.FARMLAND || canBeFarmland(block) || block instanceof SandBlock
                || block == Blocks.GRAVEL || level.isWaterAt(pos);
    }

    protected void changePodzolToDirt(Player player, Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block == Blocks.PODZOL)
        {
            setBlock(player, pos, Blocks.DIRT);
            damageItemIfSurvival(player, level, pos, blockState);
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

    protected void setBlock(Player player, BlockPos pos, Block block)
    {
        player.level.setBlock(pos, block.defaultBlockState(), 11);
        new PlaySoundPacket(SoundType.BLOCK_CHANGED).sendToPlayer((ServerPlayer) player);
    }
}
