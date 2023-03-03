package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.tools.properties.LapisHoeProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class LapisHoe extends EnhancedHoe
{
    public LapisHoe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LapisHoeProperties());
    }

    @Override
    protected void onBlockProcessing(Player player, Level level, BlockPos originalPos, BlockPos pos, Direction facing)
    {
        var blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (level.isEmptyBlock(pos.above()))
        {
            if (canBeFarmland(block))
            {
                level.setBlock(pos, Blocks.FARMLAND.defaultBlockState(), 11);
                new PlaySoundPacket(SoundType.PLANT).sendToPlayer((ServerPlayer) player);
                damageItemIfSurvival(player, level, pos, blockState);
            }
            else if (block == Blocks.COARSE_DIRT)
            {
                setBlock(player, pos, Blocks.DIRT);
                damageItemIfSurvival(player, level, pos, blockState);
            }
            else
            {
                changePodzolToDirt(player, level, pos);
            }
        }
    }
}
