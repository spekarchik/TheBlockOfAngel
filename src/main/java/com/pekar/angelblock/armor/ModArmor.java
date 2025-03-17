package com.pekar.angelblock.armor;

import com.pekar.angelblock.Main;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ModArmor extends ArmorItem
{
    protected final ArmorItem.Type armorItemType;
    protected final int maxDamage;
    protected final ModArmorMaterial material;
    protected final Utils utils = new Utils();
    private final Set<ArmorModifications> armorModificatorSet = new HashSet<>();

    protected ModArmor(ModArmorMaterial material, Type armorItemType)
    {
        super(material.getMaterial(), armorItemType, new Properties().durability(armorItemType.getDurability(material.getDurabilityMultiplier())));
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

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken)
    {
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (!utils.text.showExtendedDescription(tooltipComponents)) return;

        for (int i = 1; i <= 12; i++)
        {
            var component = getCommonDescription(i, i == 5, false, false, false, i >= 10);
            if (!component.getString().isEmpty())
                tooltipComponents.add(component);
        }

        for (int i = 1; i <= getDescriptionLineCount(); i++)
        {
            tooltipComponents.add(getSpecificDescription(i, i == 1, false, getEquipmentSlot() == EquipmentSlot.FEET && i == 8, false, false));
        }
    }

    private int getDescriptionLineCount()
    {
        return switch (getEquipmentSlot())
        {
            case HEAD -> 5;
            case CHEST -> 8;
            case LEGS -> 5;
            case FEET -> 8;
            default -> 0;
        };
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return !isDamaged(stack);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
    }

    private MutableComponent getCommonDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice, boolean isDarkGray)
    {
        var component = getCommonDisplayName(lineNumber);
        return utils.text.getFormattedTextComponent(component, isHeader, isSubHeader, isNotice, isImportantNotice, isDarkGray);
    }

    private MutableComponent getSpecificDescription(int lineNumber, boolean isHeader, boolean isSubHeader, boolean isNotice, boolean isImportantNotice, boolean isDarkGray)
    {
        var component = getSpecificDisplayName(lineNumber);
        return utils.text.getFormattedTextComponent(component, isHeader, isSubHeader, isNotice, isImportantNotice, isDarkGray);
    }

    private MutableComponent getSpecificDisplayName(int lineNumber)
    {
        return Component.translatable(this.getDescriptionId() + ".desc" + lineNumber);
    }

    private MutableComponent getCommonDisplayName(int lineNumber)
    {
        return Component.translatable(getFullArmorModelName(getArmorFamilyName()).replace(':', '.').replaceAll("[0-9]", "") + ".desc" + lineNumber);
    }

    private String getFullArmorModelName(String armorModelName)
    {
        return Main.MODID + ":" + armorModelName;
    }
}
