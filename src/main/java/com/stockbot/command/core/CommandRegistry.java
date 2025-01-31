package com.stockbot.command.core;

import com.stockbot.command.PingCommand;

public class CommandRegistry {

    public static void registerCommands(CommandHandler handler) {
        handler.addCommand(new PingCommand());
    }
}
