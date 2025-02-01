package com.stockbot.command;

import com.stockbot.command.core.Command;
import com.stockbot.formatting.MessageFormatter;
import com.stockbot.model.CompanyProfile;
import com.stockbot.service.StockDataService;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Optional;

public class CompanyProfileCommand implements Command {

    private final StockDataService stockDataService;

    public CompanyProfileCommand(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {

        if(args.length < 2) {
            event.getChannel().sendMessage("Please provide a query. For example: ticker symbol, ISIN or CUSIP.").queue();
            return;
        }

        final String query = args[1];

        Optional<CompanyProfile> response = stockDataService.getCompanyProfile(query);

        if(response.isPresent()) {
            CompanyProfile companyProfile = response.get();
            MessageEmbed formatted = MessageFormatter.createCompanyProfileEmbed(companyProfile);
            event.getChannel().sendMessageEmbeds(formatted).queue();
        } else {
            event.getChannel().sendMessage("No company profile found for query: " + query).queue();
        }
    }

    @Override
    public String getName() {
        return "company";
    }

    @Override
    public String getDescription() {
        return "Get general information of a company. You can query by ticker symbol, ISIN or CUSIP.";
    }
}
