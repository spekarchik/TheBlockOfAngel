package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class AncientRod extends MagneticRod
{
    public AncientRod(Tier material, int attackDamage, float attackSpeed, boolean isMagnetic, Properties properties)
    {
        super(material, attackDamage, attackSpeed, isMagnetic, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = super.useOn(context);
        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();

//        if (level.isClientSide) return InteractionResult.PASS;
//        if (!canUseToolEffect(player)) return InteractionResult.PASS;
        if (isEnhancedRod() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT.get()))
            return result;

        var level = player.level;
        boolean isClientSide = level.isClientSide();

        var pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        var block = blockState.getBlock();

        if (block != Blocks.STONE || context.getClickedFace() == Direction.UP)
        {
            if (utils.mossyTransforming(player, pos, block))
            {
                return InteractionResult.sidedSuccess(isClientSide);
            }
        }

        var itemStack = player.getItemInHand(context.getHand());
        boolean isBroken = itemStack.getMaxDamage() - itemStack.getDamageValue() <= 1;

        if (!isBroken)
        {
            if (block instanceof InfestedBlock infestedBlock)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, infestedBlock.getHostBlock());
                    damageItemIfSurvival(player, level, pos, blockState);
                }
                return InteractionResult.sidedSuccess(isClientSide);
            }

            if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE)
            {
                if (!isClientSide)
                {
                    setBlock(player, pos, BlockRegistry.GREEN_DIAMOND_ORE.get());
                    damageItemIfSurvival(player, level, pos, blockState);
                }
                return InteractionResult.sidedSuccess(isClientSide);
            }

            if (block instanceof LeavesBlock)
            {
                damageItemIfSurvival(player, level, pos, blockState);

                return setOnBlockSide(context, this::setVine);
            }

            if (block == Blocks.COBWEB)
            {
                if (!isClientSide)
                {
                    destroyWebBlocks(level, pos);
                    damageItemIfSurvival(player, level, pos, blockState);
                }

                return InteractionResult.sidedSuccess(isClientSide);
            }

            var hand = context.getHand();
            var facing = context.getClickedFace();
            var itemRand = new Random();

            if (facing == Direction.UP)
            {
                if (Utils.isNearWaterHorizontal(level, pos) && (block == Blocks.DIRT || block == Blocks.COARSE_DIRT
                        || block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL || block instanceof SandBlock
                        || block == Blocks.MOSS_BLOCK || block == Blocks.MYCELIUM))
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.SUGAR_CANE);
                }

                if (block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.MOSS_BLOCK)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
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
                    damageItemIfSurvival(player, level, pos, blockState);
                    int randomValue = itemRand.nextInt() % 16;
                    return plant(player, level, pos, hand, facing, chooseFlowerByValue(randomValue));
                }

                if (block == Blocks.SAND)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
                    return plant(player, level, pos, hand, facing, Blocks.CACTUS);
                }

                if (block == Blocks.FARMLAND)
                {
                    damageItemIfSurvival(player, level, pos, blockState);
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
                        damageItemIfSurvival(player, level, pos, blockState);
                        setBlock(player, pos, Blocks.GRASS_BLOCK);
                    }
                    return InteractionResult.sidedSuccess(isClientSide);
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        if (!isEnhancedRod()) return;
        for (int i = 1; i <= 4; i++)
            components.add(getDisplayName(i).withStyle(ChatFormatting.GRAY));
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
                        level.destroyBlock(localPos, false);
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
        return InteractionResult.sidedSuccess(level.isClientSide());
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

    protected InteractionResult plant(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing, Block plantBlock)
    {
        var itemStack = player.getItemInHand(hand);

        if (facing == Direction.UP && level.isEmptyBlock(pos.above()))
        {
            boolean isClientSide = player.level.isClientSide();
            if (!isClientSide)
            {
                level.setBlock(pos.above(), plantBlock.defaultBlockState(), 11);

                if (player instanceof ServerPlayer serverPlayer)
                {
                    new PlaySoundPacket(SoundType.PLANT).sendToPlayer(serverPlayer);
                    CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos.above(), itemStack);
                }
            }

            return InteractionResult.sidedSuccess(isClientSide);
        }
        else
        {
            return InteractionResult.FAIL;
        }
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
}
