package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.SoundType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class Planter extends WorkRod
{
    public Planter(ModToolMaterial material, Properties properties)
    {
        super(material, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        var player = context.getPlayer();
        var level = player.level();
        var offHandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int offHandItemCountBeforeUse = offHandItemStack.getCount();
        var pos = context.getClickedPos();

        var offHandItem = offHandItemStack.getItem();
        var success = false;

        if (offHandItem == Items.BONE_MEAL)
        {
            success = bonemealPlants(player, level, pos, context.getClickedFace());
        }
        else if (offHandItem instanceof BlockItem)
        {
            success = plantOffHandItems(player, level, pos, context.getClickedFace());
        }
        else if (offHandItem instanceof ShearsItem)
        {
            success = grabPlants(player, level, pos, 5, false);
            if (success)
            {
                damageOffHandItemIfSurvivalIgnoreClient(player, level);
            }
        }

        var result = getToolInteractionResult(success, level.isClientSide());

        if (result == InteractionResult.SUCCESS || result == InteractionResult.SUCCESS_SERVER)
        {
            int offHandItemCountAfterUse = offHandItemStack.getCount();
            if (offHandItemCountAfterUse < offHandItemCountBeforeUse)
                causePlayerExhaustion(player);
        }

        return result;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos pos, LivingEntity livingEntity)
    {
        if (!level.isClientSide() && blockState.is(BlockRegistry.PLANTER_COMPATIBLE_TO_MINE) && livingEntity instanceof Player player)
        {
            grabPlants(player, level, pos, 3, true); // no tool damage
        }

        return true;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return state.getBlock().defaultBlockState().is(BlockRegistry.PLANTER_COMPATIBLE_TO_MINE);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment)
    {
        return enchantment.is(Enchantments.FORTUNE);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip, flag)) return;

        for (int i = 0; i <= 9; i++)
        {
            tooltip.addLine(getDescriptionId(), i)
                    .styledAs(TextStyle.Header, i == 1 || i == 3)
                    .styledAs(TextStyle.Notice, i == 6)
                    .styledAs(TextStyle.ImportantNotice, i == 5)
                    .styledAs(TextStyle.DarkGray, i == 7 || i == 8)
                    .apply();

            if (i == 6) tooltip.addEmptyLine();
        }
    }

    protected boolean grabPlants(Player player, Level level, BlockPos pos, int grabWidth, boolean shouldDrop)
    {
        final int GRAB_LENGTH = 5;
        final int GRAB_HALF_WIDTH = grabWidth / 2;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        int x1 = 0, x2 = 0, z1 = 0, z2 = 0, incX = 1, incZ = 1;
        switch (player.getDirection())
        {
            case NORTH:
                x1 = GRAB_HALF_WIDTH; x2 = GRAB_HALF_WIDTH + 1; z1 = 0; z2 = -GRAB_LENGTH; incZ = -1; break;

            case SOUTH:
                x1 = GRAB_HALF_WIDTH; x2 = GRAB_HALF_WIDTH + 1; z1 = 0; z2 = GRAB_LENGTH; break;

            case EAST:
                x1 = 0; x2 = GRAB_LENGTH; z1 = GRAB_HALF_WIDTH; z2 = GRAB_HALF_WIDTH + 1; break;


            case WEST: // towards negative X
                x1 = 0; x2 = -GRAB_LENGTH; incX = -1; z1 = GRAB_HALF_WIDTH; z2 = GRAB_HALF_WIDTH + 1; break;

        }

        boolean haveAnyTransformed = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var originBlockState = level.getBlockState(pos);
        var originBlock = originBlockState.getBlock();

        int y = supportsFastGrabbing(originBlockState) ? posY : pos.above().getY();
        for (int x = posX - x1; x != posX + x2; x += incX)
            for (int z = posZ - z1; z != posZ + z2; z += incZ)
            {
                boolean hasTransformed = grabPlant(player, level, originBlock, new BlockPos(x, y, z), toolItemStack, shouldDrop);
                if (hasTransformed)
                    haveAnyTransformed = true;
            }

        return haveAnyTransformed;
    }

    private boolean supportsFastGrabbing(BlockState blockState)
    {
        return blockState.getBlock() instanceof VegetationBlock || blockState.is(BlockRegistry.PLANTER_COMPATIBLE_TO_MINE);
    }

    protected boolean plantOffHandItems(Player player, Level level, BlockPos clickedPos, Direction facing)
    {
        if (facing != Direction.UP) return false;

        var seedInHand = player.getItemInHand(InteractionHand.OFF_HAND);

        if (!(seedInHand.getItem() instanceof BlockItem blockItem)) return false;

        if (!seedInHand.is(ItemRegistry.PLANTER_COMPATIBLE_TO_PLANT))
            return false;

        var plantableBlock = blockItem.getBlock();

        int seedCount = seedInHand.getCount();
        final int posX = clickedPos.getX(), posY = clickedPos.getY(), posZ = clickedPos.getZ();

        int shiftX = 0, shiftZ = 0, increment = 0;

        switch (player.getDirection())
        {
            case NORTH:
                shiftX = -1; shiftZ = -seedCount; increment = -1; break;

            case SOUTH:
                shiftX = 1; shiftZ = seedCount; increment = 1; break;

            case EAST:
                shiftX = seedCount; shiftZ = 1; increment = 1; break;

            case WEST:
                shiftX = -seedCount; shiftZ = -1; increment = -1; break;
        }

        boolean haveAnyTransformed = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var clickedBlockState = level.getBlockState(clickedPos);
        var clickedBlock = clickedBlockState.getBlock();
        int y = supportsFastGrabbing(clickedBlockState) ? clickedPos.below().getY() : posY;
        var originSoilBlock = level.getBlockState(new BlockPos(posX, y, posZ)).getBlock();

        for (int x = posX; x != posX + shiftX; x += increment)
            for (int z = posZ; z != posZ + shiftZ; z += increment)
            {
                boolean hasTransformed = plantOffHandItem(player, level, clickedBlock, originSoilBlock, new BlockPos(x, y, z), facing, toolItemStack, plantableBlock);
                if (hasTransformed)
                    haveAnyTransformed = true;
                else
                    return haveAnyTransformed;
            }

        return haveAnyTransformed;
    }

    protected boolean bonemealPlants(Player player, Level level, BlockPos clickedPos, Direction facing)
    {
        final int MAX_BONEMEALABLE_LENGTH = 64;
        final int posX = clickedPos.getX(), posY = clickedPos.getY(), posZ = clickedPos.getZ();

        int shiftX = 0, shiftZ = 0, increment = 0;

        switch (player.getDirection())
        {
            case NORTH:
                shiftX = -1; shiftZ = -MAX_BONEMEALABLE_LENGTH; increment = -1; break;

            case SOUTH:
                shiftX = 1; shiftZ = MAX_BONEMEALABLE_LENGTH; increment = 1; break;

            case EAST:
                shiftX = MAX_BONEMEALABLE_LENGTH; shiftZ = 1; increment = 1; break;

            case WEST:
                shiftX = -MAX_BONEMEALABLE_LENGTH; shiftZ = -1; increment = -1; break;
        }

        boolean haveAnyTransformed = false;
        var toolItemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        var clickedBlock = level.getBlockState(clickedPos).getBlock();
        int y = clickedBlock instanceof BonemealableBlock ? posY : clickedPos.above().getY();
        var originSoilBlock = level.getBlockState(new BlockPos(posX, y - 1, posZ)).getBlock();

        for (int x = posX; x != posX + shiftX; x += increment)
            for (int z = posZ; z != posZ + shiftZ; z += increment)
            {
                boolean hasTransformed = bonemealPlant(player, level, clickedBlock, originSoilBlock, new BlockPos(x, y, z), facing, toolItemStack);
                if (hasTransformed)
                    haveAnyTransformed = true;
                else
                    return haveAnyTransformed;
            }

        return haveAnyTransformed;
    }

    private boolean grabPlant(Player player, Level level, Block originBlock, BlockPos pos, ItemStack toolItemStack, boolean shouldDrop)
    {
        var blockState = level.getBlockState(pos);

        if (!supportsFastGrabbing(blockState) || (supportsFastGrabbing(originBlock.defaultBlockState()) && originBlock != blockState.getBlock())) return false;

        if (level instanceof ServerLevel serverLevel)
        {
            if (shouldDrop)
            {
                var paramsBuilder = new LootParams.Builder(serverLevel)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                        .withParameter(LootContextParams.TOOL, toolItemStack)
                        .withOptionalParameter(LootContextParams.THIS_ENTITY, player);

                var drops = blockState.getDrops(paramsBuilder);

                level.destroyBlock(pos, false);

                var random = level.getRandom();

                for (ItemStack drop : drops)
                {
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.5;
                    double z = pos.getZ() + 0.5;

                    Vec3 direction = player.position().subtract(x, y, z).normalize();

                    double scatter = 0.5;
                    double dx = direction.x + (random.nextDouble() - 0.5) * scatter;
                    double dy = direction.y + (random.nextDouble() - 0.5) * scatter;
                    double dz = direction.z + (random.nextDouble() - 0.5) * scatter;

                    Vec3 velocity = new Vec3(dx, dy, dz).normalize().scale(0.25);
                    var itemEntity = new ItemEntity(level, x, y, z, drop);
                    itemEntity.setDeltaMovement(velocity);
                    itemEntity.setPickUpDelay(5);

                    level.addFreshEntity(itemEntity);
                }
            }
            else
            {
                level.destroyBlock(pos, false);
            }
        }

        return true;
    }

    private boolean plantOffHandItem(Player player, Level level, Block clickedBlock, Block originSoilBlock, BlockPos soilPosToPlantOn,
                                     Direction facing, ItemStack toolItemStack, Block blockToPlant)
    {
        if (hasCriticalDamage(toolItemStack)) return false;

        var soilBlockState = level.getBlockState(soilPosToPlantOn);
        var soilBlock = soilBlockState.getBlock();

        if (soilBlock != originSoilBlock) return false;
        var blockToReplaceByPlantBlockState = level.getBlockState(soilPosToPlantOn.above());
        if (!blockToReplaceByPlantBlockState.is(BlockRegistry.REPLACEABLE_BY_PLANTER)) return blockToReplaceByPlantBlockState.getBlock() == clickedBlock;

        var itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int itemCount = itemStack.getCount();
        if (itemCount < 1) return false;

        if (!blockToReplaceByPlantBlockState.isAir())
        {
            level.destroyBlock(soilPosToPlantOn.above(), false);
        }

        var blockAboveReplacingByPlantBlock = level.getBlockState(soilPosToPlantOn.above(2));
        if (!blockAboveReplacingByPlantBlock.isAir() && blockAboveReplacingByPlantBlock.is(BlockRegistry.REPLACEABLE_BY_PLANTER))
        {
            level.destroyBlock(soilPosToPlantOn.above(2), false);
        }

        var result = plant(player, level, soilPosToPlantOn, InteractionHand.OFF_HAND, facing, blockToPlant);
        if (result.consumesAction())
        {
            damageMainHandItemIfSurvivalIgnoreClient(player, level);

            if (!player.isCreative())
                itemStack.setCount(itemCount - 1);
        }

        return result.consumesAction();
    }

    private boolean bonemealPlant(Player player, Level level, Block clickedBlock, Block originSoilBlock, BlockPos posToBonemeal, Direction facing, ItemStack toolItemStack)
    {
        if (hasCriticalDamage(toolItemStack)) return false;

        var soilBlock = level.getBlockState(posToBonemeal.below()).getBlock();
        if (soilBlock != originSoilBlock) return false;

        var blockStateAtPosToBonemeal = level.getBlockState(posToBonemeal);
        Block blockToBonemeal = blockStateAtPosToBonemeal.getBlock();

        if (!(blockToBonemeal instanceof BonemealableBlock bonemealableBlock)) return blockStateAtPosToBonemeal.isAir();

        var itemStack = player.getItemInHand(InteractionHand.OFF_HAND);
        int itemCount = itemStack.getCount();
        if (itemCount < 1) return false;

        if (bonemealableBlock.isValidBonemealTarget(level, posToBonemeal, blockStateAtPosToBonemeal))
        {
            if (!level.isClientSide())
            {
                bonemealableBlock.performBonemeal((ServerLevel) level, level.random, posToBonemeal, blockStateAtPosToBonemeal);

                if (bonemealableBlock.isBonemealSuccess(level, level.random, posToBonemeal, blockStateAtPosToBonemeal))
                {
                    damageMainHandItemIfSurvivalIgnoreClient(player, level);

                    if (!player.isCreative())
                        itemStack.setCount(itemCount - 1);
                }
            }

            utils.sound.playSoundByBlock(player, posToBonemeal, SoundType.BONEMEAL);
        }

        return true;
    }
}
