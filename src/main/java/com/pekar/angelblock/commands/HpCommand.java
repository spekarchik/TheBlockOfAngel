package com.pekar.angelblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

public class HpCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String commandName = "hp";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        if (dispatcher.getRoot().getChild(commandName) != null)
        {
            LOGGER.warn("Command '" + commandName + "' already exists, skipping registration");
            return;
        }

        dispatcher.register(Commands.literal(commandName)
                .requires(src -> src.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
                // no-arg: set to max health
                .executes(ctx -> handleHpCommand(ctx, Integer.MIN_VALUE))
                // optional integer argument: desired health value (will be clamped)
                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                        .executes(ctx -> handleHpCommand(ctx, IntegerArgumentType.getInteger(ctx, "amount"))))
                // literal 'half' -> set to half of max
                .then(Commands.literal("half")
                        .executes(ctx -> handleHpCommand(ctx, -2)))
        );

        LOGGER.debug("HpCommand registered");
    }

    private static int handleHpCommand(CommandContext<CommandSourceStack> ctx, int requested)
    {
        try
        {
            Player player = ctx.getSource().getPlayerOrException();
            // Use getMaxHealth() which represents current max health (including modifiers)
            float maxHealth = player.getMaxHealth();

            float finalHealth;
            if (requested == Integer.MIN_VALUE)
            {
                // no-arg: set to max
                finalHealth = maxHealth;
            }
            else if (requested == -2)
            {
                // half: set to half of max, at least 1
                finalHealth = Math.max(1.0f, (float)Math.floor(maxHealth / 2.0f));
            }
            else
            {
                // explicit integer: clamp to [1, max]
                finalHealth = Math.min(Math.max(1.0f, (float)requested), (float)Math.floor(maxHealth));
            }

            // set health but ensure it doesn't exceed max
            float toSet = Math.min(finalHealth, maxHealth);
            // player.setHealth expects float
            player.setHealth(toSet);

            String message = commandName + ": set health to " + toSet + " (max=" + maxHealth + ")";
            ctx.getSource().sendSuccess(() -> Component.literal(message), false);
            return 1;
        }
        catch (Exception ex)
        {
            ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": this command must be run by a player"), false);
            return 0;
        }
    }
}
