package com.stockbot.command;

import com.stockbot.command.core.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand implements Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("Pong!").queue();
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Connection testing command. The bot should answer with \"Pong!\"";
    }
}
