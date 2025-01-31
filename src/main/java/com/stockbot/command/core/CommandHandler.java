package com.stockbot.command.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler extends ListenerAdapter {

    private static final String COMMAND_PREFIX = "!";

    private final Map<String, Command> commands = new HashMap<>();

    public void addCommand(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) {
            return;
        }

        String message = event.getMessage().getContentRaw();

        if (message.startsWith(COMMAND_PREFIX)) {
            String[] parts = message.substring(COMMAND_PREFIX.length()).split(" ");
            String commandName = parts[0].toLowerCase();

            Command command = commands.get(commandName);
            if (command != null) {
                command.execute(event, parts);
            } else {
                event.getChannel().sendMessage("Invalid command!").queue();
            }
        }
    }
}
