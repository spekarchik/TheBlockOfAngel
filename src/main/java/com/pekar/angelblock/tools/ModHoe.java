package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
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

    protected void mineAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (level.isClientSide || !isEnhancedTool() || !isToolEffective(entityLiving, pos)) return;

        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        BlockState blockState = level.getBlockState(pos);
        float originHardness = blockState.getBlock().defaultDestroyTime();

        if (originHardness == 0.0F)
            return;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        for (int x = posX - 1; x <= posX + 1; x++)
            for (int y = posY - 1; y <= posY + 1; y++)
                for (int z = posZ - 1; z <= posZ + 1; z++)
                {
                    if (x == posX && y == posY && z == posZ) continue;
                    onBlockMining(level, blockState, originHardness, new BlockPos(x, y, z), entityLiving);
                }
    }

    protected final boolean isToolEffective(LivingEntity entityLiving, BlockPos pos)
    {
        BlockState blockState = entityLiving.level.getBlockState(pos);
        return isCorrectToolForDrops(entityLiving.getMainHandItem(), blockState);
    }

    protected void onBlockMining(Level level, BlockState originBlockState, float originHardness, BlockPos pos, LivingEntity entityLiving)
    {
        var blockState = level.getBlockState(pos);

        if (blockState.hasBlockEntity()) return;

        var block = blockState.getBlock();
        float hardness = block.defaultDestroyTime();

        if (hardness <= originHardness && isToolEffective(entityLiving, pos) && materialProperties.isSafeToBreak(entityLiving, pos))
        {
            if (utils.destroyBlockByMainHandTool(level, pos, entityLiving, blockState, block))
                damageItem(1, entityLiving);
        }
    }

    protected final void processAdditionalBlocks(Player player, Level level, BlockPos pos, Direction facing)
    {
        if (level.isClientSide || !isEnhancedTool() || facing != Direction.UP) return;

        if (!player.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        BlockState blockState = level.getBlockState(pos);
        float initialHardness = blockState.getBlock().defaultDestroyTime();

        if (initialHardness == 0.0F)
            return;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int a1, a2, b1, b2;
        switch (player.getDirection())
        {
            case NORTH:
                a1 = 1; a2 = 1; b1 = 3; b2 = 1; break;

            case SOUTH:
                a1 = 1; a2 = 1; b1 = 1; b2 = 3; break;

            case EAST:
                a1 = 1; a2 = 3; b1 = 1; b2 = 1; break;

            case WEST:
                a1 = 3; a2 = 1; b1 = 1; b2 = 1; break;

            default:
                a1 = 1; a2 = 1; b1 = 1; b2 = 1; break;
        }

        for (int x = posX - a1; x <= posX + a2; x++)
            for (int z = posZ - b1; z <= posZ + b2; z++)
            {
                if (x == posX && z == posZ) continue;
                onBlockProcessing(player, level, pos, new BlockPos(x, posY, z), facing);
            }
    }

    protected void onBlockProcessing(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        // nothing by default
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

    protected boolean canPreventBlockDestroying(LivingEntity entity, BlockPos pos)
    {
        return isToolEffective(entity, pos) && !entity.isShiftKeyDown();
    }
}
