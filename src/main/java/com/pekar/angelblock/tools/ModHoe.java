package com.pekar.angelblock.tools;

import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ModHoe extends ModTool
{
    protected final IMaterialProperties materialProperties;

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
        var result = super.useOn(context);
        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();
        var level = player.level();

//        if (level.isClientSide) return result;

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        BlockPos upPos = pos.above();

        if (level.isWaterAt(upPos) || ((level.isEmptyBlock(upPos))
                && ((isFarmTypeBlock(level, upPos.north()) && isFarmTypeBlock(level, upPos.south()))
                || (isFarmTypeBlock(level, upPos.east()) && isFarmTypeBlock(level, upPos.west())))))
        {
            if (!level.isClientSide)
            {
                level.setBlock(upPos, Blocks.WATER.defaultBlockState(), 11);
                new PlaySoundPacket(SoundType.WATER_PLACED).sendToPlayer((ServerPlayer) player);

                damageItemIfSurvival(player, level, pos, blockState); // pos, not upPos

                if (!updateNeighbors(level, upPos))
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

    private MutableComponent getDescription(int lineNumber, TextStyle textStyle)
    {
        var component = getDisplayName(lineNumber);
        return switch (textStyle)
                {
                    case Header -> component.withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE);
                    case Subheader -> component.withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GRAY);
                    case Notice -> component.withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);
                    default -> component.withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY);
                };
    }

    protected MutableComponent getDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice)
    {
        TextStyle textStyle;

        if (isHeader) textStyle = TextStyle.Header;
        else if (isSubHeader) textStyle = TextStyle.Subheader;
        else if (isNotice) textStyle = TextStyle.Notice;
        else textStyle = TextStyle.Regular;

        return getDescription(lineNumber, textStyle);
    }

    protected MutableComponent getDescription(int lineNumber, boolean isHeader, boolean isSubHeader)
    {
        return getDescription(lineNumber, isHeader, isSubHeader, false);
    }

    protected MutableComponent getDescription(int lineNumber, boolean isHeader)
    {
        return getDescription(lineNumber, isHeader, false, false);
    }
}
