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
        if (!message.startsWith(COMMAND_PREFIX)) {
            return;
        }

        String[] parts = parseCommand(message);
        if (parts.length == 0) {
            return;
        }
        String commandName = parts[0].toLowerCase();

        if ("help".equals(commandName)) {
            handleHelpCommand(event);
        } else {
            Command command = commands.get(commandName);
            if (command != null) {
                command.execute(event, parts);
            } else {
                event.getChannel().sendMessage("Invalid command!").queue();
            }
        }
    }

    /**
     * Parses the raw message into command parts by removing the prefix and splitting by space.
     */
    private String[] parseCommand(String message) {
        return message.substring(COMMAND_PREFIX.length()).trim().split("\\s+");
    }

    /**
     * Constructs and sends the help message listing all available commands.
     */
    private void handleHelpCommand(MessageReceivedEvent event) {
        StringBuilder builder = new StringBuilder("Here are the available commands:\n");
        commands.forEach((name, command) ->
                builder.append("`")
                        .append(COMMAND_PREFIX)
                        .append(name)
                        .append("` - ")
                        .append(command.getDescription())
                        .append("\n")
        );
        event.getChannel().sendMessage(builder.toString()).queue();
    }
}
