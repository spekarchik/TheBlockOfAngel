package com.pekar.angelblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

public class Xp500Command
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String commandName = "xp500";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        if (dispatcher.getRoot().getChild(commandName) != null)
        {
            LOGGER.warn("Command '" + commandName + "' already exists, skipping registration");
            return;
        }

        dispatcher.register(Commands.literal(commandName)
                .requires(src -> src.hasPermission(Permissions.COMMANDS_ADMIN))
                // no-arg: set to max food
                .executes(ctx -> handleXpCommand(ctx, Integer.MIN_VALUE))
                // optional integer argument: desired food value (will be clamped)
                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                        .executes(ctx -> handleXpCommand(ctx, IntegerArgumentType.getInteger(ctx, "amount"))))
        );

        LOGGER.debug("FoodCommand registered");
    }

    private static int handleXpCommand(CommandContext<CommandSourceStack> ctx, int requested)
    {
        try
        {
            Player player = ctx.getSource().getPlayerOrException();

            final int MAX_LEVEL = 500;

            int finalLevel;
            if (requested == Integer.MIN_VALUE)
            {
                // no-arg: set to 500 levels
                finalLevel = MAX_LEVEL;
            }
            else
            {
                finalLevel = Math.max(0, requested);
            }

            int levelCorrection = finalLevel - player.experienceLevel;
            player.giveExperienceLevels(levelCorrection);

            String message = commandName + ": set experience level to " + finalLevel;
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