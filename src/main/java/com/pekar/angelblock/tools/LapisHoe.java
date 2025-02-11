package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.tools.properties.LapisHoeProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class LapisHoe extends EnhancedHoe
{
    public LapisHoe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LapisHoeProperties());
    }

    @Override
    protected boolean onBlockProcessing(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        if (!level.isEmptyBlock(pos.above())) return false;

        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (utils.blocks.types.canBeFarmland(block))
        {
            if (!level.isClientSide())
            {
                level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 11);
                new PlaySoundPacket(SoundType.PLANT).sendToPlayer((ServerPlayer) player);
                damageItemIfSurvival(player, level, pos, blockState);
            }
            return true;
        }
        else if (block == Blocks.COARSE_DIRT)
        {
            if (!level.isClientSide())
            {
                setBlock(player, pos, Blocks.DIRT);
                damageItemIfSurvival(player, level, pos, blockState);
            }
            return true;
        }
        else
        {
            return changePodzolToDirt(player, level, pos);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        for (int i = 0; i <= 12; i++)
        {
            tooltipComponents.add(getDescription(i, i == 1 || i == 5 || i == 7, false, i == 3));
        }
    }
}
