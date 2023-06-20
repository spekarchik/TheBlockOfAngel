package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.network.packets.PlaySoundPacket;
import com.pekar.angelblock.network.packets.SoundType;
import com.pekar.angelblock.potions.PotionRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
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
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity entity)
    {
        if (blockState.getBlock() == Blocks.COBWEB)
        {
            if (!level.isClientSide() && entity instanceof Player player)
            {
                destroyWebBlocks(level, pos);
                damageItemIfSurvival(player, level, pos, blockState);
            }

            return true;
        }

        return super.mineBlock(itemStack, level, blockState, pos, entity);
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState)
    {
        if (blockState.getBlock() == Blocks.COBWEB)
            return 18.0F;

        return super.getDestroySpeed(itemStack, blockState);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var result = super.useOn(context);
        if (result == InteractionResult.FAIL) return result;

        var player = context.getPlayer();

        if (isEnhancedRod() && player.hasEffect(PotionRegistry.ROD_MAGNETIC_MODE_EFFECT.get()))
            return result;

        var level = player.level();
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

        if (!isBroken(itemStack))
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
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        var block = state.getBlock();

        return block == Blocks.STONE || block == Blocks.COBBLESTONE || block == Blocks.COBBLED_DEEPSLATE
                || block == Blocks.DEEPSLATE || block == Blocks.MOSSY_COBBLESTONE || block == Blocks.COBBLESTONE_SLAB || block == Blocks.STONE_SLAB
                || block == Blocks.MOSSY_COBBLESTONE_SLAB || block == Blocks.STONE_BRICKS || block == Blocks.DEEPSLATE_BRICKS
                || block == Blocks.MOSSY_STONE_BRICKS || block == Blocks.STONE_BRICK_SLAB || block == Blocks.DEEPSLATE_BRICK_SLAB
                || block == Blocks.MOSSY_STONE_BRICK_SLAB
                || (!isBroken(stack) && (block instanceof InfestedBlock || block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE
                || block instanceof LeavesBlock || block == Blocks.COBWEB
                || block == Blocks.DIRT || block == Blocks.COARSE_DIRT
                || block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL || block instanceof SandBlock
                || block == Blocks.MOSS_BLOCK || block == Blocks.MYCELIUM || block == Blocks.FARMLAND
                || block == Blocks.DIRT_PATH));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag)
    {
        int lines = getLineNumber();
        for (int i = 1; i <= lines; i++)
        {
            var component = getDisplayName(i).withStyle(ChatFormatting.GRAY);
            if (i == 1 && isEnhancedRod())
                component = component.withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.WHITE);

            components.add(component);
        }
    }

    protected int getLineNumber()
    {
        return isEnhancedRod() ? 5 : 9;
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
