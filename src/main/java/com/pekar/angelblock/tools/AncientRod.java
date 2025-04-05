package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.blocks.GreenDiamondBlock;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.text.ITooltip;
import com.pekar.angelblock.text.ITooltipProvider;
import com.pekar.angelblock.text.TextStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class AncientRod extends MagneticRod
{
    public AncientRod(ModToolMaterial material, boolean isMagnetic, Properties properties)
    {
        super(material, isMagnetic, properties);
    }

    @Override
    protected void additionalActionOnMineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity entity)
    {
        if (blockState.getBlock() == Blocks.COBWEB)
        {
            if (!level.isClientSide() && entity instanceof Player player)
            {
                destroyWebBlocks(level, pos);
                damageMainHandItemIfSurvivalIgnoreClient(player, level);
            }
        }
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
    {
        if (hasCriticalDamage(itemStack)) return 1F;

        if (blockState.getBlock() == Blocks.COBWEB)
            return 18.0F;

        return super.getDestroySpeed(itemStack, blockState);
    }

    @Override
    protected InteractionResult useOnInternal(UseOnContext context)
    {
        var result = super.useOnInternal(context);
        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();

        if (isEnhanced() && player != null && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT))
            return result;

        var level = player.level();
        boolean isClientSide = level.isClientSide();

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block != Blocks.STONE || context.getClickedFace() == Direction.UP)
        {
            if (utils.blocks.transformations.mossyTransforming(player, pos, block))
            {
                return getToolInteractionResult(true, isClientSide);
            }
        }

        var itemStack = player.getItemInHand(context.getHand());

        if (!hasCriticalDamage(itemStack))
        {
            if (block instanceof InfestedBlock infestedBlock)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, infestedBlock.getHostBlock());
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }
                return getToolInteractionResult(true, isClientSide);
            }

            if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE)
            {
                if (!isClientSide)
                {
                    boolean isDark = block == Blocks.DEEPSLATE_DIAMOND_ORE;
                    setBlock(player, pos, BlockRegistry.GREEN_DIAMOND_ORE.get().defaultBlockState().setValue(GreenDiamondBlock.IS_DARK, isDark));
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                }
                return getToolInteractionResult(true, isClientSide);
            }

            if (block instanceof LeavesBlock)
            {
                damageMainHandItemIfSurvivalIgnoreClient(player, level);

                return setOnBlockSide(context, this::setVine);
            }

            var hand = context.getHand();
            var facing = context.getClickedFace();
            var itemRand = new Random();

            if (facing == Direction.UP)
            {
                if (utils.blocks.conditions.isNearWaterHorizontal(level, pos) && (block == Blocks.DIRT || block == Blocks.COARSE_DIRT
                        || block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL || utils.blocks.types.isSandBlock(block)
                        || block == Blocks.MOSS_BLOCK || block == Blocks.MYCELIUM))
                {
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    return plant(player, level, pos, hand, facing, Blocks.SUGAR_CANE);
                }

                if (block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.MOSS_BLOCK)
                {
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    int randomValue = itemRand.nextInt() & 1;

                    switch (randomValue)
                    {
                        case 0:
                            return plant(player, level, pos, hand, facing, Blocks.SWEET_BERRY_BUSH);
                        default:
                            return plant(player, level, pos, hand, facing, Blocks.FERN);
                    }
                }

                if (block == Blocks.GRASS_BLOCK)
                {
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    int randomValue = itemRand.nextInt(16);
                    return plant(player, level, pos, hand, facing, chooseFlowerByValue(randomValue));
                }

                if (block == Blocks.SAND)
                {
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    return plant(player, level, pos, hand, facing, Blocks.CACTUS);
                }

                if (block == Blocks.FARMLAND)
                {
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);
                    int randomValue = itemRand.nextInt() % 4;

                    switch (randomValue)
                    {
                        case 0:
                            return plant(player, level, pos, hand, facing, Blocks.WHEAT);
                        case 1:
                            return plant(player, level, pos, hand, facing, Blocks.POTATOES);
                        case 2:
                            return plant(player, level, pos, hand, facing, Blocks.CARROTS);
                        default:
                            return plant(player, level, pos, hand, facing, Blocks.BEETROOTS);
                    }
                }

                if (block == Blocks.DIRT_PATH)
                {
                    if (!isClientSide)
                    {
                        damageMainHandItemIfSurvivalIgnoreClient(player, level);
                        setBlock(player, pos, Blocks.GRASS_BLOCK);
                    }
                    return getToolInteractionResult(true, isClientSide);
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        var block = state.getBlock();
        return !hasCriticalDamage(stack) && block == Blocks.COBWEB;
    }

    protected String getRodId()
    {
        return ToolRegistry.ANCIENT_ROD.getRegisteredName();
    }

    protected final String getRodDescriptionId()
    {
        return getRodDescriptionId(getRodId());
    }

    protected final String getRodDescriptionId(String rodId)
    {
        return "item." + rodId.replace(':', '.');
    }

    protected void appendPlacingBlockInfo(ITooltip tooltip, boolean selectAsNew)
    {
        for (int i = 1; i <= 8; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.Header, i == 1 || i == 3).apply();
        }
    }

    protected void appendBlockTransformInfo(ITooltip tooltip, boolean selectAsNew)
    {
        for (int i = 9; i <= 12; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.Header, i == 9).apply();
        }
    }

    protected void appendMagneticInfo(ITooltip tooltip)
    {
        for (int i = 13; i <= 21; i++)
        {
            if (i == 21) tooltip.addEmptyLine();
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.Header, i == 13).styledAs(TextStyle.DarkGray, i == 21).apply();
        }
    }

    protected void appendCommonPreInfo(ITooltip tooltip)
    {
        tooltip.addLine(getRodDescriptionId(), 0).apply();
        if (isEnhanced())
            tooltip.addLine(getRodDescriptionId(), 1).apply();
    }

    protected void appendCommonPostInfo(ITooltip tooltip)
    {
        for (int i = 22; i <= 23; i++)
        {
            tooltip.addLine(getRodDescriptionId(), i).styledAs(TextStyle.DarkGray, i == 22).apply();
        }
    }

    private void destroyWebBlocks(Level level, BlockPos pos)
    {
        int X = pos.getX(), Y = pos.getY(), Z = pos.getZ();
        for (int a = X - 1; a <= X + 1; a++)
            for (int b = Y - 1; b <= Y + 1; b++)
                for (int c = Z - 1; c <= Z + 1; c++)
                {
                    BlockPos localPos = new BlockPos(a, b, c);
                    Block block = level.getBlockState(localPos).getBlock();

                    if (block == Blocks.COBWEB)
                    {
                        level.destroyBlock(localPos, true);
                    }
                }
    }

    private InteractionResult setVine(BlockPlaceContext context, BlockPos pos)
    {
        var level = context.getLevel();
        BlockState state = Blocks.VINE.getStateForPlacement(context);

        if (!level.isEmptyBlock(pos)) return InteractionResult.FAIL;

        if (!level.isClientSide())
        {
            level.setBlock(pos, state, 11);
            new PlaySoundPacket(SoundType.PLANT).sendToPlayer((ServerPlayer) context.getPlayer());
        }
        return getToolInteractionResult(true, level.isClientSide());
    }

    protected InteractionResult setOnBlockSide(UseOnContext useOnContext, BiFunction<BlockPlaceContext, BlockPos, InteractionResult> setBlock)
    {
        var context = new BlockPlaceContext(useOnContext);

        var pos = useOnContext.getClickedPos();
        var facing = useOnContext.getClickedFace();

        switch (facing)
        {
            case UP:
                return InteractionResult.FAIL;

            case NORTH:
                return setBlock.apply(context, pos.north());

            case SOUTH:
                return setBlock.apply(context, pos.south());

            case EAST:
                return setBlock.apply(context, pos.east());

            case WEST:
                return setBlock.apply(context, pos.west());

            case DOWN:
                return setBlock.apply(context, pos.below());
        }

        return InteractionResult.PASS;
    }

    private Block chooseFlowerByValue(int value)
    {
        switch (value)
        {
            case 1: return Blocks.POPPY;
            case 2: return Blocks.BLUE_ORCHID;
            case 3: return Blocks.ALLIUM;
            case 4: return Blocks.AZURE_BLUET;
            case 5: return Blocks.RED_TULIP;
            case 6: return Blocks.ORANGE_TULIP;
            case 7: return Blocks.WHITE_TULIP;
            case 8: return Blocks.PINK_TULIP;
            case 9: return Blocks.OXEYE_DAISY;
            case 10: return Blocks.CORNFLOWER;
            case 11: return Blocks.LILY_OF_THE_VALLEY;
            case 12: return Blocks.SUNFLOWER;
            case 13: return Blocks.LILAC;
            case 14: return Blocks.ROSE_BUSH;
            case 15: return Blocks.PEONY;
            default: return Blocks.DANDELION;
        }
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!showExtendedDescription(tooltip)) return;

        // Common
        appendCommonPreInfo(tooltip);

        if (Screen.hasControlDown())
        {
            // Magnetic
            if (!isEnhanced())
                tooltip.addLine("description.rods.no_magnet_mode").withFormatting(ChatFormatting.DARK_RED, true).apply();
            else if (this instanceof FireRod)
                tooltip.addLine("description.rods.magnet_mode.fire_rod").asDarkGrey().apply();
            else
                tooltip.addLine("description.rods.magnet_mode.ancient_rod").asDarkGrey().apply();

            appendMagneticInfo(tooltip);
        }
        else if (Screen.hasShiftDown())
        {
            // Placing
            appendPlacingBlockInfo(tooltip, true);
            tooltip.addEmptyLine();
        }
        else if (Screen.hasAltDown())
        {
            // Transformations
            appendBlockTransformInfo(tooltip, true);
            tooltip.addEmptyLine();
        }

        // Common
        appendCommonPostInfo(tooltip);
        tooltip.addEmptyLine();

        if (Screen.hasControlDown())
        {
            // Magnetic
            tooltip.addLine("description.rods.press_shift");
            tooltip.addLine("description.rods.press_alt");
        }
        else if (Screen.hasShiftDown())
        {
            // Placing
            tooltip.addLine("description.rods.press_alt");
            tooltip.addLine("description.rods.press_ctrl");
        }
        else if (Screen.hasAltDown())
        {
            // Transformations
            tooltip.addLine("description.rods.press_shift");
            tooltip.addLine("description.rods.press_ctrl");
        }
    }
}
