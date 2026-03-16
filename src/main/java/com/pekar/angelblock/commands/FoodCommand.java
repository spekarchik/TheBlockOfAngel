package com.pekar.angelblock.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

public class FoodCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String commandName = "food";

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
                .executes(ctx -> handleFoodCommand(ctx, Integer.MIN_VALUE))
                // optional integer argument: desired food value (will be clamped)
                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                        .executes(ctx -> handleFoodCommand(ctx, IntegerArgumentType.getInteger(ctx, "amount"))))
                // literal 'half' -> set to half of max
                .then(Commands.literal("half")
                        .executes(ctx -> handleFoodCommand(ctx, -2)))
        );

        LOGGER.debug("FoodCommand registered");
    }

    private static int handleFoodCommand(CommandContext<CommandSourceStack> ctx, int requested)
    {
        try
        {
            Player player = ctx.getSource().getPlayerOrException();
            // Vanilla maximum food level is 20
            final int MAX_FOOD = 20;

            int finalFood;
            if (requested == Integer.MIN_VALUE)
            {
                // no-arg: set to max
                finalFood = MAX_FOOD;
            }
            else if (requested == -2)
            {
                // half: set to half of max
                finalFood = MAX_FOOD / 2;
            }
            else
            {
                // explicit integer: clamp to [0, MAX_FOOD]
                finalFood = Math.min(Math.max(0, requested), MAX_FOOD);
            }

            var foodData = player.getFoodData();
            // set food level and reset saturation
            foodData.setFoodLevel(finalFood);
            foodData.setSaturation(0.0F);

            String message = commandName + ": set food level to " + finalFood + " (max=" + MAX_FOOD + "), saturation reset to 0";
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