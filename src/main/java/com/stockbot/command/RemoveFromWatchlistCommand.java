package com.stockbot.command;

import com.stockbot.command.core.Command;
import com.stockbot.service.WatchlistService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RemoveFromWatchlistCommand implements Command {

    private final WatchlistService watchlistService;

    public RemoveFromWatchlistCommand(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (args.length < 2) {
            event.getChannel().sendMessage("❌ Usage: !watchlist-remove <TICKER>").queue();
            return;
        }

        String ticker = args[1].toUpperCase();

        try {
            watchlistService.removeFromWatchlist(event.getGuild(), ticker);
            event.getChannel().sendMessage("✅ Removed **" + ticker + "** from the watchlist.").queue();
        } catch (RuntimeException e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }

    @Override
    public String getName() {
        return "watchlist-remove";
    }

    @Override
    public String getDescription() {
        return "Removes a stock from the server's watchlist.";
    }
}
