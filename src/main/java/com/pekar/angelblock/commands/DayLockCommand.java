package com.pekar.angelblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import com.pekar.angelblock.utils.GameRulesAccessor;
import com.pekar.angelblock.utils.Weather;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;

public class DayLockCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String commandName = "dayLock";
    private enum Param { DEFAULT, NIGHT, CANCEL }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        if (dispatcher.getRoot().getChild(commandName) != null)
        {
            LOGGER.warn("Command '" + commandName + "' already exists, skipping registration");
            return;
        }

        dispatcher.register(Commands.literal(commandName)
                .requires(src -> src.hasPermission(Permissions.COMMANDS_ADMIN))
                // no-arg: lock day and clear weather
                .executes(ctx -> handleDayLock(ctx, Param.DEFAULT))
                // 'cancel' literal -> re-enable advance_time/advance_weather
                .then(Commands.literal("night")
                        .executes(ctx -> handleDayLock(ctx, Param.NIGHT)))
                .then(Commands.literal("cancel")
                        .executes(ctx -> handleDayLock(ctx, Param.CANCEL)))
        );

        LOGGER.debug("DayLockCommand registered");
    }

    private static int handleDayLock(CommandContext<CommandSourceStack> ctx, Param param)
    {
        try
        {
            var player = ctx.getSource().getPlayerOrException();

            if (!(player instanceof ServerPlayer serverPlayer))
            {
                ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": this command must be run by a server player"), false);
                return 0;
            }

            ServerLevel level = serverPlayer.level();
            var gameRules = GameRulesAccessor.of(level);

            switch (param)
            {
                case CANCEL ->
                {
                    // Re-enable daylight and weather advancement
                    gameRules.set(GameRules.RULE_DAYLIGHT, true);
                    gameRules.set(GameRules.RULE_WEATHER_CYCLE, true);

                    ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": unlocked (advance_time and advance_weather enabled)"), false);
                }

                case NIGHT ->
                {
                    // Disable daylight and weather advancement
                    gameRules.set(GameRules.RULE_DAYLIGHT, false);
                    gameRules.set(GameRules.RULE_WEATHER_CYCLE, false);

                    // Set time to day (same as 'time set day')
                    ctx.getSource().getServer().getCommands()
                            .performPrefixedCommand(ctx.getSource(), "time set midnight");

                    // Clear weather
                    Weather.of(level).clear();

                    ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": locked day and cleared weather"), false);
                }

                case DEFAULT ->
                {
                    // Disable daylight and weather advancement
                    gameRules.set(GameRules.RULE_DAYLIGHT, false);
                    gameRules.set(GameRules.RULE_WEATHER_CYCLE, false);

                    // Set time to day (same as 'time set day')
                    ctx.getSource().getServer().getCommands()
                            .performPrefixedCommand(ctx.getSource(), "time set noon");

                    // Clear weather
                    Weather.of(level).clear();

                    ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": locked day and cleared weather"), false);
                }
            }

            return 1;
        }
        catch (Exception ex)
        {
            ctx.getSource().sendFailure(Component.literal(commandName + ": failed to execute command: " + ex.getMessage()));
            return 0;
        }
    }
}
