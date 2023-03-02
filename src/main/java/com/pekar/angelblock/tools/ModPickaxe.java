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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ModPickaxe extends PickaxeItem implements IModTool
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();

    public static ModPickaxe createPrimary(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        return new ModPickaxe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModPickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, attackDamage, attackSpeed, properties);
        this.materialProperties = materialProperties;
    }

    protected final void processAdditionalBlocks(Level level, BlockPos pos, LivingEntity entityLiving)
    {
        if (level.isClientSide || !isEnhancedTool() || !isToolEffective(entityLiving, pos)) return;

        if (!entityLiving.hasEffect(PotionRegistry.TOOL_ADVANCED_MODE_EFFECT.get()))
            return;

        BlockState blockState = level.getBlockState(pos);
        if (blockState.hasBlockEntity()) return;

        float originHardness = blockState.getBlock().defaultDestroyTime();
        if (originHardness == 0.0F) return;

        Direction facing = Utils.getDirection(entityLiving, pos);
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
                    onBlockProcessing(level, blockState, originHardness, new BlockPos(x, y, z), entityLiving);
                }
    }

    protected final boolean isToolEffective(LivingEntity entityLiving, BlockPos pos)
    {
        BlockState blockState = entityLiving.level.getBlockState(pos);
        return isCorrectToolForDrops(entityLiving.getMainHandItem(), blockState);
    }

    protected void onBlockProcessing(Level level, BlockState originBlockState, float originHardness, BlockPos pos, LivingEntity entityLiving)
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

    protected final boolean canUseToolEffect(Player player)
    {
        ItemStack itemstack = player.getItemInHand(InteractionHand.OFF_HAND);
        return itemstack.isEmpty() || !(itemstack.getItem() instanceof BlockItem);
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
