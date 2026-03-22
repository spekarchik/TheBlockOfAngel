package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.potions.PotionRegistry;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.TextStyle;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ModHumanoidArmor extends ModArmor
{
    private final Set<ArmorModifications> armorModificatorSet = new HashSet<>();
    protected boolean canFly;
    protected final PlayerArmorType armorType;

    protected ModHumanoidArmor(ModArmorMaterial material, ArmorItem.Type armorItemType, PlayerArmorType armorType, Properties properties)
    {
        super(material, armorItemType, properties.durability(armorItemType.getDurability(material.getDurabilityMultiplier())));
        this.armorType = armorType;
    }

    public PlayerArmorType getArmorType()
    {
        return armorType;
    }

    public ModHumanoidArmor canFly()
    {
        canFly = true;
        return this;
    }

    public boolean isFlying()
    {
        return canFly;
    }

    private boolean isAutoFlightDamage(ItemStack stack, @Nullable LivingEntity entity, int amount)
    {
        if (!canFly) return false;

        return entity instanceof Player player && player.isFallFlying() && amount <= 1;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken)
    {
        if (isAutoFlightDamage(stack, entity, amount)) return 0;

        return super.damageItem(stack, amount, entity, onBroken);
    }

    protected boolean isPlayerHeavy(LivingEntity player)
    {
        return player.hasEffect(PotionRegistry.ARMOR_HEAVY_JUMP_EFFECT)
                || (player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) && player.getEffect(MobEffects.MOVEMENT_SLOWDOWN).isInfiniteDuration());
    }

    public final boolean isModifiedWithDetector()
    {
        return armorModificatorSet.contains(ArmorModifications.Detector);
    }

    public final boolean isModifiedWithNightVision()
    {
        return armorModificatorSet.contains(ArmorModifications.NightVision);
    }

    public final boolean isModifiedWithHealthRegenerator()
    {
        return armorModificatorSet.contains(ArmorModifications.Regenerator);
    }

    public final boolean isModifiedWithStrengthBooster()
    {
        return armorModificatorSet.contains(ArmorModifications.StrengthBooster);
    }

    public final boolean isModifiedWithJumpBooster()
    {
        return armorModificatorSet.contains(ArmorModifications.JumpBooster);
    }

    public final boolean isModifiedWithSlowFalling()
    {
        return armorModificatorSet.contains(ArmorModifications.SlowFalling);
    }

    public final boolean isModifiedWithSeaPower()
    {
        return armorModificatorSet.contains(ArmorModifications.SeaPower);
    }

    public final boolean isModifiedWithElytra()
    {
        return armorModificatorSet.contains(ArmorModifications.Elytra);
    }

    public final boolean isModifiedWithLuck()
    {
        return armorModificatorSet.contains(ArmorModifications.Luck);
    }

    public final ModHumanoidArmor withDetector()
    {
        armorModificatorSet.add(ArmorModifications.Detector);
        return this;
    }

    public final ModHumanoidArmor withNightVision()
    {
        armorModificatorSet.add(ArmorModifications.NightVision);
        return this;
    }

    public final ModHumanoidArmor withHealthRegenerator()
    {
        armorModificatorSet.add(ArmorModifications.Regenerator);
        return this;
    }

    public final ModHumanoidArmor withStrengthBooster()
    {
        armorModificatorSet.add(ArmorModifications.StrengthBooster);
        return this;
    }

    public final ModHumanoidArmor withJumpBooster()
    {
        armorModificatorSet.add(ArmorModifications.JumpBooster);
        return this;
    }

    public final ModHumanoidArmor withSlowFalling()
    {
        armorModificatorSet.add(ArmorModifications.SlowFalling);
        return this;
    }

    public final ModHumanoidArmor withSeaPower()
    {
        armorModificatorSet.add(ArmorModifications.SeaPower);
        return this;
    }

    public final ModHumanoidArmor withLuck()
    {
        armorModificatorSet.add(ArmorModifications.Luck);
        return this;
    }

    public final ModHumanoidArmor withElytra()
    {
        armorModificatorSet.add(ArmorModifications.Elytra);
        return this;
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!flag.hasShiftDown() && !flag.hasAltDown() && !flag.hasControlDown())
        {
            tooltip.addLineById("description.common.press_shift_alt_or_ctrl").apply();
            return;
        }

        if (flag.hasShiftDown())
        {
            tooltip.ignoreEmptyLines();

            for (int i = 1; i <= 9; i++)
            {
                tooltip.addLine(getCommonDescriptionRoot(), i).styledAs(TextStyle.Header, i == 5).apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.armor.press_alt").apply();
            tooltip.addLineById("description.armor.press_ctrl").apply();
            return;
        }

        if (flag.hasAltDown())
        {
            tooltip.includeEmptyLines();

            for (int i = 1; i <= getDescriptionLineCount(); i++)
            {
                tooltip.addLine(getSpecificDescriptionRoot(), i)
                        .styledAs(TextStyle.Header, i == 1)
                        .styledAs(TextStyle.DarkGray, armorSlotType.getSlot() == EquipmentSlot.FEET && i == 9)
                        .apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.armor.press_shift").apply();
            tooltip.addLineById("description.armor.press_ctrl").apply();
            return;
        }

        if (flag.hasControlDown())
        {
            tooltip.ignoreEmptyLines();

            for (int i = 10; i <= 14; i++)
            {
                tooltip.addLine(getCommonDescriptionRoot(), i).styledAs(TextStyle.DarkGray, true).apply();
            }

            tooltip.addEmptyLine();
            tooltip.addLineById("description.armor.press_shift").apply();
            tooltip.addLineById("description.armor.press_alt").apply();
            return;
        }
    }

    private int getDescriptionLineCount()
    {
        return switch (armorSlotType.getSlot())
        {
            case HEAD -> 6;
            case CHEST -> 9;
            case LEGS -> 6;
            case FEET -> 9;
            default -> 0;
        };
    }

    private String getSpecificDescriptionRoot()
    {
        return getDescriptionId();
    }

    private String getArmorFamilyName()
    {
        return material.getMaterialName() + "_armor";
    }

    private String getCommonDescriptionRoot()
    {
        return getFullArmorModelName(getArmorFamilyName()).replace(':', '.').replaceAll("[0-9]", "");
    }

    private String getFullArmorModelName(String armorModelName)
    {
        return Main.MODID + ":" + armorModelName;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
    }
}
