package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class EnhancedShovel extends ModShovel
{
    public EnhancedShovel(Tier material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, attackDamage, attackSpeed, properties, materialProperties);
    }

    @Override
    public boolean isEnhancedTool()
    {
        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level;
        var pos = context.getClickedPos();

        InteractionResult result = super.useOn(context);

        if (result != InteractionResult.FAIL)
        {
            processAdditionalBlocks(player, level, pos, context.getClickedFace());
            return InteractionResult.CONSUME;
        }

        return result;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity)
    {
        if (!level.isClientSide)
            mineAdditionalBlocks(level, pos, livingEntity);
        return super.mineBlock(itemStack, level, blockState, pos, livingEntity);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player)
    {
        if (canPreventBlockDestroying(player, pos) && !materialProperties.isSafeToBreak(player, pos)) return true;
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    protected final void mineAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (level.isClientSide || !isEnhancedTool() || !isToolEffective(entityLiving, pos)) return;

        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        BlockState blockState = level.getBlockState(pos);
        if (blockState.hasBlockEntity() || blockState != blockState.getBlock().defaultBlockState()) return;

        float originHardness = blockState.getBlock().defaultDestroyTime();
        if (originHardness == 0.0F) return;

        var facing = Utils.getDirectionForShovel(entityLiving, pos);
        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int a, b, c;
        switch (facing)
        {
            case NORTH:
            case SOUTH:
                a = 1; b = 1; c = 0; break;

            case EAST:
            case WEST:
                a = 0; b = 1; c = 1; break;

            default:
                a = 1; b = 0; c = 1; break;
        }

        for (int x = posX - a; x <= posX + a; x++)
            for (int y = posY - b; y <= posY + b; y++)
                for (int z = posZ - c; z <= posZ + c; z++)
                {
                    if (x == posX && y == posY && z == posZ) continue;
                    onBlockMining(level, blockState, originHardness, new BlockPos(x, y, z), entityLiving);
                }
    }

    protected final void processAdditionalBlocks(Player player, Level level, BlockPos pos, Direction facing)
    {
        if (level.isClientSide || !isEnhancedTool() || facing != Direction.UP) return;

        if (!player.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
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
                onBlockTransforming(player, level, pos, new BlockPos(x, posY, z), facing);
            }
    }

    protected void onBlockTransforming(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (!level.isEmptyBlock(pos.above())) return;

        if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL
                || block == Blocks.MYCELIUM || block == Blocks.ROOTED_DIRT || block == Blocks.FARMLAND)
        {
            BlockState newBlockState = Blocks.DIRT_PATH.defaultBlockState();
            level.setBlock(pos, newBlockState, 11);
            new PlaySoundPacket(SoundType.PLANT).sendToPlayer((ServerPlayer) player);

            damageItemIfSurvival(player, level, pos, blockState);
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

        if (hardness <= originHardness && isToolEffective(entityLiving, pos)
                && (materialProperties.isSafeToBreak(entityLiving, pos) ||  entityLiving.isShiftKeyDown()))
        {
            if (utils.destroyBlockByMainHandTool(level, pos, entityLiving, blockState, block))
                damageItem(1, entityLiving);
        }
    }

    protected boolean canPreventBlockDestroying(LivingEntity entity, BlockPos pos)
    {
        return isToolEffective(entity, pos) && !entity.isShiftKeyDown();
    }
}
