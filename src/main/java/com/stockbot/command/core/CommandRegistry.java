package com.stockbot.command.core;

import com.stockbot.command.*;
import com.stockbot.service.StockDataService;

public class CommandRegistry {

    private final StockDataService stockDataService;

    public CommandRegistry(String finnhubApiKey) {
        this.stockDataService = new StockDataService(finnhubApiKey);
    }

    public void registerCommands(CommandHandler handler) {
        handler.addCommand(new PingCommand());
        handler.addCommand(new StockQuoteCommand(stockDataService));
        handler.addCommand(new SymbolLookupCommand(stockDataService));
        handler.addCommand(new MarketStatusCommand(stockDataService));
        handler.addCommand(new CompanyProfileCommand(stockDataService));
    }
}