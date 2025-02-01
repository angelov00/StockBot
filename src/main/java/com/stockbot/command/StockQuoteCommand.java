package com.stockbot.command;

import com.stockbot.command.core.Command;
import com.stockbot.formatting.MessageFormatter;
import com.stockbot.model.StockQuoteResponse;
import com.stockbot.service.StockDataService;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Optional;

public class StockQuoteCommand implements Command {

    private final StockDataService stockDataService;

    public StockQuoteCommand(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {

        if(args.length < 2) {
            event.getChannel().sendMessage("Provide a ticker symbol!").queue();
            return;
        }
        final String tickerSymbol = args[1];

        Optional<StockQuoteResponse> response = this.stockDataService.getStockByTickerSymbol(tickerSymbol);

        if(response.isPresent()) {
            StockQuoteResponse quote = response.get();
            MessageEmbed formatted = MessageFormatter.createStockQuoteEmbed(quote);
            event.getChannel().sendMessageEmbeds(formatted).queue();
        } else {
            event.getChannel().sendMessage("No information for this ticker symbol!").queue();
        }
    }

    @Override
    public String getName() {
        return "quote";
    }

    @Override
    public String getDescription() {
        return "Displays the current stock price for a given symbol. Use !quote <stock symbol>, for example: !price AAPL.";
    }
}
