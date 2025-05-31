package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.tooltip.ITooltip;
import com.pekar.angelblock.tooltip.ITooltipProvider;
import com.pekar.angelblock.tooltip.TextStyle;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ModArmor extends Item implements ITooltipProvider
{
    protected final ArmorType armorItemType;
    protected final int maxDamage;
    protected final ModArmorMaterial material;
    protected final Utils utils = new Utils();
    private final Set<ArmorModifications> armorModificatorSet = new HashSet<>();
    protected boolean canFly;

    protected ModArmor(ModArmorMaterial material, ArmorType armorItemType, Properties properties)
    {
        super(properties.humanoidArmor(material.getMaterial(), armorItemType));
        this.material = material;
        this.armorItemType = armorItemType;
        this.maxDamage = armorItemType.getDurability(material.getDurabilityMultiplier());
    }

    public ModArmorMaterial getArmorMaterial()
    {
        return material;
    }

    public String getArmorFamilyName()
    {
        return material.getMaterialName() + "_armor";
    }

    public ModArmor canFly()
    {
        canFly = true;
        return this;
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

        var durability = stack.getMaxDamage() - stack.getDamageValue();

        if (entity != null)
            utils.attributeModifiers.updateArmorAttributeModifier(entity);

        if (amount >= durability)
        {
            stack.setDamageValue(stack.getMaxDamage() - 1);
            return 0;
        }

        return super.damageItem(stack, amount, entity, onBroken);
    }

    public boolean isBroken(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getDamageValue() <= 1;
    }

    public final boolean isModifiedWithDetector()
    {
        return armorModificatorSet.contains(ArmorModifications.Detector);
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

    public final ModArmor withDetector()
    {
        armorModificatorSet.add(ArmorModifications.Detector);
        return this;
    }

    public final ModArmor withHealthRegenerator()
    {
        armorModificatorSet.add(ArmorModifications.Regenerator);
        return this;
    }

    public final ModArmor withStrengthBooster()
    {
        armorModificatorSet.add(ArmorModifications.StrengthBooster);
        return this;
    }

    public final ModArmor withJumpBooster()
    {
        armorModificatorSet.add(ArmorModifications.JumpBooster);
        return this;
    }

    public final ModArmor withSlowFalling()
    {
        armorModificatorSet.add(ArmorModifications.SlowFalling);
        return this;
    }

    public final ModArmor withSeaPower()
    {
        armorModificatorSet.add(ArmorModifications.SeaPower);
        return this;
    }

    public final ModArmor withLuck()
    {
        armorModificatorSet.add(ArmorModifications.Luck);
        return this;
    }

    public final ModArmor withElytra()
    {
        armorModificatorSet.add(ArmorModifications.Elytra);
        return this;
    }

    public int getMaxDamage()
    {
        return components().getOrDefault(DataComponents.MAX_DAMAGE, maxDamage);
    }

    @Override
    public void addTooltip(ItemStack stack, TooltipContext context, ITooltip tooltip, TooltipFlag flag)
    {
        if (!utils.text.showExtendedDescription(tooltip)) return;

        tooltip.ignoreEmptyLines();

        for (int i = 1; i <= 12; i++)
        {
            tooltip.addLine(getCommonDescriptionRoot(), i).styledAs(TextStyle.Header, i == 5).styledAs(TextStyle.DarkGray, i >= 10).apply();
        }

        tooltip.includeEmptyLines();

        for (int i = 1; i <= getDescriptionLineCount(); i++)
        {
            tooltip.addLine(getSpecificDescriptionRoot(), i)
                    .styledAs(TextStyle.Header, i == 1)
                    .styledAs(TextStyle.Notice, armorItemType.getSlot() == EquipmentSlot.FEET && i == 8)
                    .apply();
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> component, TooltipFlag flag)
    {
        ITooltipProvider.appendHoverText(this, stack, context, display, component, flag);
    }

    private int getDescriptionLineCount()
    {
        return switch (armorItemType.getSlot())
        {
            case HEAD -> 5;
            case CHEST -> 8;
            case LEGS -> 5;
            case FEET -> 8;
            default -> 0;
        };
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
    }

    private String getSpecificDescriptionRoot()
    {
        return getDescriptionId();
    }

    private String getCommonDescriptionRoot()
    {
        return getFullArmorModelName(getArmorFamilyName()).replace(':', '.').replaceAll("[0-9]", "");
    }

    private String getFullArmorModelName(String armorModelName)
    {
        return Main.MODID + ":" + armorModelName;
    }
}
