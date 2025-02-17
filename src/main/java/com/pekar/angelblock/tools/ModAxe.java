package com.pekar.angelblock.tools;

import com.pekar.angelblock.utils.Utils;
import com.pekar.angelblock.tools.properties.DefaultMaterialProperties;
import com.pekar.angelblock.tools.properties.IMaterialProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.List;

public class ModAxe extends AxeItem implements IModToolEnhanced
{
    protected final IMaterialProperties materialProperties;
    protected final Utils utils = new Utils();

    public static ModAxe createPrimary(Tier material, float attackDamage, float attackSpeed, Properties properties)
    {
        return new ModAxe(material, attackDamage, attackSpeed, properties, new DefaultMaterialProperties());
    }

    public ModAxe(Tier material, float attackDamage, float attackSpeed, Properties properties, IMaterialProperties materialProperties)
    {
        super(material, properties.attributes(AxeItem.createAttributes(material, attackDamage, attackSpeed)));
        this.materialProperties = materialProperties;
    }

    @Override
    public boolean isTool()
    {
        return true;
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
