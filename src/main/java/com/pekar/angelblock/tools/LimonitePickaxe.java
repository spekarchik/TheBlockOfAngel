package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.tools.properties.LimoniteMaterialProperties;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LimonitePickaxe extends EnhancedPickaxe
{
    public LimonitePickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties, new LimoniteMaterialProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = super.useOn(context);
        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();
        var level = player.level();

//        if (level.isClientSide) return result;
//        if (!canUseToolEffect(player)) return result;

        var pos = context.getClickedPos();

        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        if (block instanceof InfestedBlock infestedBlock)
        {
            if (!level.isClientSide())
            {
                level.setBlock(pos, infestedBlock.getHostBlock().defaultBlockState(), 11);
                new PlaySoundPacket(SoundType.INFESTED_BLOCK).sendToPlayer((ServerPlayer) player);
            }

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return result;
    }
}
