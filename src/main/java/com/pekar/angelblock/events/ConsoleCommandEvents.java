package com.pekar.angelblock.events;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import com.pekar.angelblock.commands.DamageArmorCommand;
import com.pekar.angelblock.commands.DamageMainHandCommand;
import com.pekar.angelblock.commands.RepairArmorCommand;
import com.pekar.angelblock.commands.RepairMainHandCommand;
import com.pekar.angelblock.commands.HpCommand;
import com.pekar.angelblock.commands.FoodCommand;
import com.pekar.angelblock.commands.EnchantMaxCommand;
import com.pekar.angelblock.commands.EnchantArmorMaxCommand;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

public class ConsoleCommandEvents implements IEventHandler
{
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        try
        {
            var server = event.getServer();
            var commands = server.getCommands();
            var dispatcher = commands.getDispatcher();

            // Delegate registration to per-command classes
            DamageMainHandCommand.register(dispatcher);
            RepairMainHandCommand.register(dispatcher);
            DamageArmorCommand.register(dispatcher);
            RepairArmorCommand.register(dispatcher);
            FoodCommand.register(dispatcher);
            HpCommand.register(dispatcher);
            EnchantMaxCommand.register(dispatcher);
            EnchantArmorMaxCommand.register(dispatcher);

            LOGGER.info("Registered console commands: damageMainHand, repairMainHand, damageArmor, repairArmor, hp, food, enchantMax, enchantArmorMax");
        }
        catch (Throwable t)
        {
            LOGGER.warn("Could not register console commands: {}", t.toString());
        }
    }
}