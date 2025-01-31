package com.stockbot.command.core;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {

    void execute(MessageReceivedEvent event, String[] args);

    String getName();

    String getDescription();
}
