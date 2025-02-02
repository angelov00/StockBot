package com.stockbot.service;

import com.stockbot.model.StockQuoteResponse;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.DefaultGuildChannelUnion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WatchlistService {

    private static final Logger logger = LoggerFactory.getLogger(WatchlistService.class);
    private static final int NOTIFY_INTERVAL = 60;
    private static final TimeUnit NOTIFY_INTERVAL_UNIT = TimeUnit.MINUTES;

    private final Map<Guild, List<String>> watchlist = new ConcurrentHashMap<>();
    private final StockDataService stockDataService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public WatchlistService(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
        startNotificationScheduler();
    }

    public void addToWatchlist(Guild guild, String tickerSymbol) {

        if (stockDataService.getStockByTickerSymbol(tickerSymbol).isEmpty()) {
            throw new IllegalArgumentException("Invalid ticker symbol: " + tickerSymbol);
        }

        List<String> tickers = watchlist.computeIfAbsent(guild, key -> new CopyOnWriteArrayList<>());
        if (tickers.contains(tickerSymbol)) {
            throw new IllegalArgumentException("Stock is already in the watchlist: " + tickerSymbol);
        }
        tickers.add(tickerSymbol);
    }

    public void removeFromWatchlist(Guild guild, String tickerSymbol) {
        List<String> tickers = watchlist.get(guild);
        if (tickers == null || !tickers.contains(tickerSymbol)) {
            throw new IllegalArgumentException("Stock is not in the watchlist: " + tickerSymbol);
        }
        tickers.remove(tickerSymbol);
    }

    private void startNotificationScheduler() {
        scheduler.scheduleAtFixedRate(this::notifyServers, 0, NOTIFY_INTERVAL, NOTIFY_INTERVAL_UNIT);
    }

    private void notifyServers() {
        watchlist.forEach((guild, tickers) -> {
            if (tickers.isEmpty()) {
                return;
            }

            Optional<TextChannel> defaultChannelOpt = Optional.ofNullable(guild.getDefaultChannel())
                    .map(DefaultGuildChannelUnion::asTextChannel);

            if (defaultChannelOpt.isEmpty()) {
                logger.warn("Guild {} does not have a default text channel", guild.getName());
                return;
            }

            StringBuilder message = new StringBuilder("📢 **Updates for your stock watchlist:**\n");
            tickers.forEach(ticker -> {
                Optional<StockQuoteResponse> stockData = stockDataService.getStockByTickerSymbol(ticker);
                if (stockData.isPresent()) {
                    StockQuoteResponse stock = stockData.get();
                    message.append("📈 **")
                            .append(ticker)
                            .append("**: $")
                            .append(stock.getCurrentPrice())
                            .append("\n");
                } else {
                    message.append("⚠ **")
                            .append(ticker)
                            .append("**: Data not available.\n");
                }
            });

            defaultChannelOpt.ifPresent(channel -> channel.sendMessage(message.toString()).queue());
        });
    }
}
