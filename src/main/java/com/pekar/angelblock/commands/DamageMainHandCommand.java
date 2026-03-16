package com.pekar.angelblock.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

public class DamageMainHandCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String commandName = "damageMainHand";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        if (dispatcher.getRoot().getChild(commandName) != null)
        {
            LOGGER.warn("Command '" + commandName + "' already exists, skipping registration");
            return;
        }

        dispatcher.register(Commands.literal(commandName)
                .requires(src -> src.hasPermission(Permissions.COMMANDS_ADMIN))
                // no-arg: default behaviour (maxDamage - 1)
                .executes(ctx -> handleDamageGearCommand(ctx, -1))
                // optional int argument 'damage' (>=0)
                .then(Commands.argument("damage", IntegerArgumentType.integer(0))
                        .executes(ctx -> handleDamageGearCommand(ctx, IntegerArgumentType.getInteger(ctx, "damage")))
                )
                // optional literal 'half' sets remaining durability to half of max
                .then(Commands.literal("half")
                        .executes(ctx -> handleDamageGearCommand(ctx, -2))
                )
        );

        LOGGER.debug("DamageMainHandCommand registered");
    }

    private static int handleDamageGearCommand(CommandContext<CommandSourceStack> ctx, int requestedDamage)
    {
        try
        {
            Player player = ctx.getSource().getPlayerOrException();
            ItemStack mainHand = player.getMainHandItem();
            if (mainHand.isDamageableItem())
            {
                int max = mainHand.getMaxDamage();
                int maxAllowed = Math.max(0, max - 1);
                int finalDamage;

                if (requestedDamage == -2)
                {
                    // half: set remaining durability to half (at least 1), then compute damage
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
                    // explicit clamp to [0, maxAllowed]
                    finalDamage = requestedDamage;
                    if (finalDamage > maxAllowed) finalDamage = maxAllowed;
                }
                // compute remaining durability
                int remainingDurability = Math.max(0, max - finalDamage);
                String message = commandName + ": set main-hand item damage to " + finalDamage + " (durability=" + remainingDurability + ")";
                mainHand.setDamageValue(finalDamage);
                ctx.getSource().sendSuccess(() -> Component.literal(message), false);
                return 1;
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
