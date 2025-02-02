package com.stockbot;

import com.stockbot.command.core.CommandHandler;
import com.stockbot.command.core.CommandRegistry;
import com.stockbot.config.Config;
import com.stockbot.service.CacheService;
import com.stockbot.service.StockDataService;
import com.stockbot.service.WatchlistService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.logging.Logger;

public class Bot {

    private static final Logger log = Logger.getLogger(Bot.class.getName());

    public static void main(String[] args) {

        log.info("Starting Bot...");

        Config config = new Config();
        final String discordToken = config.getDiscordBotToken();
        final String finnhubToken = config.getFinnhubApiKey();

        CacheService cacheService = new CacheService();
        StockDataService stockDataService = new StockDataService(finnhubToken, cacheService);
        WatchlistService watchlistService = new WatchlistService(stockDataService);

        CommandHandler commandHandler = new CommandHandler();
        CommandRegistry commandRegistry = new CommandRegistry(stockDataService, watchlistService);
        commandRegistry.registerCommands(commandHandler);

        JDA jda = JDABuilder.createDefault(discordToken)
                .addEventListeners(commandHandler)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("Trading stocks!"))
                .build();

        log.info("Bot started!");
    }
}