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
        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();
        if (!message.startsWith(COMMAND_PREFIX)) return;

        String[] parts = message.substring(COMMAND_PREFIX.length()).split(" ");
        String commandName = parts[0].toLowerCase();

        if ("help".equals(commandName)) {
            StringBuilder builder = new StringBuilder("Here are the available commands:\n");
            commands.forEach((key, value) -> builder.append("`!").append(key).append("` - ").append(value.getDescription()).append("\n"));
            event.getChannel().sendMessage(builder.toString()).queue();
            return;
        }

        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(event, parts);
        } else {
            event.getChannel().sendMessage("Invalid command!").queue();
        }
    }
}
