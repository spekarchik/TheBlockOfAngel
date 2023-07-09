package com.pekar.angelblock.tools;

import com.pekar.angelblock.TextStyle;
import com.pekar.angelblock.Utils;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModShovel extends ShovelItem implements IModTool
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();

    public static ModShovel createPrimary(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModShovel(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModShovel(Tier material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, attackDamage, attackSpeed, properties);
        this.materialProperties = materialProperties;
        FLATTENABLES.put(Blocks.FARMLAND, Blocks.DIRT_PATH.defaultBlockState());
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level();

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (FLATTENABLES.containsKey(block) || (block instanceof CampfireBlock && blockState.getValue(CampfireBlock.LIT)))
        {
            return super.useOn(context); // to prevent putting a block it needs both: return SUCCESS on client side AND return CONSUME on server side
        }

        return InteractionResult.PASS;
    }

    protected void damageItemIfSurvival(Player player, Level level, BlockPos pos, BlockState blockState)
    {
        if (blockState.getDestroySpeed(level, pos) != 0.0F)
        {
            damageItem(1, player);
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

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        for (int i = 0; i <= 0; i++)
        {
            components.add(getDescription(i, false));
        }
    }

    protected MutableComponent getDisplayName(int lineNumber)
    {
        return Component.translatable(this.getDescriptionId() + ".desc" + lineNumber);
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

    protected void setBlock(Player player, BlockPos pos, Block block)
    {
        player.level().setBlock(pos, block.defaultBlockState(), 11);
        new PlaySoundPacket(SoundType.BLOCK_CHANGED).sendToPlayer((ServerPlayer) player);
    }
}
