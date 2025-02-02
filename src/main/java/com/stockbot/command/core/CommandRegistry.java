package com.stockbot.command.core;

import com.stockbot.command.*;
import com.stockbot.service.StockDataService;
import com.stockbot.service.WatchlistService;

import java.util.List;

public class CommandRegistry {

    private final StockDataService stockDataService;
    private final WatchlistService watchlistService;

    public CommandRegistry(StockDataService stockDataService, WatchlistService watchlistService) {
        this.stockDataService = stockDataService;
        this.watchlistService = watchlistService;
    }

    public void registerCommands(CommandHandler handler) {
        List<Command> commands = List.of(
                new PingCommand(),
                new StockQuoteCommand(stockDataService),
                new SymbolLookupCommand(stockDataService),
                new MarketStatusCommand(stockDataService),
                new CompanyProfileCommand(stockDataService),
                new AddToWatchlistCommand(watchlistService),
                new RemoveFromWatchlistCommand(watchlistService)
        );

        commands.forEach(handler::addCommand);
    }
}