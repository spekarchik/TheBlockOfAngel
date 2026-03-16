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

public class RepairArmorCommand
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String commandName = "repairArmor";

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
                .executes(ctx -> handleRepairArmorCommand(ctx, -1))
                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                        .executes(ctx -> handleRepairArmorCommand(ctx, IntegerArgumentType.getInteger(ctx, "amount"))))
                .then(Commands.literal("half")
                        .executes(ctx -> handleRepairArmorCommand(ctx, -2)))
        );

        LOGGER.debug("RepairArmorCommand registered");
    }

    private static int handleRepairArmorCommand(CommandContext<CommandSourceStack> ctx, int requestedDurability)
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
                if (requestedDurability == -2)
                {
                    int finalDurability = Math.max(1, max / 2);
                    int finalDamage = Math.max(0, max - finalDurability);
                    int maxAllowedDamage = Math.max(0, max - 1);
                    finalDamage = Math.min(finalDamage, maxAllowedDamage);
                    stack.setDamageValue(finalDamage);
                    sb.append(slotNames[i]).append(": durability=").append(finalDurability).append(" (damage=").append(finalDamage).append("); ");
                    anyChanged = true;
                }
                else if (requestedDurability < 0)
                {
                    // fully repair
                    stack.setDamageValue(0);
                    sb.append(slotNames[i]).append(": fully repaired (durability=").append(max).append("); ");
                    anyChanged = true;
                }
                else
                {
                    int finalDurability = Math.min(Math.max(1, requestedDurability), max);
                    int finalDamage = Math.max(0, max - finalDurability);
                    int maxAllowedDamage = Math.max(0, max - 1);
                    finalDamage = Math.min(finalDamage, maxAllowedDamage);
                    stack.setDamageValue(finalDamage);
                    sb.append(slotNames[i]).append(": durability=").append(finalDurability).append(" (damage=").append(finalDamage).append("); ");
                    anyChanged = true;
                }
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