package com.stockbot.command;

import com.stockbot.command.core.Command;
import com.stockbot.service.WatchlistService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AddToWatchlistCommand implements Command {

    private final WatchlistService watchlistService;

    public AddToWatchlistCommand(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (args.length < 2) {
            event.getChannel().sendMessage("❌ Usage: !watchlist-add <TICKER>").queue();
            return;
        }

        String ticker = args[1].toUpperCase();

        try {
            watchlistService.addToWatchlist(event.getGuild(), ticker);
            event.getChannel().sendMessage("✅ Added **" + ticker + "** to the watchlist for this server.").queue();
        } catch (Exception e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }

    @Override
    public String getName() {
        return "watchlist-add";
    }

    @Override
    public String getDescription() {
        return "Adds stock to the server's watchlist. The bot notifies about the price every hour.";
    }
}
