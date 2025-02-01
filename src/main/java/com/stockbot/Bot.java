package com.stockbot;

import com.stockbot.command.core.CommandHandler;
import com.stockbot.command.core.CommandRegistry;
import com.stockbot.config.Config;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.logging.Logger;

public class Bot {

    private static final Logger log = Logger.getLogger(Bot.class.getName());

    public static void main(String[] args) {

        log.info("Starting Bot...");

        Config config = new Config();
        final String DISCORD_TOKEN = config.getDiscordBotToken();
        final String FINNHUB_TOKEN = config.getFinnhubApiKey();

        CommandHandler commandHandler = new CommandHandler();
        CommandRegistry commandRegistry = new CommandRegistry(FINNHUB_TOKEN);
        commandRegistry.registerCommands(commandHandler);

        JDABuilder builder = JDABuilder.createDefault(DISCORD_TOKEN);
        builder.addEventListeners(commandHandler);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.setActivity(Activity.playing("Trading stocks!"));
        builder.build();

        log.info("Bot started!");
    }
}