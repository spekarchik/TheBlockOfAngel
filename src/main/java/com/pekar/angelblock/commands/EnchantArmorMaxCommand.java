package com.pekar.angelblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import com.pekar.angelblock.Main;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.slf4j.Logger;

import static com.pekar.angelblock.utils.Resources.createResourceLocation;

public class EnchantArmorMaxCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final TagKey<Enchantment> EXCLUSIVE_ENCHANTMENTS = TagKey.create(Registries.ENCHANTMENT, createResourceLocation(Main.MODID, "exclusive_enchantments"));
    private static final String commandName = "enchantArmorMax";
    private enum Mode { DEFAULT, ALL, CLEAR }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        if (dispatcher.getRoot().getChild(commandName) != null)
        {
            LOGGER.warn("Command '" + commandName + "' already exists, skipping registration");
            return;
        }

        dispatcher.register(Commands.literal(commandName)
                .requires(src -> src.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
                .executes(ctx -> handleEnchantArmorCommand(ctx, Mode.DEFAULT))
                .then(Commands.literal("all")
                        .executes(ctx -> handleEnchantArmorCommand(ctx, Mode.ALL))
                )
                .then(Commands.literal("clear")
                        .executes(ctx -> handleEnchantArmorCommand(ctx, Mode.CLEAR))
                )
        );

        LOGGER.debug("EnchantArmorMaxCommand registered");
    }

    private static int handleEnchantArmorCommand(CommandContext<CommandSourceStack> ctx, Mode mode)
    {
        try
        {
            Player player = ctx.getSource().getPlayerOrException();
            var armorStacks = Utils.instance.player.getArmorInSlots(player);

            Registry<Enchantment> registry =
                    player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

            String[] slotNames = {"head", "chest", "legs", "feet"};
            StringBuilder sb = new StringBuilder();
            boolean anyChanged = false;

            for (int i = 0; i < armorStacks.size(); i++)
            {
                ItemStack stack = armorStacks.get(i);
                if (stack == null || stack.isEmpty())
                {
                    sb.append(slotNames[i]).append(": empty; ");
                    continue;
                }

                if (!stack.isEnchantable() && !stack.isEnchanted())
                {
                    sb.append(slotNames[i]).append(": not enchantable; ");
                    continue;
                }

                var mutableEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
                for (var enchantment : registry.listElements().toList())
                {
                    int level = enchantment.value().getMaxLevel();
                    if (enchantment.is(EnchantmentTags.CURSE) || mode == Mode.CLEAR) level = 0;

                    if (mode != Mode.ALL)
                    {
                        if (enchantment.is(EXCLUSIVE_ENCHANTMENTS)) level = 0;
                        boolean isExclusive = enchantment.value().exclusiveSet().stream().anyMatch(x -> mutableEnchantments.keySet().contains(x));
                        if (isExclusive) level = 0;
                    }

                    if (stack.supportsEnchantment(enchantment) || mode == Mode.CLEAR)
                    {
                        mutableEnchantments.set(enchantment, level);
                    }
                }

                EnchantmentHelper.setEnchantments(stack, mutableEnchantments.toImmutable());
                anyChanged = true;
                sb.append(slotNames[i]).append(": applied; ");
            }

            if (anyChanged)
            {
                ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": " + sb), false);
                return 1;
            }
            else
            {
                ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": no enchantable armor found or nothing changed"), false);
                return 0;
            }
        }
        catch (Exception e)
        {
            ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": this command must be run by a player"), false);
            return 0;
        }
    }
}