package com.stockbot.command;

import com.stockbot.command.core.Command;
import com.stockbot.formatting.MessageFormatter;
import com.stockbot.model.MarketStatusResponse;
import com.stockbot.service.StockDataService;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Optional;

public class MarketStatusCommand implements Command {

    private final StockDataService stockDataService;

    public MarketStatusCommand(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {

        if (args.length < 2) {
            event.getChannel().sendMessage("Please provide an exchange symbol. Example: !market-status US").queue();
            return;
        }

        final String exchangeSymbol = args[1];

        Optional<MarketStatusResponse> response = this.stockDataService.getMarketStatus(exchangeSymbol);

        if(response.isPresent()) {
            MarketStatusResponse status = response.get();
            MessageEmbed formatted = MessageFormatter.createMarketStatusEmbed(status);
            event.getChannel().sendMessageEmbeds(formatted).queue();
        } else {
            event.getChannel().sendMessage("No market status found for the provided exchange symbol.").queue();
        }
    }

    @Override
    public String getName() {
        return "market-status";
    }

    @Override
    public String getDescription() {
        return "Get current market status for global exchanges (whether exchanges are open or close).";
    }
}
