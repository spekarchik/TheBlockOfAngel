package com.pekar.angelblock.menus;

import com.pekar.angelblock.armor.ModArmor;
import com.pekar.angelblock.armor.ModArmorMaterial;
import com.pekar.angelblock.items.ItemRegistry;
import com.pekar.angelblock.tools.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;

public class CustomSmithingMenu extends SmithingMenu
{
    public CustomSmithingMenu(int containerId, Inventory playerInventory)
    {
        super(containerId, playerInventory);
    }

    public CustomSmithingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access)
    {
        super(containerId, playerInventory, access);
    }

    @Override
    protected void onTake(Player player, ItemStack stack)
    {
        var template = getSlot(0).getItem();
        var mainItem = getSlot(1).getItem();
        var secondaryItem = getSlot(2).getItem();
        var result = stack;

        if (template.is(ItemRegistry.DOWNGRADE_KIT) && secondaryItem.is(Items.FLINT))
        {
            if (mainItem.getItem() instanceof ModArmor armor)
            {
                var materialName = armor.getArmorMaterial().getMaterialName();

                boolean isResultModArmor = result.getItem() instanceof ModArmor;
                var resultAsModArmor = isResultModArmor ? (ModArmor)result.getItem() : null;

                if (!isResultModArmor || !armor.getArmorFamilyName().equals(resultAsModArmor.getArmorFamilyName()))
                {
                    Item plate;

                    switch (materialName)
                    {
                        case ModArmorMaterial.RENDELITHIC_MATERIAL_NAME:
                            plate = ItemRegistry.RENDELITHIC_PLATE.get();
                            break;
                        case ModArmorMaterial.LIMONITE_MATERIAL_NAME:
                            plate = ItemRegistry.LIMONITE_PLATE.get();
                            break;
                        case ModArmorMaterial.DIAMITHIC_MATERIAL_NAME:
                            plate = ItemRegistry.DIAMITHIC_PLATE.get();
                            break;
                        case ModArmorMaterial.LAPIS_MATERIAL_NAME:
                            plate = ItemRegistry.LAPIS_PLATE.get();
                            break;
                        case ModArmorMaterial.SUPER_MATERIAL_NAME:
                            plate = ItemRegistry.SUPER_PLATE.get();
                            break;
                        case ModArmorMaterial.FLYING_MATERIAL_NAME:
                            plate = ItemRegistry.FLYING_PLATE.get();
                            break;
                        default:
                            plate = Items.AIR;
                            break;

                    }

                    player.getInventory().add(new ItemStack(plate));
                }

                if (armor.isModifiedWithDetector() && (!isResultModArmor || !resultAsModArmor.isModifiedWithDetector()))
                    player.getInventory().add(new ItemStack(Blocks.CALIBRATED_SCULK_SENSOR));

                if (armor.isModifiedWithStrengthBooster() && (!isResultModArmor || !resultAsModArmor.isModifiedWithStrengthBooster()))
                    player.getInventory().add(new ItemStack(ItemRegistry.STRENGTH_PEARL.get()));

                if (armor.isModifiedWithJumpBooster() && (!isResultModArmor || !resultAsModArmor.isModifiedWithJumpBooster()))
                    player.getInventory().add(new ItemStack(ItemRegistry.STRENGTH_PEARL.get()));

                if (armor.isModifiedWithHealthRegenerator() && (!isResultModArmor || !resultAsModArmor.isModifiedWithHealthRegenerator()))
                    player.getInventory().add(new ItemStack(ItemRegistry.BIOS_DIAMOND.get()));

                if (armor.isModifiedWithSlowFalling() && (!isResultModArmor || !resultAsModArmor.isModifiedWithSlowFalling()))
                    player.getInventory().add(new ItemStack(ItemRegistry.SOARING_SPORE_ESSENCE.get()));

                if (armor.isModifiedWithSeaPower() && (!isResultModArmor || !resultAsModArmor.isModifiedWithSeaPower()))
                    player.getInventory().add(new ItemStack(Items.HEART_OF_THE_SEA));

                if (armor.isModifiedWithLuck() && (!isResultModArmor || !resultAsModArmor.isModifiedWithLuck()))
                    player.getInventory().add(new ItemStack(Items.HEART_OF_THE_SEA));

                if (armor.isModifiedWithElytra() && (!isResultModArmor || !resultAsModArmor.isModifiedWithElytra()))
                    player.getInventory().add(new ItemStack(Items.ELYTRA));
            }
            else if (mainItem.getItem() instanceof IModTool tool)
            {
                var materialName = tool.getMaterialName();

                boolean isResultModTool = result.getItem() instanceof IModTool;
                var resultAsModTool = isResultModTool ? (IModTool)result.getItem() : null;

                if (tool instanceof ModRod rod)
                {
                    boolean isResultModRod = result.getItem() instanceof ModRod;
                    var resultAsModRod = isResultModRod ? (ModRod)result.getItem() : null;

                    ItemStack dropItem1, dropItem2 = new ItemStack(Items.AIR);

                    switch (rod)
                    {
                        case AngelRod angelRod -> dropItem1 = new ItemStack(Items.AIR);
                        case EndRod endRod -> dropItem1 = new ItemStack(ItemRegistry.SUPER_CRYSTAL.get());
                        case AmethystRod amethystRod -> dropItem1 = new ItemStack(Items.AMETHYST_SHARD);
                        case FireRod fireRod -> dropItem1 = new ItemStack(ItemRegistry.FLAME_STONE.get());
                        case MarineRod marineRod -> dropItem1 = new ItemStack(ItemRegistry.MARINE_CRYSTAL.get());
                        case AncientRod ancientRod ->
                        {
                            dropItem1 = new ItemStack(ItemRegistry.BIOS_DIAMOND.get());
                        }
                        case TrackLayer trackLayer ->
                        {
                            dropItem1 = new ItemStack(Blocks.PISTON);
                            dropItem2 = new ItemStack(Items.FISHING_ROD);
                        }
                        case Planter planter ->
                        {
                            dropItem1 = new ItemStack(Blocks.DISPENSER);
                            dropItem2 = new ItemStack(Items.NAUTILUS_SHELL);
                        }
                        case Builder builder ->
                        {
                            dropItem1 = new ItemStack(Blocks.PISTON);
                            dropItem2 = new ItemStack(ItemRegistry.STRENGTH_PEARL.get());
                        }
                        default ->
                        {
                            dropItem1 = new ItemStack(Items.AIR);
                        }
                    }

                    if (!dropItem1.isEmpty())
                    {
                        player.getInventory().add(dropItem1);
                    }

                    if (!dropItem2.isEmpty())
                    {
                        player.getInventory().add(dropItem2);
                    }

                    if (rod.isEnhanced() && (!isResultModRod || !resultAsModRod.isEnhanced()))
                    {
                        player.getInventory().add(new ItemStack(ItemRegistry.ROD_SENSOR.get()));
                    }
                }
                else
                {
                    Item dropItem1, dropItem2;

                    switch (materialName)
                    {
                        case ToolMaterials.DIAMITHIC_MATERIAL_NAME:
                            dropItem1 = ItemRegistry.DIAMITHIC_INGOT.get();
                            dropItem2 = ItemRegistry.STRENGTH_PEARL.get();
                            break;
                        case ToolMaterials.LAPIS_MATERIAL_NAME:
                            dropItem1 = ItemRegistry.LAPIS_INGOT.get();
                            dropItem2 = ItemRegistry.MARINE_CRYSTAL.get();
                            break;
                        case ToolMaterials.LIMONITE_MATERIAL_NAME:
                            dropItem1 = ItemRegistry.LIMONITE_INGOT.get();
                            dropItem2 = ItemRegistry.BIOS_DIAMOND.get();
                            break;
                        case ToolMaterials.SUPER_MATERIAL_NAME:
                            dropItem1 = ItemRegistry.SUPER_INGOT.get();
                            dropItem2 = ItemRegistry.SUPER_CRYSTAL.get();
                            break;
                        case ToolMaterials.RENDELITHIC_MATERIAL_NAME:
                            dropItem1 = ItemRegistry.RENDELITHIC_INGOT.get();
                            dropItem2 = ItemRegistry.FLAME_STONE.get();
                            break;
                        default:
                            dropItem1 = Items.AIR;
                            dropItem2 = Items.AIR;
                    }

                    if (!isResultModTool || !tool.getMaterialName().equals(resultAsModTool.getMaterialName()))
                    {
                        player.getInventory().add(new ItemStack(dropItem1));
                    }

                    if (tool.isEnhanced() && (!isResultModTool || !resultAsModTool.isEnhanced()))
                    {
                        player.getInventory().add(new ItemStack(dropItem2));
                    }
                }
            }
            else if (mainItem.is(ItemTags.TRIMMABLE_ARMOR) && result.is(ItemTags.TRIMMABLE_ARMOR))
            {
                if (mainItem.is(ItemRegistry.NETHERITE_ARMOR_TAG) && result.is(ItemRegistry.DIAMOND_ARMOR_TAG))
                {
                    player.getInventory().add(new ItemStack(Items.NETHERITE_INGOT));
                }
            }
            else if (mainItem.getItem().getDefaultInstance().is(ItemRegistry.NETHERITE_TOOL_TAG) && result.getItem().getDefaultInstance().is(ItemRegistry.DIAMOND_TOOL_TAG))
            {
                player.getInventory().add(new ItemStack(Items.NETHERITE_INGOT));
            }
        }

        super.onTake(player, stack);
    }
}
