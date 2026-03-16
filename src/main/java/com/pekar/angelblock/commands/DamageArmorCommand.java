package com.pekar.angelblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import com.pekar.angelblock.utils.Utils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

public class DamageArmorCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String commandName = "damageArmor";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        if (dispatcher.getRoot().getChild(commandName) != null)
        {
            LOGGER.warn("Command '" + commandName + "' already exists, skipping registration");
            return;
        }

        dispatcher.register(Commands.literal(commandName)
                .requires(src -> src.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
                .executes(ctx -> handleDamageArmorCommand(ctx, -1))
                .then(Commands.argument("damage", IntegerArgumentType.integer(0))
                        .executes(ctx -> handleDamageArmorCommand(ctx, IntegerArgumentType.getInteger(ctx, "damage"))))
                .then(Commands.literal("half")
                        .executes(ctx -> handleDamageArmorCommand(ctx, -2)))
        );

        LOGGER.debug("DamageArmorCommand registered");
    }

    private static int handleDamageArmorCommand(CommandContext<CommandSourceStack> ctx, int requestedDamage)
    {
        try
        {
            Player player = ctx.getSource().getPlayerOrException();
            var armorStacks = Utils.instance.player.getArmorInSlots(player);

            String[] slotNames = {"head", "chest", "legs", "feet"};
            StringBuilder sb = new StringBuilder();
            boolean anyChanged = false;

            for (int i = 0; i < armorStacks.size(); i++)
            {
                ItemStack stack = armorStacks.get(i);
                if (stack == null || stack.isEmpty()) continue;
                if (!stack.isDamageableItem())
                {
                    sb.append(slotNames[i]).append(": not damageable; ");
                    continue;
                }

                int max = stack.getMaxDamage();
                int maxAllowed = Math.max(0, max - 1);
                int finalDamage;
                if (requestedDamage == -2)
                {
                    int halfDurability = Math.max(1, max / 2);
                    finalDamage = Math.max(0, max - halfDurability);
                    if (finalDamage > maxAllowed) finalDamage = maxAllowed;
                }
                else if (requestedDamage < 0)
                {
                    finalDamage = maxAllowed;
                }
                else
                {
                    // explicit clamp to [1, maxAllowed]
                    finalDamage = requestedDamage;
                    if (finalDamage > maxAllowed) finalDamage = maxAllowed;
                }
                int remainingDurability = Math.max(1, max - finalDamage);
                stack.setDamageValue(finalDamage);
                anyChanged = true;
                sb.append(slotNames[i]).append(": damage=").append(finalDamage).append(" (durability=").append(remainingDurability).append("); ");
            }

            if (anyChanged)
            {
                ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": " + sb), false);
                return 1;
            }
            else
            {
                ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": no damageable armor found or nothing changed"), false);
                return 0;
            }
        }
        catch (Exception ex)
        {
            ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": this command must be run by a player"), false);
            return 0;
        }
    }
}
