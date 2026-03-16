package com.pekar.angelblock.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

public class RepairMainHandCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String commandName = "repairMainHand";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        if (dispatcher.getRoot().getChild(commandName) != null)
        {
            LOGGER.warn("Command '" + commandName + "' already exists, skipping registration");
            return;
        }

        dispatcher.register(Commands.literal(commandName)
                .requires(src -> src.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
                // no-arg: full repair
                .executes(ctx -> handleRepairGearCommand(ctx, -1))
                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                        .executes(ctx -> handleRepairGearCommand(ctx, IntegerArgumentType.getInteger(ctx, "amount"))))
                .then(Commands.literal("half")
                        .executes(ctx -> handleRepairGearCommand(ctx, -2)))
        );

        LOGGER.debug("RepairMainHandCommand registered");
    }

    private static int handleRepairGearCommand(CommandContext<CommandSourceStack> ctx, int requestedDurability)
    {
        try
        {
            Player player = ctx.getSource().getPlayerOrException();
            ItemStack mainHand = player.getMainHandItem();
            if (mainHand.isDamageableItem())
            {
                int max = mainHand.getMaxDamage();
                if (requestedDurability == -2)
                {
                    int finalDurability = Math.max(1, max / 2);
                    int finalDamage = Math.max(0, max - finalDurability);
                    int maxAllowedDamage = Math.max(0, max - 1);
                    finalDamage = Math.min(finalDamage, maxAllowedDamage);

                    String message = commandName + ": set main-hand item durability to " + finalDurability + " (damage=" + finalDamage + ")";
                    mainHand.setDamageValue(finalDamage);
                    ctx.getSource().sendSuccess(() -> Component.literal(message), false);
                    return 1;
                }
                else if (requestedDurability < 0)
                {
                    // no-arg: fully repair
                    mainHand.setDamageValue(0);
                    String message = commandName + ": fully repaired main-hand item (durability=" + max + ")";
                    ctx.getSource().sendSuccess(() -> Component.literal(message), false);
                    return 1;
                }
                else
                {
                    // clamp requested durability to [1, max]
                    int finalDurability = Math.min(Math.max(1, requestedDurability), max);
                    // remaining durability -> damage value
                    int finalDamage = Math.max(0, max - finalDurability);
                    // avoid setting damage to the exact max (which usually means broken); clamp to max-1 if necessary
                    int maxAllowedDamage = Math.max(0, max - 1);
                    finalDamage = Math.min(finalDamage, maxAllowedDamage);

                    String message = commandName + ": set main-hand item durability to " + finalDurability + " (damage=" + finalDamage + ")";
                    mainHand.setDamageValue(finalDamage);
                    ctx.getSource().sendSuccess(() -> Component.literal(message), false);
                    return 1;
                }
            }
            else
            {
                ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": you must hold a damageable item in main hand"), false);
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