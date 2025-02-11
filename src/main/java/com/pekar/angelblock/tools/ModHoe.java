package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ModHoe extends HoeItem implements IModToolEnhanced
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();

    public static ModHoe createPrimary(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        return new ModHoe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModHoe(Tier material, int attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, properties.attributes(HoeItem.createAttributes(material, attackDamage, attackSpeed)));
        this.materialProperties = materialProperties;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = super.useOn(context);
        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();
        var level = player.level();

//        if (level.isClientSide) return result;

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        BlockPos upPos = pos.above();

        if (level.isWaterAt(upPos) || ((level.isEmptyBlock(upPos))
                && ((utils.blocks.types.isFarmTypeBlock(level, upPos.north()) && utils.blocks.types.isFarmTypeBlock(level, upPos.south()))
                || (utils.blocks.types.isFarmTypeBlock(level, upPos.east()) && utils.blocks.types.isFarmTypeBlock(level, upPos.west())))))
        {
            if (!level.isClientSide)
            {
                level.setBlock(upPos, Blocks.WATER.defaultBlockState(), 11);
                new PlaySoundPacket(SoundType.WATER_PLACED).sendToPlayer((ServerPlayer) player);

                damageItemIfSurvival(player, level, pos, blockState); // pos, not upPos

                if (!utils.blocks.transformations.updateNeighbors(level, upPos)) // TODO: check it
                {
                    return InteractionResult.FAIL;
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else
        {
            if (level.isEmptyBlock(upPos) && context.getClickedFace() == Direction.UP)
                return changePodzolToDirt(player, level, pos) ? InteractionResult.sidedSuccess(level.isClientSide) : result;
        }

        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        for (int i = 0; i <= 0; i++)
        {
            tooltipComponents.add(getDescription(i, false));
        }
    }

    protected boolean changePodzolToDirt(Player player, Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block == Blocks.PODZOL)
        {
            if (!level.isClientSide)
            {
                setBlock(player, pos, Blocks.DIRT);
                damageItemIfSurvival(player, level, pos, blockState);
            }
            return true;
        }

        return false;
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

    @Override
    public TieredItem getTool()
    {
        return this;
    }

    @Override
    public IMaterialProperties getMaterialProperties()
    {
        return materialProperties;
    }
}
