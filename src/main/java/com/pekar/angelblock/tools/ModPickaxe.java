package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.List;

public class ModPickaxe extends PickaxeItem implements IModToolEnhanced
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();

    public static ModPickaxe createPrimary(Tier material, int attackDamage, float attackSpeed, Properties properties)
    {
        return new ModPickaxe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModPickaxe(Tier material, int attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, properties.attributes(PickaxeItem.createAttributes(material, attackDamage, attackSpeed)));
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

    @Override
    public TieredItem getTool()
    {
        return this;
    }

    @Override
    public IMaterialProperties getMaterialProperties()
    {
        return materialProperties;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        for (int i = 0; i <= 0; i++)
        {
            tooltipComponents.add(getDescription(i, false));
        }
    }
}
