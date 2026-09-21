package com.pekar.angelblock.tools;

import com.pekar.angelblock.blocks.BlockRegistry;
import com.pekar.angelblock.events.cleaners.Cleaner;
import com.pekar.angelblock.events.cleaners.TrackedBlock;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.ArrayList;
import java.util.List;

public abstract class ModSword extends SwordItem implements IModTool, ITooltipProvider
{
    private static final int ANVIL_COUNT = 10;
    private static final int ATTACK_DISTANCE = 4;
    private static final int ATTACK_RADIUS = 4;
    private static final int MIN_ANVIL_HEIGHT = 8;
    private static final int ANVIL_HEIGHT_VARIATION = 7;
    private static final int MIN_CLEARANCE_BELOW_OBSTACLE = 5;

    private static final int[] dx = { 3, -3, 2, 2, -2, -2, 0, 0, 1, 1, -1, -1 };
    private static final int[] dz = { 0, 0, 1, -1, 1, -1, 3, -3, 2, -2, 2, -2 };
    private static final int CactusLifeTime = 1200;
    private static final int WebLifeTime = 1200;
    private static final int TimeThreshold = 600;
    protected final Utils utils = new Utils();

    public ModSword(ModToolMaterial material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, material.isFireResistant()
                            ? properties.attributes(SwordItem.createAttributes(material, attackDamage, attackSpeed)).fireResistant()
                            : properties.attributes(SwordItem.createAttributes(material, attackDamage, attackSpeed)));
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        var modifiedDamage = Mth.clamp(damage, 0, stack.getMaxDamage() - getCriticalDurability());
        stack.set(DataComponents.DAMAGE, modifiedDamage);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state)
    {
        return !hasCriticalDamage(stack) && super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public int getCriticalDurability()
    {
        return 3;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        if (hasCriticalDamage(stack)) return 1F;
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility)
    {
        return !hasCriticalDamage(stack) && super.canPerformAction(stack, itemAbility);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (!hasCriticalDamage(stack) && attacker instanceof ServerPlayer player && player.getFoodData().getFoodLevel() > 0)
            additionalActionOnHurtEnemy(stack, target, player);

        return super.hurtEnemy(stack, target, attacker);
    }

    protected void additionalActionOnHurtEnemy(ItemStack stack, LivingEntity target, ServerPlayer player)
    {
    }

    protected final void setEffectAround(Player player, InteractionHand interactionHand, Level level, BlockPos pos)
    {
        if (player.getFoodData().getFoodLevel() <= 0) return;

        var blockState = level.getBlockState(pos);

        final int posX = pos.getX(), posZ = pos.getZ();
        final int posY = blockState.is(BlockTags.REPLACEABLE)
                ? level.getBlockState(pos.below()).is(BlockTags.REPLACEABLE) ? pos.below(2).getY() : pos.below().getY()
                : pos.getY();

        for (int dx = -4; dx <= 4; dx++)
            for (int dz = -4; dz <= 4; dz++)
            {
                if (Math.abs(dx) + Math.abs(dz) < 3) continue;
                BlockPos blockPos = new BlockPos(posX + dx, posY, posZ + dz);
                processBlock(player, interactionHand, level, blockPos);
            }

        damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, level);
        causePlayerMultiEffectExhaustion(player);
    }

    protected final void setEffectAhead(Player player, InteractionHand interactionHand, Level level, BlockPos pos)
    {
        if (player.getFoodData().getFoodLevel() <= 0) return;

        var blockState = level.getBlockState(pos);

        final int posX = pos.getX(), posZ = pos.getZ();
        final int posY = blockState.is(BlockTags.REPLACEABLE)
                ? level.getBlockState(pos.below()).is(BlockTags.REPLACEABLE) ? pos.below(2).getY() : pos.below().getY()
                : pos.getY();

        final BlockPos playerPos = player.blockPosition();
        final int playerPosX = playerPos.getX(), playerPosZ = playerPos.getZ();
        int a1, a2, k, n, b1, b2;
        final int effectLength = 9, effectWidth = 4;
        final int effectHalfWidth = effectWidth / 2;

        if (Math.abs(posX - playerPosX) < 2)
        {
            a1 = -effectHalfWidth; a2 = effectHalfWidth; k = 0; n = 0;

            if (posZ > playerPosZ)
            {
                // south
                b1 = 0; b2 = effectLength;
            }
            else
            {
                // north
                b1 = -effectLength; b2 = 0;
            }
        }
        else if (posX > playerPosX)
        {
            a1 = 0; a2 = effectLength;
            if (Math.abs(posZ - playerPosZ) < 2)
            {
                // east
                b1 = -effectHalfWidth; b2 = effectHalfWidth; k = 0; n = 0;
            }
            else if (posZ > playerPosZ)
            {
                // south-east
                b1 = 0; b2 = effectLength; k = -1; n = 0;
            }
            else
            {
                // north-east
                b1 = -effectLength; b2 = 0; k = 0; n = 1;
            }
        }
        else
        {
            a1 = -effectLength; a2 = 0;
            if (Math.abs(posZ - playerPosZ) < 2)
            {
                // west
                b1 = -effectHalfWidth; b2 = effectHalfWidth; k = 0; n = 0;
            }
            else if (posZ > playerPosZ)
            {
                // south-west
                b1 = 0; b2 = effectLength; n = 0; k = 1;
            }
            else
            {
                // north-west
                b1 = -effectLength; b2 = 0; n = -1; k = 0;
            }
        }

        for (int dx = a1; dx <= a2; dx++)
            for (int dz = b1 + dx * n; dz <= b2 + dx * k; dz++)
            {
                BlockPos blockPos = new BlockPos(posX + dx, posY, posZ + dz);
                processBlock(player, interactionHand, level, blockPos);
            }

        damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, level);
        causePlayerMultiEffectExhaustion(player);
    }

    protected void causePlayerSingleEffectExhaustion(Player player)
    {
        utils.player.causePlayerExhaustion(player, 1);
    }

    protected void causePlayerMultiEffectExhaustion(Player player)
    {
        utils.player.causePlayerExhaustion(player, 4);
    }

    protected void processBlock(Player player, InteractionHand interactionHand, Level level, BlockPos pos)
    {
        // nothing by default
    }

    protected boolean allowsApplyEffect(Level level, BlockPos pos)
    {
        var blockState = level.getBlockState(pos);
        if (level.isEmptyBlock(pos) || !blockState.isSolidRender(level, pos)) return false;

        var posAbove = pos.above();
        if (level.isEmptyBlock(posAbove)) return true;

        var blockStateAbove = level.getBlockState(posAbove);
        var blockStateAbove2 = level.getBlockState(posAbove.above());
        return blockStateAbove.is(BlockTags.REPLACEABLE) && !blockStateAbove.is(Blocks.WATER) && !blockStateAbove.is(Blocks.LAVA)
                && !blockStateAbove2.is(Blocks.WATER) && !blockStateAbove2.is(Blocks.LAVA);
    }

    private BlockPos calculateCorrectYPosForPlacement(Level level, BlockPos pos)
    {
        if (allowsApplyEffect(level, pos)) return pos;
        else if (allowsApplyEffect(level, pos.below())) return pos.below();
        else if (allowsApplyEffect(level, pos.above())) return pos.above();

        return null;
    }

    protected final void trySetFire(Level level, BlockPos pos)
    {
        var correctPos = calculateCorrectYPosForPlacement(level, pos);
        if (correctPos == null) return;

        if (!level.isClientSide)
        {
            level.setBlock(correctPos.above(), Blocks.FIRE.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        }
    }

    protected final void setWeb(Player player, Level level, BlockPos pos)
    {
        if (level.isClientSide) return;
        var correctPos = calculateCorrectYPosForPlacement(level, pos.below());
        if (correctPos == null) return;

        level.setBlock(correctPos.above(), Blocks.COBWEB.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        int increment = Utils.random.nextInt(TimeThreshold);
        var targetToRemove = new TrackedBlock(Blocks.COBWEB, correctPos.above(), player, WebLifeTime + increment, true);
        Cleaner.add(targetToRemove);

        // no need to call `damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, level);`
    }

    protected void explode(Player player, InteractionHand interactionHand, Level level, BlockPos pos)
    {
        if (player.getFoodData().getFoodLevel() <= 0) return;

        level.explode(player, pos.getX(), pos.getY() + 0.5, pos.getZ(), 0.8f, false /*fire*/, Level.ExplosionInteraction.NONE /*influences on explosion particles?*/);
        level.explode(player, pos.getX() + 0.8, pos.getY() + 0.2, pos.getZ() + 0.8, 0.8f, Level.ExplosionInteraction.NONE);
        level.explode(player, pos.getX() + 0.8, pos.getY() + 0.7, pos.getZ() - 0.8, 0.8f, Level.ExplosionInteraction.NONE);
        level.explode(player, pos.getX() - 0.8, pos.getY() + 0.4, pos.getZ() + 0.8, 0.8f, Level.ExplosionInteraction.NONE);
        level.explode(player, pos.getX() - 0.8, pos.getY() + 1.5, pos.getZ() - 0.8, 0.8f, Level.ExplosionInteraction.NONE);

        damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, level);
        causePlayerSingleEffectExhaustion(player);
    }

    protected final void plantCacti(Player player, Level level, BlockPos pos, InteractionHand interactionHand, Direction facing)
    {
        if (player.getFoodData().getFoodLevel() <= 0) return;

        final int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();

        boolean succeeded = false;
        for (int i = 0; i < dx.length; i++)
        {
            BlockPos blockPos = new BlockPos(posX + dx[i], posY, posZ + dz[i]);
            if (tryPlantCactus(player, level, blockPos, interactionHand, facing))
                succeeded = true;
        }

        if (succeeded)
        {
            damageProperHandItemIfSurvivalIgnoreClient(player, interactionHand, level);
            causePlayerMultiEffectExhaustion(player);
        }
    }

    protected final boolean canUseToolEffect(Player player)
    {
        // We need it to prevent to fire a house when you are holding a sword with fire effect and a torch and trying to set the torch on a wall
        var mainHandItemStack = player.getMainHandItem();
        if (hasCriticalDamage(mainHandItemStack)) return false;

        var offHandItemStack = player.getOffhandItem();
        return offHandItemStack.isEmpty() || offHandItemStack.is(Items.TOTEM_OF_UNDYING);
    }

    private boolean tryPlantCactus(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing)
    {
        BlockState state = level.getBlockState(pos);
        if (!state.is(BlockTags.SAND) || !allowsApplyEffect(level, pos)
                || !level.isEmptyBlock(pos.above(2)) || !level.isEmptyBlock(pos.above(3))
            || !hasEnoughSpace(level, pos.above()))
        {
            return false;
        }

        if (!plantCactus(player, level, pos, hand, facing)) return false;
        level.setBlock(pos.above(2), Blocks.CACTUS.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        level.setBlock(pos.above(3), Blocks.CACTUS.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);

        int increment = Utils.random.nextInt(TimeThreshold);

        var targetToRemove1 = new TrackedBlock(Blocks.CACTUS, pos.above(3), player, CactusLifeTime + increment, false);
        var targetToRemove2 = new TrackedBlock(Blocks.CACTUS, pos.above(2), player, CactusLifeTime + increment + 15, false);
        var targetToRemove3 = new TrackedBlock(Blocks.CACTUS, pos.above(), player, CactusLifeTime + increment + 30, false);
        Cleaner.add(targetToRemove1);
        Cleaner.add(targetToRemove2);
        Cleaner.add(targetToRemove3);

        return true;
    }

    private boolean hasEnoughSpace(Level level, BlockPos pos)
    {
        return level.isEmptyBlock(pos.east()) && level.isEmptyBlock(pos.west())
                && level.isEmptyBlock(pos.north()) && level.isEmptyBlock(pos.south());
    }

    private boolean plantCactus(Player player, Level level, BlockPos pos, InteractionHand hand, Direction facing)
    {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockState state = level.getBlockState(pos);
        if (facing == Direction.UP
                && (allowsApplyEffect(level, pos))
                && !state.getBlock().canSustainPlant(state, level, pos, Direction.UP, Blocks.CACTUS.defaultBlockState()).isFalse() // Blocks.CACTUS.defaultBlockState() -> age == 0
                )
        {
            level.setBlock(pos.above(), Blocks.CACTUS.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);

            if (player instanceof ServerPlayer serverPlayer)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos.above(), itemstack);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected void damageProperHandItemIfSurvivalIgnoreClient(Player player, InteractionHand interactionHand, Level level)
    {
        if (interactionHand == InteractionHand.MAIN_HAND)
            damageMainHandItemIfSurvivalIgnoreClient(player, level);
        else
            damageOffHandItemIfSurvivalIgnoreClient(player, level);
    }

    protected void dropAnvils(Player player, InteractionHand hand, Level level, BlockPos clickedPos)
    {
        if (player.getFoodData().getFoodLevel() <= 0) return;

        double directionX = clickedPos.getX() + 0.5 - player.getX();
        double directionZ = clickedPos.getZ() + 0.5 - player.getZ();
        double horizontalDistance = Math.sqrt(directionX * directionX + directionZ * directionZ);

        double centerX = clickedPos.getX() + 0.5;
        double centerZ = clickedPos.getZ() + 0.5;
        if (horizontalDistance > 0.0001)
        {
            centerX += directionX / horizontalDistance * ATTACK_DISTANCE;
            centerZ += directionZ / horizontalDistance * ATTACK_DISTANCE;
        }

        int centerBlockX = (int)Math.floor(centerX);
        int centerBlockZ = (int)Math.floor(centerZ);
        List<BlockPos> candidates = createAnvilCandidates(centerBlockX, centerBlockZ, clickedPos.getY());
        int spawned = 0;

        for (int attempt = 0; attempt < ANVIL_COUNT && !candidates.isEmpty(); attempt++)
        {
            BlockPos candidate = candidates.remove(level.getRandom().nextInt(candidates.size()));
            int intendedY = clickedPos.getY() + MIN_ANVIL_HEIGHT + level.getRandom().nextInt(ANVIL_HEIGHT_VARIATION);
            BlockPos spawnPos = findAnvilSpawnPosition(level, candidate.getX(), candidate.getZ(), clickedPos.getY(), intendedY);
            if (spawnPos == null) continue;

            var anvilState = BlockRegistry.TRANSIENT_ANVIL.get().defaultBlockState()
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.Plane.HORIZONTAL.getRandomDirection(level.getRandom()));
            FallingBlockEntity anvil = FallingBlockEntity.fall(level, spawnPos, anvilState);
            anvil.setHurtsEntities(2.0F, 40);
            spawned++;
        }

        if (spawned > 0)
        {
            damageProperHandItemIfSurvivalIgnoreClient(player, hand, level);
            causePlayerMultiEffectExhaustion(player);
        }
    }

    private List<BlockPos> createAnvilCandidates(int centerX, int centerZ, int y)
    {
        List<BlockPos> candidates = new ArrayList<>();
        for (int dx = -ATTACK_RADIUS; dx <= ATTACK_RADIUS; dx++)
            for (int dz = -ATTACK_RADIUS; dz <= ATTACK_RADIUS; dz++)
                if (dx * dx + dz * dz <= ATTACK_RADIUS * ATTACK_RADIUS)
                    candidates.add(new BlockPos(centerX + dx, y, centerZ + dz));
        return candidates;
    }

    private BlockPos findAnvilSpawnPosition(Level level, int x, int z, int clickedY, int intendedY)
    {
        BlockPos intendedPos = new BlockPos(x, intendedY, z);
        if (!level.isInWorldBounds(intendedPos) || !level.hasChunkAt(x, z)) return null;

        int surfaceTopY = findSurfaceTop(level, x, z, clickedY, intendedY);
        if (surfaceTopY > intendedY) return null;

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(x, surfaceTopY, z);
        for (int y = surfaceTopY; y <= intendedY; y++)
        {
            cursor.setY(y);
            if (FallingBlock.isFree(level.getBlockState(cursor))) continue;

            int freeBlocks = y - surfaceTopY;
            if (freeBlocks < MIN_CLEARANCE_BELOW_OBSTACLE) return null;

            BlockPos belowObstacle = new BlockPos(x, y - 1, z);
            return level.isEmptyBlock(belowObstacle) ? belowObstacle : null;
        }

        return level.isEmptyBlock(intendedPos) ? intendedPos : null;
    }

    private int findSurfaceTop(Level level, int x, int z, int clickedY, int intendedY)
    {
        int minY = level.getMinBuildHeight();
        int maxY = Math.min(intendedY, level.getMinBuildHeight() + level.getHeight() - 1);
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos(x, Math.min(clickedY, maxY), z);

        if (isBlocking(level, cursor))
        {
            while (cursor.getY() < maxY && isBlocking(level, cursor))
                cursor.move(Direction.UP);
            return cursor.getY();
        }

        while (cursor.getY() > minY)
        {
            cursor.move(Direction.DOWN);
            if (isBlocking(level, cursor)) return cursor.getY() + 1;
        }

        return minY;
    }

    private boolean isBlocking(Level level, BlockPos pos)
    {
        return !FallingBlock.isFree(level.getBlockState(pos));
    }

    @Override
    public final void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean isWeapon()
    {
        return true;
    }

    @Override
    public TieredItem getTool()
    {
        return this;
    }

    public boolean hasExplosionMode()
    {
        return false;
    }

    public boolean hasFireMode()
    {
        return false;
    }

    public boolean hasWebMode()
    {
        return false;
    }
}
