package com.stockbot.command;

import com.stockbot.command.core.Command;
import com.stockbot.formatting.MessageFormatter;
import com.stockbot.model.StockSymbolResponse;
import com.stockbot.service.StockDataService;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Optional;

public class SymbolLookupCommand implements Command {

    private final StockDataService stockDataService;

    public SymbolLookupCommand(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {

        if(args.length < 2) {
            event.getChannel().sendMessage("Please provide a query. For example: symbol, security's name to ISIN and Cusip.") .queue();
            return;
        }

        final String query = args[1];

        Optional<StockSymbolResponse> response = this.stockDataService.getSymbolLookup(query);

        if(response.isPresent()) {
            StockSymbolResponse stockSymbolResponse = response.get();
            MessageEmbed formatted = MessageFormatter.createStockSymbolResponseEmbed(stockSymbolResponse);
            event.getChannel().sendMessageEmbeds(formatted).queue();
        } else {
            event.getChannel().sendMessage("No symbols found for query: " + query).queue();
        }
    }

    @Override
    public String getName() {
        return "lookup";
    }

    @Override
    public String getDescription() {
        return "Search for best-matching symbols based on your query. You can input anything from symbol, security's name to ISIN and Cusip.";
    }
}
