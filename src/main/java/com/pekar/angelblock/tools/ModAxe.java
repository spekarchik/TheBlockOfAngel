package com.pekar.angelblock.tools;

import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class ModAxe extends AxeItem implements IModTool
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();

    public static ModAxe createPrimary(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModAxe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModAxe(Tier material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, attackDamage, attackSpeed, properties);
        this.materialProperties = materialProperties;
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

    protected MutableComponent getDisplayName(int lineNumber)
    {
        return Component.translatable(this.getDescriptionId() + ".desc" + lineNumber);
    }
}
