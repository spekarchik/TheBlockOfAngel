package com.pekar.angelblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;

import java.util.Collection;

public class ResetProgressCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String commandName = "resetProgress";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        if (dispatcher.getRoot().getChild(commandName) != null)
        {
            LOGGER.warn("Command '" + commandName + "' already exists, skipping registration");
            return;
        }

        dispatcher.register(Commands.literal(commandName)
                .requires(src -> src.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
                .executes(ctx -> handleResetProgressCommand(ctx))
        );

        LOGGER.debug("ResetProgressCommand registered");
    }

    private static int handleResetProgressCommand(CommandContext<CommandSourceStack> ctx)
    {
        try
        {
            var player = ctx.getSource().getPlayerOrException();
            if (!(player instanceof ServerPlayer serverPlayer))
            {
                ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": this command must be run by a server player"), false);
                return 0;
            }

            MinecraftServer server = serverPlayer.level().getServer();

            // Reset all advancements: revoke all completed criteria for each advancement
            var advancements = server.getAdvancements().getAllAdvancements();
            for (var advancement : advancements)
            {
                AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
                // revoke completed criteria (clear progress)
                progress.getCompletedCriteria().forEach(progress::revokeProgress);
            }

            // Reset all recipes
            RecipeManager recipeManager = server.getRecipeManager();
            var allRecipes = recipeManager.getRecipes();
            serverPlayer.resetRecipes(allRecipes);

            ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": advancements and recipes reset for player"), false);
            return 1;
        }
        catch (Exception ex)
        {
            ctx.getSource().sendSuccess(() -> Component.literal(commandName + ": this command must be run by a player"), false);
            return 0;
        }
    }
}
